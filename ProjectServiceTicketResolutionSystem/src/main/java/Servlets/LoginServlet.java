package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import BeanClasses.LoginCredentials;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Client client = ClientBuilder.newClient(new ClientConfig());
		if(request.getParameter("submit").equals("Login")) {
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/Login").path("validate");
			LoginCredentials credentials = new LoginCredentials();
			credentials.setUsername(request.getParameter("username"));
			credentials.setPassword(request.getParameter("password"));
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.post(Entity.entity(credentials, MediaType.APPLICATION_JSON));
			
			String message = res.readEntity(String.class);
			
			System.out.println(message);
			if(message.equals("User")) {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("UserHome.jsp");
				HttpSession session = request.getSession(); 
				session.setAttribute("username", request.getParameter("username"));
				requestDispatcher.forward(request, response);
			}
			else if(message.equals("ServiceEngineer")) {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("ServiceEngineerHome.jsp");
				HttpSession session = request.getSession();
				session.setAttribute("username", request.getParameter("username"));
				requestDispatcher.forward(request, response);
			}
			else if(message.equals("Admin"))
			{
				response.sendRedirect("Admin.jsp");
			}
			else
			{
			
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("Login.jsp");
				request.setAttribute("message", message);
				requestDispatcher.forward(request, response);
			}
		}
		else if(request.getParameter("submit").equals("Register"))
		{
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/Login").path("register");
			LoginCredentials credentials = new LoginCredentials();
			credentials.setUsername(request.getParameter("username"));
			credentials.setPassword(request.getParameter("password"));
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.post(Entity.entity(credentials, MediaType.APPLICATION_JSON));
			
			String message = res.readEntity(String.class);
			System.out.println(message);
			
			if(message.equals("Registered"))
			{
				response.sendRedirect("Login.jsp");
			}
			else if(message.equals("UsernameAlreadyExists"))
			{
				response.sendRedirect("Register.jsp");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
