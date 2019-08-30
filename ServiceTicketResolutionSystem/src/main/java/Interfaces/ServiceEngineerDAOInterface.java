package Interfaces;

import java.util.List;

import BeanClasses.LoginCredentials;
import BeanClasses.Priorities;
import BeanClasses.ServiceEngineerDetails;
import BeanClasses.TicketDetails;

public interface ServiceEngineerDAOInterface {

	public List<TicketDetails> getServiceEngineerTickets(LoginCredentials credentials);
	
	public void changeToClosed(String ticketID, long ID);
	
	public void changeToWorking(String ticketID, long ID);
	
	public void incrementTicketsWorkedOn(long id);
	
	public List<Priorities> getPriorities();
	
	public LoginCredentials updateTicketPriority(TicketDetails ticketDetails);
	
	public boolean checkIfTicketExists(TicketDetails ticketDetails);
	
	public List<String> calculateAverageSeverity(List<Priorities> priorities);
	
	public void changeToPending(String ticketID, long ID);
	
	public List<TicketDetails> getServiceEngineerOpenTickets(LoginCredentials credentials);
	
	public List<ServiceEngineerDetails> getServiceEngineers();
	
	public List<String> getReportStatisticsPerServiceEngineer(List<ServiceEngineerDetails> serviceEngineers);
	
	public List<Object[]> getAverageAgeOfOpenTickets(LoginCredentials credentials);
}
