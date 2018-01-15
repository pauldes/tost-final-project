package com.tost.services;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.InvalidRequestException;
import com.google.maps.model.PlaceDetails;
import com.tost.models.Place;
import com.tost.models.UserRecommendation;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

                try {

                    PlaceDetails currDetails = PlacesApi.placeDetails(context,currId).await();
                    /*
                    PlaceDetailsRequest request = new PlaceDetailsRequest(context);
                    PlaceDetails currDetails = request.placeId(currId).await();
                    */

                } catch(InvalidRequestException ire){
                    System.out.println("Exception InvalidRequestException!");
                } catch (Exception e){
                    System.out.println("Exception !");
                    System.out.println(e.toString());
                }

                //Compute score
                currScore=currScore;

                idScoreMap.put(currId,currScore);
            }
        }

        System.out.println(idScoreMap);

        //Close DB
        DatabaseServices.closeDB();

        // Return sorted and shortened array
        return inputIds;
    }
}
