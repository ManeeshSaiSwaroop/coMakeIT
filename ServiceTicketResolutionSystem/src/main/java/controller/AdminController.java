package controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import bean.Departments;
import bean.LoginCredentials;
import bean.Roles;
import bean.ServiceEngineerDetails;

@Controller
public class AdminController {

	@Autowired
	Environment environment;

	/*
	 * It is invoked when admin is registering a user
	 */
	@RequestMapping(value = "/registerUser")
	public ModelAndView registerUser(LoginCredentials credentials) {
		RestTemplate restTemplate = new RestTemplate();
		String port = environment.getProperty("local.server.port");
		final String url = "http://localhost:" + port + "/admin/registerUser";
		String response = restTemplate.postForObject(url, credentials, String.class);
		ModelAndView mv = new ModelAndView("/Admin");
		if (response.equals("Username already exists, try a different one")) {
			mv = new ModelAndView("/Register");
		}
		mv.addObject("message", response);
		return mv;
	}

	/*
	 * Redirects the admin to the user registration page where in the admin can
	 * register a user
	 */
	@RequestMapping(value = "/userRegistration")
	public String directToUserRegistrationPage() {
		return "Register";
	}

	/*
	 * It is invoked when the admin clicks register service engineer, It gets the
	 * departments for service engineer registration form where in the admin can
	 * select the department required for the service engineer and redirects to
	 * Service engineer registration form
	 */
	@RequestMapping(value = "/engineerDepartments")
	public ModelAndView getDepartmentsForServiceEngineerRegistration() {
		String port = environment.getProperty("local.server.port");
		RestTemplate restTemplate = new RestTemplate();
		final String url = "http://localhost:" + port + "/user/departments";
		ResponseEntity<List<Departments>> departmentsResponse = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Departments>>() {
				});
		List<Departments> departments = departmentsResponse.getBody();
		ModelAndView mv = new ModelAndView("/RegisterServiceEngineer");
		mv.addObject("departments", departments);
		return mv;
	}

	/*
	 * It is invoked when the admin wants to register a service engineer
	 */
	@RequestMapping(value = "/registerServiceEngineer")
	public ModelAndView registerServiceEngineer(LoginCredentials credentials, Departments departments) {
		String port = environment.getProperty("local.server.port");
		ServiceEngineerDetails serviceEngineerDetails = new ServiceEngineerDetails();
		serviceEngineerDetails.setCredentials(credentials);
		serviceEngineerDetails.setDepartments(departments);
		RestTemplate restTemplate = new RestTemplate();
		final String url1 = "http://localhost:" + port + "/admin/registerServiceEngineer1";
		final String url2 = "http://localhost:" + port + "/admin/registerServiceEngineer2";
		String response = restTemplate.postForObject(url1, credentials, String.class);
		ModelAndView mv = new ModelAndView("/Admin");
		if (response.equals("Username already exists, try a different one")) {
			mv = new ModelAndView("/RegisterServiceEngineer");
			final String url = "http://localhost:" + port + "/user/departments";
			ResponseEntity<List<Departments>> departmentsResponse = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Departments>>() {
					});
			List<Departments> departmentsList = departmentsResponse.getBody();
			mv.addObject("departments", departmentsList);
		} else {
			@SuppressWarnings("unused")
			String confirmation = restTemplate.postForObject(url2, serviceEngineerDetails, String.class);
		}
		mv.addObject("message", response);
		return mv;
	}

	/*
	 * Get the roles for viewing that particular role users
	 */
	@RequestMapping(value = "/userRoles")
	public ModelAndView getRoles() {
		String port = environment.getProperty("local.server.port");
		RestTemplate restTemplate = new RestTemplate();
		final String url = "http://localhost:" + port + "/admin/roles";
		ResponseEntity<List<Roles>> rolesResponse = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Roles>>() {
				});
		List<Roles> roles = rolesResponse.getBody();
		ModelAndView mv = new ModelAndView("/viewUsers");
		mv.addObject("roles", roles);
		mv.addObject("option", "getRoles");
		return mv;
	}

	/*
	 * It is invoked when admin wants to view users
	 */
	@RequestMapping(value = "/viewUsers")
	public ModelAndView viewUsers(Roles roles) {
		String port = environment.getProperty("local.server.port");
		RestTemplate restTemplate = new RestTemplate();
		final String url1 = "http://localhost:" + port + "/admin/users";
		final String url2 = "http://localhost:" + port + "/admin/serviceEngineers";
		List<ServiceEngineerDetails> serviceEngineers = new ArrayList<ServiceEngineerDetails>();
		List<LoginCredentials> users = new ArrayList<LoginCredentials>();
		if (roles.getRoleID() == 1) {
			ResponseEntity<List<LoginCredentials>> usersResponse = restTemplate.exchange(url1, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<LoginCredentials>>() {
					});
			users = usersResponse.getBody();
		} else if (roles.getRoleID() == 2) {
			ResponseEntity<List<ServiceEngineerDetails>> usersResponse = restTemplate.exchange(url2, HttpMethod.GET,
					null, new ParameterizedTypeReference<List<ServiceEngineerDetails>>() {
					});
			serviceEngineers = usersResponse.getBody();
		} else {
			ResponseEntity<List<LoginCredentials>> usersResponse = restTemplate.exchange(url1, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<LoginCredentials>>() {
					});
			users = usersResponse.getBody();
		}
		ModelAndView mv = new ModelAndView("/viewUsers");
		mv.addObject("users", roles.getRoleID() == 1 ? users : serviceEngineers);
		mv.addObject("option", roles.getRoleID() == 1 ? "viewUsers" : "viewServiceEngineers");
		return mv;
	}

	/*
	 * It is invoked when admin is deleting a user
	 */
	@RequestMapping(value = "/deleteUser")
	public ModelAndView deleteUser(HttpServletRequest request, HttpServletResponse response) {
		String port = environment.getProperty("local.server.port");
		LoginCredentials credentials = new LoginCredentials();
		credentials.setUsername(request.getParameter("Delete"));
		RestTemplate restTemplate = new RestTemplate();
		final String url = "http://localhost:" + port + "/admin/deleteUser";
		String message = restTemplate.postForObject(url, credentials, String.class);
		ModelAndView mv = new ModelAndView("/Admin");
		mv.addObject("message", message);
		return mv;
	}

	/*
	 * It is invoked when admin is deleting a service engineer
	 */
	@RequestMapping(value = "/deleteServiceEngineer")
	public ModelAndView deleteServiceEngineer(HttpServletRequest request, HttpServletResponse response) {
		String port = environment.getProperty("local.server.port");
		ServiceEngineerDetails serviceEngineerDetails = new ServiceEngineerDetails();
		serviceEngineerDetails.setID(Long.parseLong(request.getParameter("Delete")));
		RestTemplate restTemplate = new RestTemplate();
		final String url = "http://localhost:" + port + "/admin/deleteServiceEngineer";
		String message = restTemplate.postForObject(url, serviceEngineerDetails, String.class);
		ModelAndView mv = new ModelAndView("/Admin");
		mv.addObject("message", message);
		return mv;
	}

	/*
	 * It is for admin so that he can view departments and add departments needed
	 */
	@RequestMapping(value = "/departments")
	public ModelAndView getDepartmentsForAddingDepartments() {
		String port = environment.getProperty("local.server.port");
		RestTemplate restTemplate = new RestTemplate();
		final String url = "http://localhost:" + port + "/user/departments";
		ResponseEntity<List<Departments>> departmentsResponse = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Departments>>() {
				});
		List<Departments> departments = departmentsResponse.getBody();
		ModelAndView mv = new ModelAndView("/AdminAlterationPage");
		mv.addObject("departments", departments);
		return mv;
	}

	/*
	 * It is invoked when admin is adding a department
	 */
	@RequestMapping(value = "/addDepartment")
	public ModelAndView addDepartment(Departments department) {
		String port = environment.getProperty("local.server.port");
		RestTemplate restTemplate = new RestTemplate();
		final String url = "http://localhost:" + port + "/admin/addDepartment";
		String message = restTemplate.postForObject(url, department, String.class);
		ModelAndView mv = new ModelAndView("/Admin");
		if (message.equals("departmentID already exists") || message.equals("departmentName already exists")) {
			mv = new ModelAndView("/AdminAlterationPage");
			final String url2 = "http://localhost:" + port + "/user/departments";
			ResponseEntity<List<Departments>> departmentsResponse = restTemplate.exchange(url2, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Departments>>() {
					});
			List<Departments> departments = departmentsResponse.getBody();
			mv.addObject("departments", departments);
			mv.addObject("message", message);
			return mv;
		}
		mv.addObject("message", message);
		return mv;
	}
}
