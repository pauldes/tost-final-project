package com.tost.services;

import com.tost.models.Tag;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

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
}
