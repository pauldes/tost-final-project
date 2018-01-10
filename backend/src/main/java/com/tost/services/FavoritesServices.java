package com.tost.services;

import com.tost.models.Place;
import com.tost.models.UserLikedPlace;
import org.json.JSONObject;

import java.util.List;

public class FavoritesServices {

    public static String addToFavorites(String placeName, String googlePlaceId, String placeCategories, Double userLike, String userId){

        DatabaseServices.openDB();

        // Add the place if no exists
        Place hypotheticPlace = Place.findFirst("place_name=?",placeName);
        int idPlace=0;
        if(hypotheticPlace==null){
            // Add to db if necessary
            Place newPlace = new Place();
            newPlace.set("place_name",placeName);
            newPlace.set("google_place_id",googlePlaceId);
            newPlace.set("place_categories",placeCategories);
            newPlace.saveIt();
            newPlace = Place.findFirst("place_name=?",placeName);
            idPlace = Integer.parseInt(newPlace.getId().toString());
        } else {
            idPlace = Integer.parseInt(hypotheticPlace.getId().toString());
        }

        // Add a link if no exist
        UserLikedPlace hypotheticLink = UserLikedPlace.findFirst("id_user=? AND id_place=?",userId,idPlace);
        if(hypotheticLink==null){
            UserLikedPlace newLink = new UserLikedPlace();
            newLink.set("id_user",userId);
            newLink.set("id_place",idPlace);
            newLink.set("user_like",userLike);
            newLink.saveIt();
        } else {
            hypotheticLink.set("user_like", userLike);
            hypotheticLink.saveIt();
        }

        DatabaseServices.closeDB();
        return "OK";
    }
    public static String removeFromFavorites(){
        return "";
    }

    public static String getFavorites(String userId){

        JSONObject myFavsJson = new JSONObject();
        String myFavsStr = "[";
        DatabaseServices.openDB();

        // Get likes by user
        List <UserLikedPlace> allLikes = UserLikedPlace.where("id_user=?",userId);

        // Get places for each like
        int counter=0;
        for(UserLikedPlace like: allLikes){
            String placeId = like.get("id_place").toString();
            Place place = Place.findById(placeId);
            if(place!=null) {
                String currentPlace = place.toJson(false);
                //myFavsJson.put(counter, currentPlace);

                if(counter>0){
                    myFavsStr+=", ";
                }
                //myFavsStr += counter + " : " + currentPlace;
                myFavsStr += currentPlace;
                counter++;
            }
        }
        DatabaseServices.closeDB();
        myFavsStr+="]";
        System.out.println(myFavsStr);
        return myFavsStr;
    }

}
