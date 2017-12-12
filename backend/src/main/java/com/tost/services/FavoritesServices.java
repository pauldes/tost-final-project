package com.tost.services;

import com.tost.models.Place;
import org.json.JSONObject;

import java.util.List;

public class FavoritesServices {

    public static String addToFavorites(String username,String placeName,String googlePlaceId){

        //1. Check if exists in db
        //2. Add to db if necessary
        //3. Add a link between u and p

        return "";
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
