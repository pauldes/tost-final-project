package com.tost.services;

import com.tost.models.Place;
import com.tost.models.UserLikedPlace;
import org.json.JSONObject;

import java.util.List;

public class FavoritesServices {

    public static String addToFavorites(String placeName, String googlePlaceId, String placeCategories, String userId){

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
            idPlace = Integer.parseInt(newPlace.get("id_place").toString());
        } else {
            idPlace = Integer.parseInt(hypotheticPlace.get("id_place").toString());
        }

        // Add a link if no exist
        UserLikedPlace hypotheticLink = UserLikedPlace.findFirst("id_user=? AND id_place=?",userId,idPlace);
        if(hypotheticLink==null){
            UserLikedPlace newLink = new UserLikedPlace();
            newLink.set("id_user",userId);
            newLink.set("id_place",idPlace);
            newLink.set("user_like",1);
            newLink.saveIt();
        }

        DatabaseServices.closeDB();
        return "OK";
    }
    public static String removeFromFavorites(){
        return "";
    }

    public static JSONObject getFavorites(){

        //Get user
        //Get id of places liked by user
        //Get places with these ids

        List<Place> places = Place.findAll();
        return new JSONObject();
    }

}
