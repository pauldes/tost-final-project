package io.tost.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		asyncSupported = true, 
		name = "Greetings", 
		urlPatterns = "/")

public class Greetings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Greetings() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
<<<<<<< HEAD
		response.getWriter().print("Hello world");
=======
		response.getWriter().print("Hey. You are on the Tøst server.");
>>>>>>> al-homepage
	}

	/*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	*/
}
