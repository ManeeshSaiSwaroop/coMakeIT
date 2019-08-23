package BusinessClasses;

import java.util.List;
import java.util.Random;

import BeanClasses.LoginCredentials;
import BeanClasses.Roles;
import BeanClasses.ServiceEngineerDetails;
import DAOClasses.AdminDAO;

public class AdminOperations {

	public void addServiceEngineer(LoginCredentials credentials) {
		new AdminDAO().registerServiceEngineer(credentials);
	}

	public void insertIntoServiceEngineerDetails(ServiceEngineerDetails engineerDetails) {
		engineerDetails.setID(generateServiceEngineerID());
		new AdminDAO().insertIntoServiceEngineerDetails(engineerDetails);
	}

	public long generateServiceEngineerID()
	{
		long a = (long) Math.pow(10, 10);
		a = a + (long) (new Random().nextInt(999999999));
		return a;
	}

	public List<Roles> getRoles() {
		return new AdminDAO().getRoles();
	}

	public List<LoginCredentials> getUsers(Roles roles) {
		return new AdminDAO().getUsers(roles);
	}

	public List<ServiceEngineerDetails> getServiceEngineers() {
		List<ServiceEngineerDetails> list = new AdminDAO().getServiceEngineers();
		
		return list;
	}

	public void deleteServiceEngineer(ServiceEngineerDetails engineerDetails) {
		new AdminDAO().deleteServiceEngineer(engineerDetails);
	}

	public void deleteUser(LoginCredentials loginCredentials) {
		new AdminDAO().deleteUser(loginCredentials);
	}
}
