package com.miniproject.ProjectServiceTicketResolutionSystem;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import com.google.gson.Gson;

import BeanClasses.LoginCredentials;
import BeanClasses.Priorities;
import BeanClasses.ServiceEngineerDetails;
import BeanClasses.TicketDetails;
import BusinessClasses.ServiceEngineerOperations;

@Path("ServiceEngineer")
public class ServiceEngineer {

	@POST
	@Path("viewTickets")
	public List<TicketDetails> viewTickets(String json)
	{
		LoginCredentials credentials = new Gson().fromJson(json, LoginCredentials.class);
		return new ServiceEngineerOperations().getTickets(credentials);
	}
	
	@POST
	@Path("resolveTickets")
	public TicketDetails resolveTickets(String json)
	{
		LoginCredentials credentials = new Gson().fromJson(json, LoginCredentials.class);
		List<TicketDetails> ticketDetails = new ServiceEngineerOperations().getTickets(credentials);
		if(ticketDetails.size()!=0)
			return ticketDetails.get(0);
		else
		{
			TicketDetails ticketDetails2 = new TicketDetails();
			ticketDetails2.setTicketID("0");
			return ticketDetails2;
		}
	}
	
	@PUT
	@Path("closeTicket")
	public String closeTicket(String json)
	{
		LoginCredentials credentials = new Gson().fromJson(json, LoginCredentials.class);
		String message = new ServiceEngineerOperations().closeTicket(credentials);
		return message;
	}
	
	@GET
	@Path("changePriority")
	public List<Priorities> getPriorities()
	{
		return new ServiceEngineerOperations().getPriorities();
	}
	
	@PUT
	@Path("updatePriority")
	public String updatePriority(String json)
	{
		TicketDetails ticketDetails = new Gson().fromJson(json, TicketDetails.class);
		String message = new ServiceEngineerOperations().updateTicketPriority(ticketDetails);
		return message;
	}
	
	@GET
	@Path("checkAverageSeverity")
	public List<String> getAverageSeverityList()
	{
		return new ServiceEngineerOperations().getAverageSeveritylist();
	}
	
	@GET
	@Path("checkReportStatistics")
	public List<String> getReportStatisticsPerServiceEngineer()
	{
		System.out.println("Test");
		return new ServiceEngineerOperations().getReportStatisticsPerServiceEngineer();
	}
	
	@GET
	@Path("getServiceEngineers")
	public List<ServiceEngineerDetails> getServiceEngineers()
	{
		return new ServiceEngineerOperations().getServiceEngineers();
	}
	
	@POST
	@Path("checkAgingOfOpenTickets")
	public List<Object[]> getAverageAgeOfOpenTickets(String json)
	{
		LoginCredentials credentials = new Gson().fromJson(json, LoginCredentials.class);
		return new ServiceEngineerOperations().getAverageAgeOfOpenTickets(credentials);
	}
}
