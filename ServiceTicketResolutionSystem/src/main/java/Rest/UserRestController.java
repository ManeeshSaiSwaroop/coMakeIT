package Rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import Beans.Departments;
import Beans.Priorities;
import Beans.TicketDetails;
import BusinessLogic.UserOperations;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	UserOperations userOperations;

	@RequestMapping(value = "/getDepartments", method = RequestMethod.GET)
	public List<Departments> getDepartments() {
		return userOperations.getDepartments();
	}

	@RequestMapping(value = "/getPriorities", method = RequestMethod.GET)
	public List<Priorities> getPriorities() {
		return userOperations.getPriorities();
	}

	@RequestMapping(value = "/assignServiceEngineer", method = RequestMethod.POST)
	public String assignServiceEngineer(@RequestBody TicketDetails ticketDetails) {
		userOperations.submitTicket(ticketDetails);
		return "Ticket raised successfully";
	}

	@RequestMapping(value = "/getTickets", method = RequestMethod.POST)
	public List<TicketDetails> getUserTickets(@RequestBody TicketDetails ticketDetails) {
		return userOperations.getUserTickets(ticketDetails);
	}
}
