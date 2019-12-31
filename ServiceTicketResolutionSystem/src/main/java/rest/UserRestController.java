package rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bean.Departments;
import bean.Priorities;
import bean.TicketDetails;
import business.UserOperations;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	UserOperations userOperations;

	@RequestMapping(value = "/departments", method = RequestMethod.GET)
	public List<Departments> getDepartments() {
		return userOperations.getDepartments();
	}

	@RequestMapping(value = "/priorities", method = RequestMethod.GET)
	public List<Priorities> getPriorities() {
		return userOperations.getPriorities();
	}

	@RequestMapping(value = "/assignTicket", method = RequestMethod.POST)
	public String assignServiceEngineer(@RequestBody TicketDetails ticketDetails) {
		userOperations.submitTicket(ticketDetails);
		return "Ticket raised successfully";
	}

	@RequestMapping(value = "/tickets", method = RequestMethod.POST)
	public List<TicketDetails> getUserTickets(@RequestBody TicketDetails ticketDetails) {
		return userOperations.getUserTickets(ticketDetails);
	}
}
