package com.miniproject.ProjectServiceTicketResolutionSystem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.google.gson.Gson;

import BeanClasses.Departments;
import BeanClasses.LoginCredentials;
import BeanClasses.Priorities;
import BeanClasses.ServiceEngineerDetails;
import BeanClasses.TicketDetails;
import BusinessClasses.UserOperations;
import DAOClasses.UserDAO;

@Path("User")
public class User {
	
	@GET
	@Path("getDepartments")
	public List<Departments> getDepartments()
	{
		return new UserOperations().getDepartments();
	}
	
	@GET
	@Path("getPriorities")
	public List<Priorities> getPriorities()
	{
		return new UserOperations().getPriorities();
	}
	
	@POST
	@Path("submitTicket")
	public String submitTicket(String json)
	{
		UserOperations operations = new UserOperations();
		TicketDetails details = new Gson().fromJson(json, TicketDetails.class);
		int flag=0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		details.setStartTime(dateFormat.format(date));
		details.setDateOfIssue(dateFormat.format(date));
		details.setTicketID(operations.generateTicketID());
		List<ServiceEngineerDetails> details2 = new UserDAO().getCorrespondingEngineerTable(details);
		for(int i=0;i<details2.size();i++)
		{
			if(details2.get(i).getPriorities().getPriorityID()==0)
			{
				details.setDetails(details2.get(i));
				flag=1;
				break;
			}
		}
		if(flag==0)
		{
			details.setDetails(operations.getMostRecentlyLowPriorityTicketAppointedServiceEngineer(details));
			operations.submitTicket(details);
		}
		else
		{
			operations.submitTicket(details);
		}
		return "Submitted";
	}
	
	@POST
	@Path("viewTickets")
	public List<TicketDetails> getTickets(String json)
	{
		LoginCredentials credentials = new Gson().fromJson(json, LoginCredentials.class);
		return new UserOperations().getTickets(credentials);
	}
}
