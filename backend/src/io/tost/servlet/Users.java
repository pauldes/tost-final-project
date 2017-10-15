package io.tost.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.tost.service.Operations;

@WebServlet(asyncSupported = true, urlPatterns = { "/users" })
public class Users extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Users() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String username = "";
		String usernameParam = request.getParameter("username");
		if(usernameParam!=null)
			username = usernameParam;
		//response.getWriter().print("Users request. Hello "+username+" !");
		response.getWriter().print("Users are: "+Operations.getAllUsers());
		
	}

	/*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	*/
}
