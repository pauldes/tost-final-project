package com.tost.serv;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;


@Path("/")
public class HomeServlet extends HttpServlet {

    @GET
    public String yesyes()
    {
        return "I'm Paul. This is my API rest.";
    }

    @POST
    @Path("/signin")
    public boolean signIn(HttpServletRequest req)
    {
        String mail = req.getParameter("mail");
        String cryptedPassword = req.getParameter("cryptedPassword");
        if(mail!=null && cryptedPassword!=null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
