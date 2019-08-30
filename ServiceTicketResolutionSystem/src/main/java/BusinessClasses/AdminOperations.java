package BusinessClasses;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import BeanClasses.Departments;
import BeanClasses.LoginCredentials;
import BeanClasses.Roles;
import BeanClasses.ServiceEngineerDetails;
import DAOClasses.AdminDAO;

@Component
public class AdminOperations {

	@Autowired
	AdminDAO dao;

	public String addUser(LoginCredentials user) {
		return dao.registerUser(user);
	}

	public String addServiceEngineer(LoginCredentials credentials) {
		return dao.registerServiceEngineer(credentials);
	}

	public String insertIntoServiceEngineerDetails(ServiceEngineerDetails engineerDetails) {
		engineerDetails.setID(generateServiceEngineerID());
		dao.insertIntoServiceEngineerDetails(engineerDetails);
		return "done";
	}

	/*
	 * Generates serviceEngineer ID
	 */
	public long generateServiceEngineerID() {
		long a = (long) Math.pow(10, 10);
		a = a + (long) (new Random().nextInt(999999999));
		return a;
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
