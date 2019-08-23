package BusinessClasses;

import java.util.List;

import BeanClasses.LoginCredentials;
import BeanClasses.Priorities;
import BeanClasses.ServiceEngineerDetails;
import BeanClasses.TicketDetails;
import DAOClasses.ServiceEngineerDAO;

public class ServiceEngineerOperations {

	public List<TicketDetails> getTickets(LoginCredentials credentials) {
		return new ServiceEngineerDAO().getTickets(credentials);
	}

	public String closeTicket(LoginCredentials credentials) {
		List<TicketDetails> list = new ServiceEngineerDAO().getTickets(credentials);
		System.out.print(list.size());
		if(list.size()==0)
			return "All the Tickets Assigned to you have been closed already";
		else if(list.size()==1) {
			new ServiceEngineerDAO().changeToClosed(list.get(0).getTicketID(), list.get(0).getDetails().getID());
			new ServiceEngineerDAO().incrementTicketsWorkedOn(list.get(0).getDetails().getID());
		}
		else
		{
			new ServiceEngineerDAO().changeToClosed(list.get(0).getTicketID(), list.get(0).getDetails().getID());
			new ServiceEngineerDAO().incrementTicketsWorkedOn(list.get(0).getDetails().getID());
			new ServiceEngineerDAO().changeToWorking(list.get(1).getTicketID(), list.get(0).getDetails().getID());
		}
		return "Ticket has been closed!!!";
	}
	
	public List<Priorities> getPriorities() {
		return new ServiceEngineerDAO().getPriorities();
	}

	public String updateTicketPriority(TicketDetails ticketDetails) {
		if(new ServiceEngineerDAO().checkIfTicketExists(ticketDetails))
		{
			new ServiceEngineerDAO().updateTicketPriority(ticketDetails);
			return "Priority of the Mentioned Ticket has been updated";
		}
		
		return "Enter a valid TicketID";
	}

	public List<String> getAverageSeveritylist() {
		return new ServiceEngineerDAO().getAverageSeverity(new ServiceEngineerDAO().getPriorities());
	}
	
	public void sortOutStatus(LoginCredentials credentials) {
		List<TicketDetails> list = new ServiceEngineerDAO().getTicketsWhichAreNotClosed(credentials);
				
		new ServiceEngineerDAO().changeToWorking(list.get(0).getTicketID(), list.get(0).getDetails().getID());
		
	}

	public List<String> getReportStatisticsPerServiceEngineer() {
		return new ServiceEngineerDAO().getReportStatisticsPerServiceEngineer(new ServiceEngineerDAO().getServiceEngineers());
	}

	public List<ServiceEngineerDetails> getServiceEngineers() {
		return new ServiceEngineerDAO().getServiceEngineers();
	}

	public List<Object[]> getAverageAgeOfOpenTickets(LoginCredentials credentials) {
		return new ServiceEngineerDAO().getAverageAgeOfOpenTickets(credentials);
	}

}
