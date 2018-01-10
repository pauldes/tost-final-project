package com.tost.services;

import com.tost.models.User;
import org.json.JSONObject;

import java.util.List;
public class GroupsServices {

    static int MAX_AUTOCOMPLETE_RESULTS = 5;

    public static JSONObject getAutocompletePseudos(String input) {

        DatabaseServices.openDB();
        JSONObject results = new JSONObject();

        List<User> matchingUserList = User.findAll();
        //List<User> matchingUserList = User.where("user_pseudo=?","'"+input+"%'").limit(5);
        // Marche pas et je sais pas pourquoi !

        int count = 0;
        for(User user : matchingUserList){
            // Tant que la requete sql marche pas, obligé de faire à la main...
            if(count<MAX_AUTOCOMPLETE_RESULTS && user.get("user_pseudo").toString().startsWith(input)){
                results.put(""+user.getId(), user.get("user_pseudo"));
            }
        }

        System.out.println(results);

        DatabaseServices.closeDB();
        return results;
    }

    public static String createGroup(){
        //TODO
        return "OK";
    }

}
