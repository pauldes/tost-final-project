package com.tost.services;

import com.tost.models.Place;
import com.tost.models.UserRecommendation;
import org.json.JSONObject;

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
        recommendedPlaceRank++;

        Place recommendedPlace = Place.findById(recommendedPlaceId);
        if(recommendedPlace==null){
            System.out.println("Bug2");
            return recom;
        }
        recom.put("google_place_id",recommendedPlace.get("google_place_id"));
        recom.put("place_name",recommendedPlace.get("place_name"));
        recom.put("categories",recommendedPlace.get("place_categories"));

        DatabaseServices.closeDB();
        return recom;
    }
}
