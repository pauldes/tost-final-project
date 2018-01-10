package com.tost.services;

import com.tost.models.User;
import com.tost.models.UserGroup;
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

    public static String createGroup(String userId, String name, String members){

        DatabaseServices.openDB();

        UserGroup newGroup = new UserGroup();
        newGroup.set("group_name",name);
        newGroup.set("owner_id",userId);
        newGroup.set("members_id",members);
        newGroup.saveIt();

        DatabaseServices.closeDB();
        return "OK";
    }

    public static String getMyGroups(String userId) {
        String myGroupsStr = "[";
        DatabaseServices.openDB();

        // Get groups where user is owner
        List <UserGroup> allGroups = UserGroup.where("owner_id=?",userId);

        int counter = 0;
        for(UserGroup group: allGroups){
            String groupName = group.get("group_name").toString();
            String groupMembers = group.get("members_id").toString();
            String groupId = group.getId().toString();

            String groupStr = group.toJson(false);
            JSONObject groupJson = new JSONObject(groupStr);
            groupJson.put("members_name","Laura, Juliette");
            groupStr = groupJson.toString();

            if(counter>0){
                myGroupsStr += ", ";
            }
            myGroupsStr += groupStr;
            counter++;
        }

        DatabaseServices.closeDB();
        myGroupsStr += "]";

        System.out.println("OUTPUT:" + myGroupsStr);

        return myGroupsStr;
    }
}
