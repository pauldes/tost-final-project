package com.tost.serv;

import com.tost.services.ConnectionServices;
import com.tost.services.FavoritesServices;
import com.tost.services.GeniusServices;
import com.tost.services.TagsServices;
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

    @Path("/randomtags/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getRandomTags(String data)
    {

        return TagsServices.getRandomTags();
    }
}
