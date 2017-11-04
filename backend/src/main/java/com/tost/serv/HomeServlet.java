package com.tost.serv;

import com.tost.services.ConnectionServices;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/")
public class HomeServlet extends HttpServlet {

    @GET
    public String yesyes()
    {
        return "I'm Paul. This is my API rest.";
    }

    @Path("/signin")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String signIn(@FormParam("username") String username, @FormParam("password") String password)
    {
        if(username==null || password==null || username.equals("") || password.equals(""))
        {
            return "INVALID_CREDENTIALS";
        }
        else
        {
            return ""+ ConnectionServices.checkCredentials(username,password);
        }
    }

    @Path("/signup")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String signUp(@FormParam("username") String username, @FormParam("mail") String mail, @FormParam("password") String password)
    {
        if(username==null || password==null || mail==null || username.equals("") || password.equals("") || mail.equals(""))
        {
            return "INVALID_CREDENTIALS";
        }
        else
        {
            return ""+ ConnectionServices.createUser(mail,username,password);
        }
    }
}
