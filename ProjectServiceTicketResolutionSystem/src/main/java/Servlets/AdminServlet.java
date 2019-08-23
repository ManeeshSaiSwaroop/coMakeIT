package Servlets;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import BeanClasses.Departments;
import BeanClasses.LoginCredentials;
import BeanClasses.Roles;
import BeanClasses.ServiceEngineerDetails;

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Client client = ClientBuilder.newClient(new ClientConfig());
		String option = request.getParameter("option");
		
		if(option.equals("RegisterUser"))
		{
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/Admin").path("registerUser");
			LoginCredentials credentials = new LoginCredentials();
			credentials.setUsername(request.getParameter("username"));
			credentials.setPassword(request.getParameter("password"));
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.post(Entity.entity(credentials, MediaType.APPLICATION_JSON));
			
			String message = res.readEntity(String.class);
			System.out.println(message);
			
			if(message.equals("Registered"))
			{
				message = "User "+ credentials.getUsername()+ " has been registered";
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("Admin.jsp");
				request.setAttribute("registrationStatus", message);
				requestDispatcher.forward(request, response);
				
			}
			else if(message.equals("UsernameAlreadyExists"))
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("Register.jsp");
				request.setAttribute("registrationStatus", message);
				requestDispatcher.forward(request, response);
			}
		}
		else if(option.equals("getDepartments"))
		{
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/Admin").path("getDepartments");
			
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.get();
			
			String jsonDepartments = res.readEntity(String.class);
			System.out.println(jsonDepartments);
			Type collectionType = new TypeToken<List<Departments>>() {
			}.getType();
			List<Departments> departmentList = new Gson().fromJson(jsonDepartments, collectionType);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("RegisterServiceEngineer.jsp");
			request.setAttribute("departments", departmentList);
			requestDispatcher.forward(request, response);
		}
		else if(option.equals("RegisterServiceEngineer"))
		{
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/Admin").path("registerServiceEngineer");
			
			LoginCredentials credentials = new LoginCredentials();
			credentials.setUsername(request.getParameter("username"));
			credentials.setPassword(request.getParameter("password"));
			int departmentID = Integer.parseInt((String) request.getParameter("Department"));
			
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.post(Entity.entity(credentials, MediaType.APPLICATION_JSON));
			
			String message = res.readEntity(String.class);
			System.out.println(message);
			
			if(message.equals("Registered"))
			{
				webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/Admin").path("insertIntoServiceEngineerDetails");
				
				ServiceEngineerDetails engineerDetails = new ServiceEngineerDetails();
				engineerDetails.setCredentials(credentials);
				Departments departments = new Departments();
				departments.setDepartmentID(departmentID);
				engineerDetails.setDepartments(departments);
				
				Invocation.Builder invocationBuilder1 = webTarget.request(MediaType.APPLICATION_JSON);
				Response res1 = invocationBuilder1.post(Entity.entity(engineerDetails, MediaType.APPLICATION_JSON));
				
				String department = res1.readEntity(String.class);
				
				String registrationStatus = "ServiceEngineer " + credentials.getUsername() + " of Department " + department + " has been registered" ;
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("Admin.jsp");
				request.setAttribute("registrationStatus", registrationStatus);
				requestDispatcher.forward(request, response);
				
			}
			
			else if(message.equals("UsernameAlreadyExists"))
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("RegisterServiceEngineer.jsp");
				request.setAttribute("registrationStatus", message);
				requestDispatcher.forward(request, response);
			}
		}
		else if(option.equals("getRoles"))
		{
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/Admin").path("getRoles");
			
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.get();
			
			String jsonRoles = res.readEntity(String.class);
			Type collectionType = new TypeToken<List<Roles>>() {
			}.getType();
			List<Roles> roleList = new Gson().fromJson(jsonRoles, collectionType);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewUsers.jsp");
			request.setAttribute("option", "getRoles");
			request.setAttribute("roles", roleList);
			requestDispatcher.forward(request, response);
		}
		else if(option.equals("viewUsers"))
		{
			Roles roles = new Roles();
			roles.setRoleID(Integer.parseInt(request.getParameter("Role")));
			
			if(roles.getRoleID()==1)
			{
				WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/Admin").path("getUsers");
				
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
				Response res = invocationBuilder.post(Entity.entity(roles, MediaType.APPLICATION_JSON));
				
				String jsonUsers = res.readEntity(String.class);
				Type collectionType = new TypeToken<List<LoginCredentials>>() {
				}.getType();
				List<LoginCredentials> userList = new Gson().fromJson(jsonUsers, collectionType);
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewUsers.jsp");
				request.setAttribute("option", "viewUsers");
				request.setAttribute("roleID", roles.getRoleID());
				request.setAttribute("userList", userList);
				requestDispatcher.forward(request, response);
				
			}
			else if(roles.getRoleID()==2)
			{
				WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/Admin").path("getServiceEngineers");
				
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
				Response res = invocationBuilder.get();
				
				GenericType<List<ServiceEngineerDetails>> type = new GenericType<List<ServiceEngineerDetails>>() {};
				List<ServiceEngineerDetails> serviceEngineerList = res.readEntity(type);
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewUsers.jsp");
				request.setAttribute("option", "viewServiceEngineers");
				request.setAttribute("roleID", roles.getRoleID());
				request.setAttribute("serviceEngineerList", serviceEngineerList);
				requestDispatcher.forward(request, response);
			}
			response.sendRedirect("Admin.jsp");
		}
		else if(option.equals("deleteServiceEngineer"))
		{
			ServiceEngineerDetails engineerDetails = new ServiceEngineerDetails();
			engineerDetails.setID(Long.parseLong(request.getParameter("Delete")));
			
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/Admin").path("deleteServiceEngineer");
			
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			@SuppressWarnings("unused")
			Response res = invocationBuilder.post(Entity.entity(engineerDetails, MediaType.APPLICATION_JSON));
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("Admin.jsp");
			request.setAttribute("message", "ServiceEngineer of ID-"+engineerDetails.getID()+" has been deleted");
			requestDispatcher.forward(request, response);
			
		}
		else if(option.equals("deleteUser"))
		{
			LoginCredentials loginCredentials = new LoginCredentials();
			loginCredentials.setUsername(request.getParameter("Delete"));
			
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/Admin").path("deleteUser");
			
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			@SuppressWarnings("unused")
			Response res = invocationBuilder.post(Entity.entity(loginCredentials, MediaType.APPLICATION_JSON));
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("Admin.jsp");
			request.setAttribute("message", "user-"+loginCredentials.getUsername()+" has been deleted");
			requestDispatcher.forward(request, response);
			
		}
		else if(option.equals("Logout"))
		{	
			HttpSession httpSession = request.getSession(false);
			httpSession.invalidate();
			response.sendRedirect("Login.jsp");
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
