package com.example.ServiceTicketResolutionSystem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import BeanClasses.LoginCredentials;
import BeanClasses.ServiceEngineerDetails;
import BeanClasses.TicketDetails;
import BusinessClasses.ServiceEngineerOperations;

@RestController
@RequestMapping("/serviceEngineer")
public class ServiceEngineerRestController {

	@Autowired
	ServiceEngineerOperations engineerOperations;

	@RequestMapping(value = "/getTickets", method = RequestMethod.POST)
	public List<TicketDetails> getServiceEngineerTickets(@RequestBody LoginCredentials credentials) {
		return engineerOperations.getServiceEngineerTickets(credentials);
	}

	@RequestMapping(value = "/getAverageSeverity", method = RequestMethod.GET)
	public List<String> calculateAverageSeverity() {
		return engineerOperations.calculateAverageSeverity();
	}

	@RequestMapping(value = "/getReportStatistics", method = RequestMethod.GET)
	public List<String> calculateReportStatistics() {
		return engineerOperations.calculateReportStatisticsPerServiceEngineer();
	}

	@RequestMapping(value = "/getServiceEngineers", method = RequestMethod.GET)
	public List<ServiceEngineerDetails> getServiceEngineers() {
		return engineerOperations.getServiceEngineers();
	}

	@RequestMapping(value = "/getAgingOfOpenTickets", method = RequestMethod.POST)
	public List<Object[]> getAgingList(@RequestBody LoginCredentials credentials) {
		return engineerOperations.getAgingList(credentials);
	}

	@RequestMapping(value = "/resolveTicket", method = RequestMethod.POST)
	public String closeTicket(@RequestBody LoginCredentials credentials) {
		return engineerOperations.closeTicket(credentials);
	}

	@RequestMapping(value = "/updatePriorityAndStatus", method = RequestMethod.POST)
	public String updateTicketAndStatus(@RequestBody TicketDetails ticketDetails) {
		return engineerOperations.updateTicketPriority(ticketDetails);
	}

}
