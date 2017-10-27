package com.tost.serv;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;


@Path("/home")
public class HomeServlet extends HttpServlet {
    @GET
    public String yesyes(){
        return "I'm Paul. This is my API rest.";
    }

    @GET
    @Path("/nono")
    public String nono(){
        return "I'm Paul. This is my API rest.";
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Momomo");
    }
}
