package rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bean.LoginCredentials;
import bean.ServiceEngineerDetails;
import bean.TicketDetails;
import business.ServiceEngineerOperations;

@RestController
@RequestMapping("/serviceEngineer")
public class ServiceEngineerRestController {

	@Autowired
	ServiceEngineerOperations engineerOperations;

	@RequestMapping(value = "/tickets", method = RequestMethod.POST)
	public List<TicketDetails> getServiceEngineerTickets(@RequestBody LoginCredentials credentials) {
		return engineerOperations.getServiceEngineerTickets(credentials);
	}

	@RequestMapping(value = "/averageSeverity", method = RequestMethod.GET)
	public List<String> calculateAverageSeverity() {
		return engineerOperations.calculateAverageSeverity();
	}

	@RequestMapping(value = "/reportStatistics", method = RequestMethod.GET)
	public List<String> calculateReportStatistics() {
		return engineerOperations.calculateReportStatisticsPerServiceEngineer();
	}

	@RequestMapping(value = "/serviceEngineers", method = RequestMethod.GET)
	public List<ServiceEngineerDetails> getServiceEngineers() {
		return engineerOperations.getServiceEngineers();
	}

	@RequestMapping(value = "/aging", method = RequestMethod.POST)
	public List<Object[]> getAgingList(@RequestBody LoginCredentials credentials) {
		return engineerOperations.getAgingList(credentials);
	}

	@RequestMapping(value = "/resolveTicket", method = RequestMethod.POST)
	public String closeTicket(@RequestBody LoginCredentials credentials) {
		return engineerOperations.closeTicket(credentials);
	}

	@RequestMapping(value = "/updatePriority", method = RequestMethod.POST)
	public String updateTicketAndStatus(@RequestBody TicketDetails ticketDetails) {
		return engineerOperations.updateTicketPriority(ticketDetails);
	}

}
