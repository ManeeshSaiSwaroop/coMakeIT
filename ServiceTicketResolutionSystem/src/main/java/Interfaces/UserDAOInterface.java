package Interfaces;

import java.util.List;

import Beans.Departments;
import Beans.Priorities;
import Beans.ServiceEngineerDetails;
import Beans.TicketDetails;

public interface UserDAOInterface {

	public List<Departments> getDepartments();
	
	public List<Priorities> getPriorities();
	
	public void submitTicket(TicketDetails details);
	
	public List<ServiceEngineerDetails> getCorrespondingEngineerTable(TicketDetails details);
	
	public ServiceEngineerDetails getMostRecentlyLowPriorityTicketAppointedServiceEngineer(TicketDetails details);
	
	public List<TicketDetails> getUserTickets(TicketDetails ticketDetails);
}
