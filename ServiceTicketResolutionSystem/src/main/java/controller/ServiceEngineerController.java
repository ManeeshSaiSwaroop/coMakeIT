package controller;

import java.util.List;
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

import bean.LoginCredentials;
import bean.Priorities;
import bean.ServiceEngineerDetails;
import bean.TicketDetails;

@Controller
public class ServiceEngineerController {

	@Autowired
	Environment environment;

	/*
	 * It is invoked when service engineer clicks on view tickets
	 */
	@RequestMapping("/assignedTickets")
	public ModelAndView viewTickets(HttpSession session) {
		String port = environment.getProperty("local.server.port");
		RestTemplate restTemplate = new RestTemplate();
		LoginCredentials credentials = new LoginCredentials();
		credentials.setUsername((String) session.getAttribute("username"));
		final String url = "http://localhost:" + port + "/serviceEngineer/tickets";
		HttpEntity<LoginCredentials> requestEntity = new HttpEntity<>(credentials);
		ResponseEntity<List<TicketDetails>> ticketsResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<List<TicketDetails>>() {
				});
		List<TicketDetails> serviceEngineerTickets = ticketsResponse.getBody();
		ModelAndView mv = new ModelAndView("/viewServiceEngineerTickets");
		mv.addObject("option", "viewServiceEngineerTickets");
		mv.addObject("serviceEngineerTickets", serviceEngineerTickets);
		return mv;
	}

	/*
	 * It is invoked when service engineer clicks on checkAverageSeverity
	 */
	@RequestMapping("/averageSeverity")
	public ModelAndView getAverageSeverity() {
		String port = environment.getProperty("local.server.port");
		RestTemplate restTemplate = new RestTemplate();
		final String url1 = "http://localhost:" + port + "/serviceEngineer/averageSeverity";
		ResponseEntity<List<String>> ticketsResponse = restTemplate.exchange(url1, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<String>>() {
				});
		List<String> averageListPerPriority = ticketsResponse.getBody();
		final String url2 = "http://localhost:" + port + "/user/priorities";
		ResponseEntity<List<Priorities>> prioritiesResponse = restTemplate.exchange(url2, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Priorities>>() {
				});
		List<Priorities> priorities = prioritiesResponse.getBody();
		ModelAndView mv = new ModelAndView("/TicketStatistics");
		mv.addObject("averageListPerPriority", averageListPerPriority);
		mv.addObject("option", "checkAverageSeverity");
		mv.addObject("priorities", priorities);
		return mv;
	}

	/*
	 * It is invoked when service engineer clicks on checkReportStatistics
	 */
	@RequestMapping(value = "/reportStatistics")
	public ModelAndView getReportStatistics() {
		String port = environment.getProperty("local.server.port");
		RestTemplate restTemplate = new RestTemplate();
		final String url1 = "http://localhost:" + port + "/serviceEngineer/reportStatistics";
		ResponseEntity<List<String>> statisticsResponse = restTemplate.exchange(url1, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<String>>() {
				});
		List<String> statisticsPerServiceEngineer = statisticsResponse.getBody();
		final String url2 = "http://localhost:" + port + "/serviceEngineer/serviceEngineers";
		ResponseEntity<List<ServiceEngineerDetails>> engineersResponse = restTemplate.exchange(url2, HttpMethod.GET,
				null, new ParameterizedTypeReference<List<ServiceEngineerDetails>>() {
				});
		List<ServiceEngineerDetails> serviceEngineerList = engineersResponse.getBody();
		ModelAndView mv = new ModelAndView("/TicketStatistics");
		mv.addObject("engineerReportStatistics", statisticsPerServiceEngineer);
		mv.addObject("option", "getReportStatisticsPerServiceEngineer");
		mv.addObject("engineers", serviceEngineerList);
		return mv;
	}

	/*
	 * It is invoked when service engineer clicks on checkAging
	 */
	@RequestMapping(value = "/aging")
	public ModelAndView checkAging(HttpSession session) {
		String port = environment.getProperty("local.server.port");
		RestTemplate restTemplate = new RestTemplate();
		LoginCredentials credentials = new LoginCredentials();
		credentials.setUsername((String) session.getAttribute("username"));
		HttpEntity<LoginCredentials> requestEntity = new HttpEntity<>(credentials);
		final String url1 = "http://localhost:" + port + "/serviceEngineer/aging";
		ResponseEntity<List<Object[]>> agingResponse = restTemplate.exchange(url1, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<List<Object[]>>() {
				});
		List<Object[]> agingList = agingResponse.getBody();
		ModelAndView mv = new ModelAndView("/TicketStatistics");
		mv.addObject("AgeOfOpenTickets", agingList);
		mv.addObject("option", "checkAgingOfOpenTickets");
		return mv;
	}

	/*
	 * It is invoked when service engineer clicks on resolve tickets where in only
	 * the ticket InProgress will be showed
	 */
	@RequestMapping(value = "/resolveTicket")
	public ModelAndView resolveTickets(HttpSession session) {
		String port = environment.getProperty("local.server.port");
		RestTemplate restTemplate = new RestTemplate();
		LoginCredentials credentials = new LoginCredentials();
		credentials.setUsername((String) session.getAttribute("username"));
		final String url = "http://localhost:" + port + "/serviceEngineer/tickets";
		HttpEntity<LoginCredentials> requestEntity = new HttpEntity<>(credentials);
		ResponseEntity<List<TicketDetails>> ticketsResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<List<TicketDetails>>() {
				});
		List<TicketDetails> serviceEngineerTickets = ticketsResponse.getBody();
		ModelAndView mv = new ModelAndView("/viewServiceEngineerTickets");
		mv.addObject("option", "resolveTickets");
		mv.addObject("TicketDetails",
				serviceEngineerTickets.size() != 0 ? serviceEngineerTickets.get(0) : new TicketDetails());
		mv.addObject("Empty?", serviceEngineerTickets.size() != 0 ? "1" : "2");
		return mv;
	}

	/*
	 * It is invoked when service engineer clicks on close ticket where in the
	 * ticket which is InProgress will be closed
	 */
	@RequestMapping(value = "/closeTicket")
	public ModelAndView closeTicket(HttpSession session) {
		String port = environment.getProperty("local.server.port");
		RestTemplate restTemplate = new RestTemplate();
		LoginCredentials credentials = new LoginCredentials();
		credentials.setUsername((String) session.getAttribute("username"));
		final String url = "http://localhost:" + port + "/serviceEngineer/resolveTicket";
		String message = restTemplate.postForObject(url, credentials, String.class);
		ModelAndView mv = new ModelAndView("/ServiceEngineerHome");
		mv.addObject("message", message);
		return mv;
	}

	/*
	 * Gets the list of priorities and forwards it to UpdateTicketPriorityForm
	 */
	@RequestMapping(value = "/changePriority")
	public ModelAndView changePriority() {
		String port = environment.getProperty("local.server.port");
		RestTemplate restTemplate = new RestTemplate();
		final String url = "http://localhost:" + port + "/user/priorities";
		ResponseEntity<List<Priorities>> prioritiesResponse = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Priorities>>() {
				});
		List<Priorities> priorities = prioritiesResponse.getBody();
		ModelAndView mv = new ModelAndView("/ChangePriority");
		mv.addObject("priorities", priorities);
		return mv;
	}

	/*
	 * updates the ticket priority of the given ticket as mentioned
	 */
	@RequestMapping(value = "/updateTicketPriority")
	public ModelAndView updateTicketPriorityID(TicketDetails ticketDetails, Priorities priorities) {
		String port = environment.getProperty("local.server.port");
		ticketDetails.setPriorities(priorities);
		RestTemplate restTemplate = new RestTemplate();
		final String url = "http://localhost:" + port + "/serviceEngineer/updatePriority";
		String message = restTemplate.postForObject(url, ticketDetails, String.class);
		ModelAndView mv = new ModelAndView("/ServiceEngineerHome");
		mv.addObject("message", message);
		return mv;
	}
}
