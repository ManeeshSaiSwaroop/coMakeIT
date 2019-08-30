package Interfaces;

import java.util.List;

import BeanClasses.Departments;
import BeanClasses.Priorities;
import BeanClasses.ServiceEngineerDetails;
import BeanClasses.TicketDetails;

public interface UserDAOInterface {

	public List<Departments> getDepartments();
	
	public List<Priorities> getPriorities();
	
	public void submitTicket(TicketDetails details);
	
	public List<ServiceEngineerDetails> getCorrespondingEngineerTable(TicketDetails details);
	
	public ServiceEngineerDetails getMostRecentlyLowPriorityTicketAppointedServiceEngineer(TicketDetails details);
	
	public List<TicketDetails> getUserTickets(TicketDetails ticketDetails);
}
