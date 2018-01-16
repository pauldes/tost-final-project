package com.tost.services;

import com.google.maps.GeoApiContext;
import com.tost.models.Place;
import com.tost.models.UserRecommendation;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class GeniusServices {

    static int recommendedPlaceRank = 0;
    static int MAX_RECOMMENDATIONS  = 10;

    public static JSONObject getRecommendation(String userId){

        DatabaseServices.openDB();
        JSONObject recom = new JSONObject();

        UserRecommendation myRecs = UserRecommendation.findFirst("id_user=?",Integer.parseInt(userId));
        if(myRecs==null){
            System.out.println("Bug1");
            return recom;
        }

        String[] myRecsIds = myRecs.get("id_places").toString().split(",");
        myRecsIds = sortRecommendations(myRecsIds);

        // Reopen DB (security)
        DatabaseServices.openDB();

        if(recommendedPlaceRank+1>=myRecsIds.length){
            recommendedPlaceRank = 0;
        }

        int recommendedPlaceId = Integer.parseInt(myRecsIds[recommendedPlaceRank]);

        Place recommendedPlace = Place.findById(recommendedPlaceId);
        if(recommendedPlace==null){
            System.out.println("Bug2");
            return recom;
        }

        String placeCategories = recommendedPlace.getString("place_categories");

        //Show place according to the time
        LocalDateTime currentTime = LocalDateTime.now();
        DayOfWeek currentDay = currentTime.getDayOfWeek();

        System.out.println("Time is "+currentTime.toString());
        System.out.println("Day is "+currentDay.toString());

        LocalDateTime fiveAm = LocalDateTime.now().withHour(5).withMinute(0);
        LocalDateTime eightAm = LocalDateTime.now().withHour(8).withMinute(0);
        LocalDateTime twoPm = LocalDateTime.now().withHour(14).withMinute(0);
        LocalDateTime sixPm = LocalDateTime.now().withHour(18).withMinute(0);

        System.out.println("Found categories are : "+placeCategories);

        if(currentTime.isAfter(sixPm) && currentTime.isBefore(fiveAm)) {
            //Bar
            System.out.println("We are looking for a bar");
            if (!placeCategories.contains("Bar")) {
                System.out.println("Not a bar. Next...");
                recommendedPlaceRank++;
                return getRecommendation(userId);
            }
        } else if (currentDay.toString().equals("SUNDAY") &&
                currentTime.isAfter(eightAm) && currentTime.isBefore(twoPm))
        {
            //Brunch
            System.out.println("We are looking for a brunch");
            if (!placeCategories.contains("Brunch")) {
                System.out.println("Not a brunch. Next...");
                recommendedPlaceRank++;
                return getRecommendation(userId);
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
                return getRecommendation(userId);
            }
        } else {
            //Café
            System.out.println("We are looking for a caffé");
            if (!placeCategories.contains("Café")) {
                System.out.println("Not a café (2). Next...");
                recommendedPlaceRank++;
                return getRecommendation(userId);
            }
        }

        recommendedPlaceRank++;

        recom.put("google_place_id",recommendedPlace.get("google_place_id"));
        recom.put("place_name",recommendedPlace.get("place_name"));
        recom.put("categories",recommendedPlace.get("place_categories"));

        DatabaseServices.closeDB();
        return recom;
    }

    private static String[] sortRecommendations(String[] inputIds) {

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
}
