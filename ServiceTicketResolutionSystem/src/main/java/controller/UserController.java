package controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import bean.Departments;
import bean.LoginCredentials;
import bean.Priorities;
import bean.ServiceEngineerDetails;
import bean.TicketDetails;

@Controller
public class UserController {

	@Autowired
	Environment environment;

	/*
	 * It gets the list of departments and priorities and forwards it to
	 * SubmitTicketForm
	 */
	@RequestMapping(value = "/raiseTicket")
	public ModelAndView ticketRaised() {
		String port = environment.getProperty("local.server.port");
		RestTemplate restTemplate = new RestTemplate();
		final String url1 = "http://localhost:" + port + "/user/departments";
		final String url2 = "http://localhost:" + port + "/user/priorities";
		ResponseEntity<List<Departments>> departmentsResponse = restTemplate.exchange(url1, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Departments>>() {
				});
		ResponseEntity<List<Priorities>> prioritiesResponse = restTemplate.exchange(url2, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Priorities>>() {
				});
		List<Departments> departments = departmentsResponse.getBody();
		List<Priorities> priorities = prioritiesResponse.getBody();
		ModelAndView mv = new ModelAndView("/SubmitTicketForm");
		mv.addObject("departments", departments);
		mv.addObject("priorities", priorities);
		return mv;
	}

	/*
	 * Assigns a service engineer for the ticket raised by the user
	 */
	@RequestMapping(value = "/assignTicket")
	public ModelAndView assignServiceEngineer(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String port = environment.getProperty("local.server.port");
		RestTemplate restTemplate = new RestTemplate();
		TicketDetails ticketDetails = new TicketDetails();
		LoginCredentials credentials = new LoginCredentials();
		ServiceEngineerDetails serviceEngineerDetails = new ServiceEngineerDetails();
		Departments departments = new Departments();
		Priorities priorities = new Priorities();
		credentials.setUsername((String) session.getAttribute("username"));
		departments.setDepartmentID(Integer.parseInt(request.getParameter("Department")));
		serviceEngineerDetails.setDepartments(departments);
		ticketDetails.setDetails(serviceEngineerDetails);
		ticketDetails.setProblemDescription(request.getParameter("ProblemDescription"));
		ticketDetails.setRequestedEndDate(request.getParameter("RequestedEndDate"));
		priorities.setPriorityID(Integer.parseInt(request.getParameter("TicketPriority")));
		ticketDetails.setPriorities(priorities);
		ticketDetails.setCredentials(credentials);
		final String url = "http://localhost:" + port + "/user/assignTicket";
		String message = restTemplate.postForObject(url, ticketDetails, String.class);
		ModelAndView mv = new ModelAndView("/UserHome");
		mv.addObject("message", message);
		return mv;
	}

	/*
	 * All the user tickets are extracted and forwarded to userTickets.jsp where he
	 * can view his tickets
	 */
	@RequestMapping(value = "/tickets")
	public ModelAndView viewTickets(HttpSession session) {
		String port = environment.getProperty("local.server.port");
		RestTemplate restTemplate = new RestTemplate();
		TicketDetails ticketDetails = new TicketDetails();
		LoginCredentials credentials = new LoginCredentials();
		credentials.setUsername((String) session.getAttribute("username"));
		ticketDetails.setCredentials(credentials);
		final String url = "http://localhost:" + port + "/user/tickets";
		HttpEntity<TicketDetails> requestEntity = new HttpEntity<>(ticketDetails);
		ResponseEntity<List<TicketDetails>> ticketsResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<List<TicketDetails>>() {
				});
		List<TicketDetails> userTickets = ticketsResponse.getBody();
		ModelAndView mv = new ModelAndView("/UserTickets");
		mv.addObject("userTickets", userTickets);
		return mv;
	}

}
