package com.tost.services;

import com.tost.models.Place;
import com.tost.models.Tag;
import com.tost.models.TagUsedForPlace;
import com.tost.models.UserUsedTag;

//import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TagsServices {

    public static String getRandomTags() {

        String tagsStr = "[";
        DatabaseServices.openDB();

        Random rand = new Random();

        // Get all tags
        List <Tag> allTags = Tag.findAll();

        for (int i = 0; i < 8; i++) {
            int randomIndex = rand.nextInt(allTags.size());
            tagsStr += allTags.get(randomIndex).toJson(false) + ",";
            allTags.remove(randomIndex);
        }

        int randomIndex = rand.nextInt(allTags.size());
        tagsStr += allTags.get(randomIndex).toJson(false);

        DatabaseServices.closeDB();
        tagsStr+="]";

        //JSONParser parser = new JSONParser();
        //JSONObject tagsJson = (JSONObject) parser.parse(tagsStr);

        return tagsStr;
    }

    public static String addToTagUsedForPlace(String placeTagName, String placeId) {

        DatabaseServices.openDB();

        Tag placeTag = Tag.findFirst("place_tag_name=?", placeTagName);

        String placeTagId = placeTag.getId().toString();
        placeId = Place.findFirst("google_place_id=?", placeId).getId().toString();

        // Add a link if no exist
        TagUsedForPlace hypotheticLink = TagUsedForPlace.findFirst("id_place_tag=? AND id_place=?", placeTagId, placeId);

        if(hypotheticLink==null){
            TagUsedForPlace newLink = new TagUsedForPlace();
            newLink.set("id_place_tag", placeTagId);
            newLink.set("id_place", placeId);
            newLink.set("score", 1);
            newLink.saveIt();
        } else {
            Double newScore = hypotheticLink.getDouble("score");
            newScore += 1;
            hypotheticLink.set("score", newScore);
            hypotheticLink.saveIt();
        }

        DatabaseServices.closeDB();
        return "OK";
    }

    public static String addToUserUsedTag(String placeTagName, String userId) {

        DatabaseServices.openDB();

        Tag placeTag = Tag.findFirst("place_tag_name=?", placeTagName);

        String placeTagId = placeTag.getId().toString();

        //Add a link if none exists
        UserUsedTag hypotheticLink = UserUsedTag.findFirst("id_place_tag=? AND id_user=?", placeTagId, userId);

        if(hypotheticLink==null){
            UserUsedTag newLink = new UserUsedTag();
            newLink.set("id_place_tag", placeTagId);
            newLink.set("id_user", userId);
            newLink.set("score", 1);
            newLink.saveIt();
        } else {
            Double newScore = hypotheticLink.getDouble("score");
            newScore += 1;
            hypotheticLink.set("score", newScore);
            hypotheticLink.saveIt();
        }

        DatabaseServices.closeDB();
        return "OK";

    }
}
