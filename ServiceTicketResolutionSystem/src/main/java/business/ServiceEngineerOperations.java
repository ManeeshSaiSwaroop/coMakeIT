package business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bean.LoginCredentials;
import bean.ServiceEngineerDetails;
import bean.TicketDetails;
import service.ServiceEngineerService;

@Component
public class ServiceEngineerOperations {

	@Autowired
	ServiceEngineerService dao;

	public List<TicketDetails> getServiceEngineerTickets(LoginCredentials credentials) {
		return dao.getServiceEngineerTickets(credentials);
	}

	/*
	 * This function is called when the serviceEngineer closes a ticket And the
	 * function is as follows: First we get the list of open tickets currently
	 * assigned to the service engineer, If he has only one open ticket, then we
	 * close that ticket If he has more than one, then we also set the next
	 * ticket(ordered by priority and dates) to working
	 */
	public String closeTicket(LoginCredentials credentials) {
		List<TicketDetails> list = dao.getServiceEngineerOpenTickets(credentials);
		if (list.size() == 0)
			return "All the Tickets Assigned to you have been closed already";
		else if (list.size() == 1) {
			dao.changeToClosed(list.get(0).getTicketID(), list.get(0).getDetails().getID());
			dao.incrementTicketsWorkedOn(list.get(0).getDetails().getID());
		} else {
			dao.changeToClosed(list.get(0).getTicketID(), list.get(0).getDetails().getID());
			dao.incrementTicketsWorkedOn(list.get(0).getDetails().getID());
			dao.changeToWorking(list.get(1).getTicketID(), list.get(0).getDetails().getID());
		}
		return "Ticket " + list.get(0).getTicketID() + " has been closed!!!";
	}

	/*
	 * Here we check if the entered ticketID exists, if yes, then we change the
	 * priority and return the credentials object to sortOutStatus Check
	 * sortOutStatus to know how it works
	 */
	public String updateTicketPriority(TicketDetails ticketDetails) {		
		if (dao.checkIfTicketExists(ticketDetails)) {
			sortOutStatus(dao.updateTicketPriority(ticketDetails));
			return "Priority of the Mentioned Ticket has been updated";
		}
		return "Enter a valid TicketID";
	}

	public List<String> calculateAverageSeverity() {
		return dao.calculateAverageSeverity(dao.getPriorities());
	}

	/*
	 * Gets the list of service engineer open tickets and changes the first ticket's
	 * ticketStatus which are ordered by priority, dates, etc. to Working
	 */
	public void sortOutStatus(LoginCredentials credentials) {
		List<TicketDetails> list = dao.getServiceEngineerOpenTickets(credentials);
		dao.changeToWorking(list.get(0).getTicketID(), list.get(0).getDetails().getID());
	}

	public List<String> calculateReportStatisticsPerServiceEngineer() {
		return dao.getReportStatisticsPerServiceEngineer(dao.getServiceEngineers());
	}

	public List<ServiceEngineerDetails> getServiceEngineers() {
		return dao.getServiceEngineers();
	}

	public List<Object[]> getAgingList(LoginCredentials credentials) {
		return dao.getAverageAgeOfOpenTickets(credentials);
	}

}
