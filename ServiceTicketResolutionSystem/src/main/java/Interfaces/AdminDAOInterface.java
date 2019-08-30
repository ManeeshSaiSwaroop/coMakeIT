package Interfaces;

import java.util.List;

import BeanClasses.Departments;
import BeanClasses.LoginCredentials;
import BeanClasses.Roles;
import BeanClasses.ServiceEngineerDetails;

public interface AdminDAOInterface {

	public String registerUser(LoginCredentials user);
	
	public String registerServiceEngineer(LoginCredentials user);
	
	public void insertIntoServiceEngineerDetails(ServiceEngineerDetails engineerDetails);
	
	public List<Roles> getRoles();
	
	public List<LoginCredentials> getUsers();
	
	public List<ServiceEngineerDetails> getServiceEngineers();
	
	public String deleteServiceEngineer(ServiceEngineerDetails engineerDetails);
	
	public String deleteUser(LoginCredentials loginCredentials);
	
	public String addDepartment(Departments department);
}
