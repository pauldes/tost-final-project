package com.tost.serv;

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
        if(username!=null && password!=null)
        {
            return "Hello "+username;
        }
        else
        {
            return "false";
        }
    }
}
