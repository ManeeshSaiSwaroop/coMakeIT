package com.miniproject.ProjectServiceTicketResolutionSystem;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import BeanClasses.LoginCredentials;
import BusinessClasses.LoginOperations;

@Path("Login")
public class Login {

    @POST
    @Path("validate")
    @Produces(MediaType.APPLICATION_JSON)
    public String validateLogin(String json) {
    	LoginOperations loginOperations = new LoginOperations();
    	LoginCredentials credentials = new Gson().fromJson(json, LoginCredentials.class);
    	System.out.println(credentials.getUsername());
    	if(loginOperations.checkUserExists(credentials))
    	{
    		if(loginOperations.validatePassword(credentials))
    		{
    			return loginOperations.getType(credentials);
    		}
    		else
    		{
    			return "WrongPassword";
    		}
    	}
    	else
    	{
    		return "UserDoesNotExist";
    	}
    }
    
    @POST
    @Path("register")
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
}
