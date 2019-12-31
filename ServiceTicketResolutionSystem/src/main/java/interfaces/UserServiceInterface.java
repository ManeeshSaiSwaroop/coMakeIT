package interfaces;

import java.util.List;

import bean.Departments;
import bean.Priorities;
import bean.ServiceEngineerDetails;
import bean.TicketDetails;

public interface UserServiceInterface {

	public List<Departments> getDepartments();
	
	public List<Priorities> getPriorities();
	
	public void submitTicket(TicketDetails details);
	
	public List<ServiceEngineerDetails> getCorrespondingEngineerTable(TicketDetails details);
	
	public ServiceEngineerDetails getMostRecentlyLowPriorityTicketAppointedServiceEngineer(TicketDetails details);
	
	public List<TicketDetails> getUserTickets(TicketDetails ticketDetails);
}
