package BusinessClasses;

import java.util.List;
import java.util.Random;

import BeanClasses.Departments;
import BeanClasses.LoginCredentials;
import BeanClasses.Priorities;
import BeanClasses.ServiceEngineerDetails;
import BeanClasses.TicketDetails;
import DAOClasses.UserDAO;

public class UserOperations {
	
	public List<Departments> getDepartments()
	{
		return new UserDAO().getDepartments();
	}
	
	public List<Priorities> getPriorities()
	{
		return new UserDAO().getPriorities();
	}
	
	public void submitTicket(TicketDetails details)
	{
		new UserDAO().submitTicket(details);
	}
	
	public ServiceEngineerDetails checkWork(TicketDetails details)
	{
		return null;
	}
	
	public String generateTicketID()
	{
		long a = (long) Math.pow(10, 10);
		a = a + (long) (new Random().nextInt(999999999));
		String r = "CMIT" + Long.toString(a);
		return r;
	}

	public int getWhatToWorkOn(TicketDetails details) {
		return 0;
	}

	public void changeTicketStatus() {
		
	}

	public ServiceEngineerDetails getMostRecentlyLowPriorityTicketAppointedServiceEngineer(TicketDetails details) {
		return new UserDAO().getMostRecentlyLowPriorityTicketAppointedServiceEngineer(details);
	}

	public List<TicketDetails> getTickets(LoginCredentials credentials) {
		return new UserDAO().getTickets(credentials);
	}

}
