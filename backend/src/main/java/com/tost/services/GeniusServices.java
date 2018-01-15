package com.tost.services;

import com.tost.models.Place;
import com.tost.models.UserRecommendation;
import org.json.JSONObject;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class GeniusServices {

    static int recommendedPlaceRank=0;

    public static JSONObject getRecommendation(String userId){

        DatabaseServices.openDB();
        JSONObject recom = new JSONObject();

        UserRecommendation myRecs = UserRecommendation.findFirst("id_user=?",Integer.parseInt(userId));
        if(myRecs==null){
            System.out.println("Bug1");
            return recom;
        }

        String[] myRecsIds = myRecs.get("id_places").toString().split(",");
        if(recommendedPlaceRank+1>myRecsIds.length){
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

        LocalDateTime fiveAm = LocalDateTime.now().withHour(5).withMinute(0);
        LocalDateTime eightAm = LocalDateTime.now().withHour(8).withMinute(0);
        LocalDateTime twoPm = LocalDateTime.now().withHour(14).withMinute(0);
        LocalDateTime sixPm = LocalDateTime.now().withHour(18).withMinute(0);

        System.out.println(placeCategories);

        if(currentTime.isAfter(sixPm) && currentTime.isBefore(fiveAm)) {
            //Bar
            if (!placeCategories.contains("Bar")) {
                System.out.println("Searching for a bar");
                recommendedPlaceRank++;
                recommendedPlaceId = Integer.parseInt(myRecsIds[recommendedPlaceRank]);
                recommendedPlace = Place.findById(recommendedPlaceId);
                if(recommendedPlace==null){
                    System.out.println("Bug2");
                    return recom;
                }
            }
        } else if (currentDay.toString().equals("SUNDAY") &&
                currentTime.isAfter(eightAm) && currentTime.isBefore(twoPm))
        {
            //Brunch
            if (!placeCategories.contains("Brunch")) {
                System.out.println("Searching for a brunch");
                recommendedPlaceRank++;
                recommendedPlaceId = Integer.parseInt(myRecsIds[recommendedPlaceRank]);
                recommendedPlace = Place.findById(recommendedPlaceId);
                if(recommendedPlace==null){
                    System.out.println("Bug2");
                    return recom;
                }
            }
        } else if (currentDay.toString().equals("SUNDAY") &&
                (currentTime.isAfter(fiveAm) && currentTime.isBefore(eightAm)) ||
                (currentTime.isAfter(twoPm) && currentTime.isBefore(sixPm)))
        {
            //Café
            if (!placeCategories.contains("Café")) {
                System.out.println("Searching for a café");
                recommendedPlaceRank++;
                recommendedPlaceId = Integer.parseInt(myRecsIds[recommendedPlaceRank]);
                recommendedPlace = Place.findById(recommendedPlaceId);
                if(recommendedPlace==null){
                    System.out.println("Bug2");
                    return recom;
                }
            }
        } else {
            //Café
            if (!placeCategories.contains("Café")) {
                System.out.println("Searching for a cafééé");
                recommendedPlaceRank++;
                recommendedPlaceId = Integer.parseInt(myRecsIds[recommendedPlaceRank]);
                recommendedPlace = Place.findById(recommendedPlaceId);
                if(recommendedPlace==null){
                    System.out.println("Bug2");
                    return recom;
                }
                System.out.println(recommendedPlace);

            }
        }

        recommendedPlaceRank++;

        recom.put("google_place_id",recommendedPlace.get("google_place_id"));
        recom.put("place_name",recommendedPlace.get("place_name"));
        recom.put("categories",recommendedPlace.get("place_categories"));

        DatabaseServices.closeDB();
        return recom;
    }

    //public static fetchRecommendation() {}
}
