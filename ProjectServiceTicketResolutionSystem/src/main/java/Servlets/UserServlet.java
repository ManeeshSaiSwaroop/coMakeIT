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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import BeanClasses.Departments;
import BeanClasses.LoginCredentials;
import BeanClasses.Priorities;
import BeanClasses.ServiceEngineerDetails;
import BeanClasses.TicketDetails;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Client client = ClientBuilder.newClient(new ClientConfig());
		String option = request.getParameter("option");
		if(option.equals("getRequiredTables"))
		{
			WebTarget webTarget2 = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/User").path("getPriorities");
			Invocation.Builder invocationBuilder2 = webTarget2.request(MediaType.APPLICATION_JSON);
			Response res2 = invocationBuilder2.get();
			
			String jsonPriorities = res2.readEntity(String.class);
			System.out.println(jsonPriorities);
			Type collectionType2 = new TypeToken<List<Priorities>>() {
			}.getType();
			List<Priorities> priorityList = new Gson().fromJson(jsonPriorities, collectionType2);
			
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/User").path("getDepartments");
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.get();
			
			String jsonDepartments = res.readEntity(String.class);
			System.out.println(jsonDepartments);
			Type collectionType = new TypeToken<List<Departments>>() {
			}.getType();
			List<Departments> departmentList = new Gson().fromJson(jsonDepartments, collectionType);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("SubmitTicketForm.jsp");
			request.setAttribute("priorities", priorityList);
			request.setAttribute("departments", departmentList);
			requestDispatcher.forward(request, response);
			
		}
		else if(option.equals("submitTicket"))
		{
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/User").path("submitTicket");
			
			HttpSession session = request.getSession(false);
			
			TicketDetails details = new TicketDetails();
			LoginCredentials credentials = new LoginCredentials();
			ServiceEngineerDetails details2 = new ServiceEngineerDetails();
			Departments departments = new Departments();
			credentials.setUsername((String)session.getAttribute("username"));
			departments.setDepartmentID(Integer.parseInt(request.getParameter("Department")));
			details2.setDepartments(departments);
			details.setDetails(details2);
			details.setProblemDescription(request.getParameter("ProblemDescription"));
			Priorities priorities = new Priorities();
			priorities.setPriorityID(Integer.parseInt(request.getParameter("TicketPriority")));
			details.setPriorities(priorities);
			details.setRequestedEndDate(request.getParameter("RequestedEndDate"));
			details.setCredentials(credentials);
			
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.post(Entity.entity(details, MediaType.APPLICATION_JSON));
			
			String message = res.readEntity(String.class);
			System.out.println(message);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("UserHome.jsp");
			request.setAttribute("message", message);
			dispatcher.forward(request, response);
		}
		else if(option.equals("viewTickets"))
		{
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/User").path("viewTickets");
			
			HttpSession session = request.getSession(false);
			LoginCredentials credentials = new LoginCredentials();
			credentials.setUsername((String) session.getAttribute("username"));
			
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.post(Entity.entity(credentials, MediaType.APPLICATION_JSON));
			
			String tickets = res.readEntity(String.class);
			Type collectionType = new TypeToken<List<TicketDetails>>() {
			}.getType();
			List<TicketDetails> details = new Gson().fromJson(tickets, collectionType);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("UserTickets.jsp");
			request.setAttribute("Tickets", details);
			dispatcher.forward(request, response);
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
