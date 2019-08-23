package com.miniproject.ProjectServiceTicketResolutionSystem;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import BeanClasses.Departments;
import BeanClasses.LoginCredentials;
import BeanClasses.Roles;
import BeanClasses.ServiceEngineerDetails;
import BusinessClasses.AdminOperations;
import BusinessClasses.LoginOperations;
import BusinessClasses.UserOperations;

@Path("Admin")
public class Admin {
	
	@POST
    @Path("registerUser")
    @Produces(MediaType.APPLICATION_JSON)
    public String registerUser(String json) {
    	LoginOperations loginOperations = new LoginOperations();
    	LoginCredentials credentials = new Gson().fromJson(json, LoginCredentials.class);
    	System.out.println(credentials.getUsername());
    	if(loginOperations.checkUserExists(credentials))
    	{
    		return "UsernameAlreadyExists";
    	}
    	else
    	{
    		loginOperations.addUser(credentials);
    		return "Registered";
    	}
    }
	
	@GET
	@Path("getDepartments")
	public List<Departments> getDepartments()
	{
		return new UserOperations().getDepartments();
	}
	
	@POST
	@Path("registerServiceEngineer")
	public String registerServiceEngineer(String json)
	{
		AdminOperations adminOperations = new AdminOperations();
		LoginCredentials credentials = new Gson().fromJson(json, LoginCredentials.class);
		
		if(new LoginOperations().checkUserExists(credentials))
		{
			return "Username already exists";
		}
		else
		{
			adminOperations.addServiceEngineer(credentials);
			return "Registered";
		}
	}
	
	@POST
	@Path("insertIntoServiceEngineerDetails")
	public String insertIntoServiceEngineerDetails(String json)
	{
		AdminOperations adminOperations = new AdminOperations();
		ServiceEngineerDetails engineerDetails = new Gson().fromJson(json, ServiceEngineerDetails.class);
		
		adminOperations.insertIntoServiceEngineerDetails(engineerDetails);
		
		return engineerDetails.getDepartments().getDepartmentName();
	}
	
	@GET
	@Path("getRoles")
	public List<Roles> getRoles()
	{
		return new AdminOperations().getRoles();
	}
	
	@POST
	@Path("getUsers")
	public List<LoginCredentials> getUsers(String json)
	{
		AdminOperations adminOperations = new AdminOperations();
		Roles roles = new Gson().fromJson(json, Roles.class);
		
		return adminOperations.getUsers(roles);
	}
	
	@GET
	@Path("getServiceEngineers")
	public List<ServiceEngineerDetails> getServiceengineerDetails()
	{
		return new AdminOperations().getServiceEngineers();
	}
	
	@POST
	@Path("deleteServiceEngineer")
	public void deleteServiceEngineer(String json)
	{
		AdminOperations adminOperations = new AdminOperations();
		ServiceEngineerDetails engineerDetails = new Gson().fromJson(json, ServiceEngineerDetails.class);
		adminOperations.deleteServiceEngineer(engineerDetails);
	}
	
	
	@POST
	@Path("deleteUser")
	public void deleteUser(String json)
	{
		AdminOperations adminOperations = new AdminOperations();
		LoginCredentials loginCredentials = new Gson().fromJson(json, LoginCredentials.class);
		adminOperations.deleteUser(loginCredentials);
	}
}
