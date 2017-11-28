package com.tost.services;

import com.tost.models.Place;
import org.json.JSONObject;

import java.util.List;

public class FavoritesServices {

    public static String addToFavorites(){
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
        JSONObject jsonData = new JSONObject();
        return jsonData;
    }

}
