package BusinessLogic;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Beans.Departments;
import Beans.LoginCredentials;
import Beans.Roles;
import Beans.ServiceEngineerDetails;
import Services.AdminService;

@Component
public class AdminOperations {

	@Autowired
	AdminService dao;

	public String addUser(LoginCredentials user) {
		return dao.registerUser(user);
	}

	public String addServiceEngineer(LoginCredentials credentials) {
		return dao.registerServiceEngineer(credentials);
	}

	public String insertIntoServiceEngineerDetails(ServiceEngineerDetails engineerDetails) {
		dao.insertIntoServiceEngineerDetails(engineerDetails);
		return "done";
	}

	public List<Roles> getRoles() {
		return dao.getRoles();
	}

	public List<LoginCredentials> getUsers() {
		return dao.getUsers();
	}

	public List<ServiceEngineerDetails> getServiceEngineerDetails() {
		return dao.getServiceEngineers();
	}

	public String deleteServiceEngineer(ServiceEngineerDetails engineerDetails) {
		return dao.deleteServiceEngineer(engineerDetails);
	}

	public String deleteUser(LoginCredentials loginCredentials) {
		return dao.deleteUser(loginCredentials);
	}

	public String addDepartment(Departments department) {
		return dao.addDepartment(department);
	}
}
