package rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bean.Departments;
import bean.LoginCredentials;
import bean.Roles;
import bean.ServiceEngineerDetails;
import business.AdminOperations;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

	@Autowired
	AdminOperations adminOperations;

	@RequestMapping(value = "/registerUser")
	public String addUser(@RequestBody LoginCredentials credentials) {
		return adminOperations.addUser(credentials);
	}

	@RequestMapping(value = "/registerServiceEngineerLoginCredentials")
	public String addServiceEngineer(@RequestBody LoginCredentials credentials) {
		return adminOperations.addServiceEngineer(credentials);
	}

	@RequestMapping(value = "/registerServiceEngineerserviceEngineerDetails")
	public String addServiceEngineerIntoServiceEngineerDetails(
			@RequestBody ServiceEngineerDetails serviceEngineerDetails) {
		return adminOperations.insertIntoServiceEngineerDetails(serviceEngineerDetails);
	}

	@RequestMapping(value = "/getRoles")
	public List<Roles> getRoles() {
		return adminOperations.getRoles();
	}

	@RequestMapping(value = "/getUsers")
	public List<LoginCredentials> getUsers() {
		return adminOperations.getUsers();
	}

	@RequestMapping(value = "/getServiceEngineers")
	public List<ServiceEngineerDetails> getServiceEngineerDetails() {
		return adminOperations.getServiceEngineerDetails();
	}

	@RequestMapping(value = "/DeleteUser")
	public String deleteUser(@RequestBody LoginCredentials credentials) {
		return adminOperations.deleteUser(credentials);
	}

	@RequestMapping(value = "/DeleteServiceEngineer")
	public String deleteServiceEngineer(@RequestBody ServiceEngineerDetails serviceEngineerDetails) {
		return adminOperations.deleteServiceEngineer(serviceEngineerDetails);
	}

	@RequestMapping(value = "/AddDepartment")
	public String addDepartment(@RequestBody Departments department) {
		return adminOperations.addDepartment(department);
	}
}
