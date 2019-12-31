package interfaces;

import java.util.List;

import bean.Departments;
import bean.LoginCredentials;
import bean.Roles;
import bean.ServiceEngineerDetails;

public interface AdminServiceInterface {

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
