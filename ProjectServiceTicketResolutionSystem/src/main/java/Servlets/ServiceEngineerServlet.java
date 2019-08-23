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

import BeanClasses.LoginCredentials;
import BeanClasses.Priorities;
import BeanClasses.ServiceEngineerDetails;
import BeanClasses.TicketDetails;

public class ServiceEngineerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServiceEngineerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Client client = ClientBuilder.newClient(new ClientConfig());
		String option = request.getParameter("option");
		
		if(option.equals("viewTickets"))
		{
			
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/ServiceEngineer").path("viewTickets");
			
			HttpSession session = request.getSession(false);
			LoginCredentials credentials = new LoginCredentials();
			credentials.setUsername((String) session.getAttribute("username"));
			
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.post(Entity.entity(credentials, MediaType.APPLICATION_JSON));
			
			String tickets = res.readEntity(String.class);
			Type collectionType = new TypeToken<List<TicketDetails>>() {
			}.getType();
			List<TicketDetails> details = new Gson().fromJson(tickets, collectionType);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("viewServiceEngineerTickets.jsp");
			request.setAttribute("Tickets", details);
			request.setAttribute("option", "viewTickets");
			dispatcher.forward(request, response);
			
		}
		else if(option.equals("resolveTickets"))
		{
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/ServiceEngineer").path("resolveTickets");
			
			HttpSession session = request.getSession(false);
			LoginCredentials credentials = new LoginCredentials();
			credentials.setUsername((String) session.getAttribute("username"));
			
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.post(Entity.entity(credentials, MediaType.APPLICATION_JSON));
			
			TicketDetails ticketDetails = res.readEntity(TicketDetails.class);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("viewServiceEngineerTickets.jsp");
			request.setAttribute("TicketDetails", ticketDetails);
			request.setAttribute("option", "resolveTickets");
			dispatcher.forward(request, response);
		}
		else if(option.equals("closeTicket"))
		{
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/ServiceEngineer").path("closeTicket");
			
			HttpSession session = request.getSession(false);
			LoginCredentials credentials = new LoginCredentials();
			credentials.setUsername((String) session.getAttribute("username"));
			
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.put(Entity.entity(credentials, MediaType.APPLICATION_JSON));
			
			String message = res.readEntity(String.class);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("ServiceEngineerHome.jsp");
			request.setAttribute("message", message);
			dispatcher.forward(request, response);
		}
		else if(option.equals("changePriority"))
		{
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/ServiceEngineer").path("changePriority");
			
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.get();
			
			String jsonPriorities = res.readEntity(String.class);
			System.out.println(jsonPriorities);
			Type collectionType = new TypeToken<List<Priorities>>() {
			}.getType();
			List<Priorities> priorityList = new Gson().fromJson(jsonPriorities, collectionType);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("ChangePriority.jsp");
			request.setAttribute("priorities", priorityList);
			requestDispatcher.forward(request, response);
		}
		else if(option.equals("UpdatePriority"))
		{
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/ServiceEngineer").path("updatePriority");
			
			TicketDetails ticketDetails = new TicketDetails();
			ticketDetails.setTicketID(request.getParameter("RTicketID"));
			Priorities priorities = new Priorities();
			priorities.setPriorityID(Integer.parseInt(request.getParameter("TicketPriority")));
			ticketDetails.setPriorities(priorities);
			
			
			
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.put(Entity.entity(ticketDetails, MediaType.APPLICATION_JSON));
			
			String message = res.readEntity(String.class);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("ServiceEngineerHome.jsp");
			request.setAttribute("message", message);
			dispatcher.forward(request, response);
		}
		else if(option.equals("checkAverageSeverity"))
		{
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/ServiceEngineer").path("checkAverageSeverity");
			
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.get();
			
			String jsonSeverityList = res.readEntity(String.class);
			
			
			Type collectionType = new TypeToken<List<String>>() {
			}.getType();
			List<TicketDetails> details = new Gson().fromJson(jsonSeverityList, collectionType);
		
			System.out.println("jsonSeverity-"+jsonSeverityList + " details-"+details);
			
			WebTarget webTarget2 = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/User").path("getPriorities");
			Invocation.Builder invocationBuilder2 = webTarget2.request(MediaType.APPLICATION_JSON);
			Response res2 = invocationBuilder2.get();
			
			String jsonPriorities = res2.readEntity(String.class);
			System.out.println(jsonPriorities);
			Type collectionType2 = new TypeToken<List<Priorities>>() {
			}.getType();
			List<Priorities> priorityList = new Gson().fromJson(jsonPriorities, collectionType2);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("TicketStatistics.jsp");
			request.setAttribute("averageSeverityList", details);
			request.setAttribute("priorities", priorityList);
			request.setAttribute("option", "checkAverageSeverity");
			requestDispatcher.forward(request, response);
		}
		else if(option.equals("checkReportStatistics"))
		{
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/ServiceEngineer").path("checkReportStatistics");
			
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.get();
			
			String jsonStatistics = res.readEntity(String.class);
			
			System.out.print("jsonStatistics-"+jsonStatistics);
			
			Type collectionType = new TypeToken<List<String>>() {
			}.getType();
			List<TicketDetails> statistics = new Gson().fromJson(jsonStatistics, collectionType);
			
			WebTarget webTarget2 = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/ServiceEngineer").path("getServiceEngineers");
			Invocation.Builder invocationBuilder2 = webTarget2.request(MediaType.APPLICATION_JSON);
			Response res2 = invocationBuilder2.get();
			
			String jsonServiceEngineers = res2.readEntity(String.class);
			System.out.println("jsonServiceEngineers"+jsonServiceEngineers);
			Type collectionType2 = new TypeToken<List<ServiceEngineerDetails>>() {
			}.getType();
			List<ServiceEngineerDetails> engineerDetails = new Gson().fromJson(jsonServiceEngineers, collectionType2);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("TicketStatistics.jsp");
			request.setAttribute("engineerReportStatistics", statistics);
			request.setAttribute("engineers", engineerDetails);
			request.setAttribute("option", "getReportStatisticsPerServiceEngineer");
			requestDispatcher.forward(request, response);
		}
		else if(option.equals("checkAging"))
		{
			WebTarget webTarget = client.target("http://localhost:8080/ProjectServiceTicketResolutionSystem/webapi/ServiceEngineer").path("checkAgingOfOpenTickets");
			
			HttpSession session = request.getSession(false);
			LoginCredentials credentials = new LoginCredentials();
			credentials.setUsername((String) session.getAttribute("username"));
			
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response res = invocationBuilder.post(Entity.entity(credentials, MediaType.APPLICATION_JSON));
			
			List<Object []> jsonAge = res.readEntity(new GenericType<List<Object []>>() {});
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("TicketStatistics.jsp");
			request.setAttribute("AgeOfOpenTickets", jsonAge);
			request.setAttribute("option", "checkAgingOfOpenTickets");
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
