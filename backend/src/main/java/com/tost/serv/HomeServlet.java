package com.tost.serv;

import com.tost.services.ConnectionServices;
import org.json.JSONObject;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class HomeServlet extends HttpServlet {

    @GET
    public String yesyes()
    {
        return "Welcome to the Tost API home !";
    }

    @Path("/signin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
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
            return ConnectionServices.checkCredentials(username,password);
        }
    }

    @Path("/signup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
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
}
