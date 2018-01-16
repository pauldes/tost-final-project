package com.tost.services;

import com.google.maps.GeoApiContext;
import com.tost.models.Place;
import com.tost.models.UserGroup;
import com.tost.models.UserRecommendation;
import org.json.JSONObject;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GeniusServices {

    static int recommendedPlaceRank = 0;
    static int MAX_RECOMMENDATIONS  = 10;

    public static JSONObject getRecommendation(String userId, boolean isGroupRecomm, String groupId){

        DatabaseServices.openDB();
        JSONObject recom = new JSONObject();

        String[] myRecsIds;
        if(isGroupRecomm){
            myRecsIds = getRecommendationsArrayGroup(groupId);
        } else {
            myRecsIds = getRecommendationsArraySingle(userId);
        }
        myRecsIds = sortRecommendationsByDistance(myRecsIds);

        if(recommendedPlaceRank+1>=myRecsIds.length){
            recommendedPlaceRank = 0;
        }

        // Reopen DB (security)
        DatabaseServices.openDB();

        Place recommendedPlace = Place.findById(myRecsIds[recommendedPlaceRank]);
        if(recommendedPlace==null){
            System.out.println("Bug2");
            return recom;
        }

        String placeCategories = recommendedPlace.getString("place_categories");

        //Show place according to the time
        LocalDateTime currentTime = LocalDateTime.now();
        DayOfWeek currentDay = currentTime.getDayOfWeek();

        LocalDateTime fiveAm = LocalDateTime.now().withHour(5).withMinute(0);
        LocalDateTime eightAm = LocalDateTime.now().withHour(8).withMinute(0);
        LocalDateTime twoPm = LocalDateTime.now().withHour(14).withMinute(0);
        LocalDateTime sixPm = LocalDateTime.now().withHour(18).withMinute(0);

        System.out.println("Found categories are : "+placeCategories);

        if(currentTime.isAfter(sixPm) && currentTime.isBefore(fiveAm.plusDays(1))) {
            //Bar
            System.out.println("We are looking for a bar");
            if (!placeCategories.contains("Bar")) {
                System.out.println("Not a bar. Next...");
                recommendedPlaceRank++;
                return getRecommendation(userId,isGroupRecomm,groupId);
            }
        } else if (currentDay.toString().equals("SUNDAY") &&
                currentTime.isAfter(eightAm) && currentTime.isBefore(twoPm))
        {
            //Brunch
            System.out.println("We are looking for a brunch");
            if (!placeCategories.contains("Brunch")) {
                System.out.println("Not a brunch. Next...");
                recommendedPlaceRank++;
                return getRecommendation(userId,isGroupRecomm,groupId);
            }
        } else if (currentDay.toString().equals("SUNDAY") &&
                (currentTime.isAfter(fiveAm) && currentTime.isBefore(eightAm)) ||
                (currentTime.isAfter(twoPm) && currentTime.isBefore(sixPm)))
        {
            //Café
            System.out.println("We are looking for a caffé");
            if (!placeCategories.contains("Café")) {
                System.out.println("Not a café. Next...");
                recommendedPlaceRank++;
                return getRecommendation(userId,isGroupRecomm,groupId);
            }
        } else {
            //Café
            System.out.println("We are looking for a caffé");
            if (!placeCategories.contains("Café")) {
                System.out.println("Not a café (2). Next...");
                recommendedPlaceRank++;
                return getRecommendation(userId,isGroupRecomm,groupId);
            }
        }

        recommendedPlaceRank++;

        recom.put("google_place_id",recommendedPlace.get("google_place_id"));
        recom.put("place_name",recommendedPlace.get("place_name"));
        recom.put("categories",recommendedPlace.get("place_categories"));

        DatabaseServices.closeDB();
        return recom;
    }

    private static String[] getRecommendationsArraySingle(String userId){

        System.out.println("GeniusServices.getRecommendationsArraySingle");
        System.out.println("userId = [" + userId + "]");

        DatabaseServices.openDB();
        UserRecommendation myRecs = UserRecommendation.findFirst("id_user=?",Integer.parseInt(userId));
        if(myRecs==null){
            System.out.println("Bug1");
            return new String[0];
        }
        String[] myRecsIds = myRecs.get("id_places").toString().split(",");
        DatabaseServices.closeDB();

        return myRecsIds;
    }

    private static String[] getRecommendationsArrayGroup(String groupId){

        System.out.println("GeniusServices.getRecommendationsArrayGroup");
        System.out.println("groupId = [" + groupId + "]");

        // Will store a score for each place
        Map<String,Integer> placeScore = new TreeMap<String, Integer>();

        DatabaseServices.openDB();
        UserGroup group = UserGroup.findById(groupId);
        String[] userIds = group.get("members_id").toString().split(",");

        System.out.println("Found "+userIds.length+" members");

        for(int i=0; i<userIds.length; i++){
            String[] currRecomm = getRecommendationsArraySingle(userIds[i]);

            System.out.println("Has "+currRecomm.length+" recoms");

            for(int j=0; j<currRecomm.length; j++) {
                String currPlaceId = currRecomm[j];
                System.out.println("treating "+j+"th. recom : placeId="+currPlaceId);
                if(placeScore.containsKey(currPlaceId)){
                    placeScore.replace(currPlaceId, placeScore.get(currPlaceId)+j);
                } else {
                    placeScore.put(currPlaceId, j);
                }
            }
        }

        //Sort map by value
        System.out.println(placeScore);
        placeScore = sortMapByValue(placeScore);
        System.out.println(placeScore);

        //Put the hashmap into an array
        String[] placeScoreArr = new String[placeScore.size()];
        int k = 0;
        for(Map.Entry<String,Integer> entry : placeScore.entrySet()){
            placeScoreArr[k] = entry.getKey();
            k++;
        }
        //Over !
        return placeScoreArr;

    }

    private static String[] sortRecommendationsByDistance(String[] inputIds) {

        // Open DB
        DatabaseServices.openDB();

        // Create Google API context
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCkbd1ZrFB-uc4urPcTg1_39Aw29F5lLFg")
                .build();

        // Prepare a map id->score
        Map<String,Integer> idScoreMap = new HashMap<String, Integer>();

        // Browse the array
        // Not full length to limit response time
        int maxRecommendations = inputIds.length;
        if(maxRecommendations>MAX_RECOMMENDATIONS){
            maxRecommendations = MAX_RECOMMENDATIONS;
        }
        for(int i=0; i<maxRecommendations; i++){

            String currId = inputIds[i];
            int currScore = 0;

            Place currPlace = Place.findById(currId);
            if(currPlace!=null){

                //Get Google details
                final String currGoogleId = currPlace.get("google_place_id").toString();

                //TODO : make this work

                /*

                try {

                    PlaceDetails currDetails = PlacesApi.placeDetails(context,currId).await();
                    /*
                    PlaceDetailsRequest request = new PlaceDetailsRequest(context);
                    PlaceDetails currDetails = request.placeId(currId).await();
                    */

                /*

                } catch(InvalidRequestException ire){
                    System.out.println("Exception InvalidRequestException!");
                } catch (Exception e){
                    System.out.println("Exception !");
                    System.out.println(e.toString());
                }

                */

                //Compute score
                currScore=currScore;
                idScoreMap.put(currId,currScore);
            }
        }

        //System.out.println(idScoreMap);

        //Close DB
        DatabaseServices.closeDB();

        // Return sorted and shortened array
        return inputIds;
    }

    private static <K, V extends Comparable<? super V>> Map<K, V>  sortMapByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort( list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo( o2.getValue() );
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
