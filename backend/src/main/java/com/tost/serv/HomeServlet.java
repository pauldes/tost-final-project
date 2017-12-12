package com.tost.serv;

import com.tost.services.ConnectionServices;
import com.tost.services.FavoritesServices;
import org.json.JSONObject;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class HomeServlet extends HttpServlet {

    String userId = null;

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
                System.out.println(userId);
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
        return "hey";
    }

    @Path("/favorites/get")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getFavs(String data)
    {
        JSONObject jsonData = new JSONObject(data);
        return FavoritesServices.getFavorites();
    }
}
