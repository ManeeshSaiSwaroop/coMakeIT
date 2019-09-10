package BusinessLogic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Beans.Departments;
import Beans.Priorities;
import Beans.ServiceEngineerDetails;
import Beans.TicketDetails;
import Services.UserService;

@Component
public class UserOperations {

	@Autowired
	UserService dao;

	public List<Departments> getDepartments() {
		return dao.getDepartments();
	}

	public List<Priorities> getPriorities() {
		return dao.getPriorities();
	}

	/*
	 * This function is called when a ticket is raised by a user And the process is
	 * as follows: First we set the Date of Issue to current dateAndTime and
	 * generate ticketID, then we get the list of service engineers of that
	 * department of the ticket, and check if any of them is free, if yes, directly
	 * assign the ticket to them, if no, then we get the service engineer who is
	 * assigned with the most recently assigned low priority ticket and assign him
	 * with the ticket raised
	 */
	public void submitTicket(TicketDetails details) {
		int flag = 0;

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		details.setDateOfIssue(dateFormat.format(date));
//		details.setTicketID(generateTicketID());
		List<ServiceEngineerDetails> serviceEngineerDetails = dao.getCorrespondingEngineerTable(details);

		for (int i = 0; i < serviceEngineerDetails.size(); i++) {
			if (serviceEngineerDetails.get(i).getPriorities().getPriorityID() == 0) {
				details.setStartTime(details.getDateOfIssue());
				details.setDetails(serviceEngineerDetails.get(i));
				flag = 1;
				break;
			}
		}

		if (flag == 0) {
			details.setDetails(dao.getMostRecentlyLowPriorityTicketAppointedServiceEngineer(details));
			details.setStartTime(null);
			dao.submitTicket(details);
		} else {
			dao.submitTicket(details);
		}

	}

	public List<TicketDetails> getUserTickets(TicketDetails ticketDetails) {
		return dao.getUserTickets(ticketDetails);
	}

}
