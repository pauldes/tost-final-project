package com.tost.serv;

import com.tost.services.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class HomeServlet extends HttpServlet {

    static String userId = null;

    @GET
    public String testConnection()
    {
        return "Hey :)";
    }

    @Path("/signin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String signIn(String data)
    {
        JSONObject jsonData = new JSONObject(data);

        if( !jsonData.has("username") || !jsonData.has("password"))
        {
            return "INVALID_POST";
        }
        else
        {
            String username = jsonData.getString("username");
            String password = jsonData.getString("password");
            String[]returns = ConnectionServices.checkCredentials(username,password).split("__");
            if(returns.length>1){
                userId = returns[1];
                System.out.println("User "+userId+" ("+username+")"+" connected");
            }
            return returns[0];
        }
    }

    @Path("/signup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String signUp(String data)
    {
        JSONObject jsonData = new JSONObject(data);

        if(!jsonData.has("username") || !jsonData.has("password") || !jsonData.has("mail"))
        {
            return "INVALID_POST";
        }
        else
        {
            String username = jsonData.getString("username");
            String password = jsonData.getString("password");
            String mail = jsonData.getString("mail");
            return ConnectionServices.createUser(mail,username,password);
        }
    }

    @Path("/favorites/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addToFavs(String data)
    {
        JSONObject jsonData = new JSONObject(data);
        if(!jsonData.has("google_place_id") || !jsonData.has("place_name") || !jsonData.has("place_categories") || !jsonData.has("user_like"))
        {
            return "INVALID_POST";
        }
        else
        {
            String placeCategories = jsonData.getString("place_categories");
            String placeName = jsonData.getString("place_name");
            String googlePlaceId = jsonData.getString("google_place_id");
            Double userLike = jsonData.getDouble("user_like");
            return FavoritesServices.addToFavorites(placeName,googlePlaceId,placeCategories, userLike, userId);
        }
    }

    @Path("/favorites/get")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getFavs(String data)
    {
        JSONObject jsonData = new JSONObject(data);
        if(userId==null)
            return "ERROR";
        else
            return FavoritesServices.getFavorites(userId).toString();
    }

    @Path("/genius/get")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getGenius(String data)
    {
        if(userId==null)
            return "ERROR";
        else
            return GeniusServices.getRecommendation(userId).toString();
    }

    @Path("/groups/autocomplete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getAutocomplete(String data)
    {
        JSONObject jsonData = new JSONObject(data);
        if(!jsonData.has("input") )
        {
            return "INVALID_POST";
        }
        else if(userId==null) {
            return "ERROR";
        }
        else {
            String input = jsonData.getString("input");
            if(input.equals(""))
                return "";
            else
                return GroupsServices.getAutocompletePseudos(input).toString();
        }
    }

    @Path("/groups/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createGroup(String data)
    {
        System.out.println(data);
        JSONObject jsonData = new JSONObject(data);

        if(!jsonData.has("members") || !jsonData.has("name") )
        {
            return "INVALID_POST";
        }
        else if(userId==null)
            return "ERROR";
        else {
            String groupName = jsonData.getString("name");
            JSONArray membersJson = jsonData.getJSONArray("members");
            int memberCount = membersJson.length();
            System.out.println("Got "+memberCount);
            String members = "";
            for(int i=0; i<memberCount; i++){
                if(i>0)
                    members+=",";
                members += membersJson.get(i).toString();
            }
            return GroupsServices.createGroup(userId,groupName,members);
        }

    }

    @Path("groups/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getGroups()
    {
        if(userId==null)
            return "ERROR";
        else
            return GroupsServices.getMyGroups(userId);
    }

    @Path("/randomtags/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getRandomTags()
    {
        return TagsServices.getRandomTags();
    }

    @Path("/placetag/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addTagToPlace(String data)
    {
        JSONObject jsonData = new JSONObject(data);
        if(!jsonData.has("place_tag_name") || !jsonData.has("place_id") || !jsonData.has("score"))
        {
            return "INVALID_POST";
        }
        else
        {
            String placeTagName = jsonData.getString("place_tag_name");
            String placeId = jsonData.getString("place_id");
            return TagsServices.addToTagUsedForPlace(placeTagName, placeId);
        }
    }
}
