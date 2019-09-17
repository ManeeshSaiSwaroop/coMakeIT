package service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bean.Departments;
import bean.Priorities;
import bean.ServiceEngineerDetails;
import bean.TicketDetails;
import interfaces.UserServiceInterface;
import repository.DepartmentsRepository;
import repository.PrioritiesRepository;
import repository.ServiceEngineerDetailsRepository;
import repository.TicketDetailsRepository;

@Service
public class UserService implements UserServiceInterface {

	@Autowired
	DepartmentsRepository departmentsRepository;

	@Autowired
	PrioritiesRepository prioritiesRepository;

	@Autowired
	TicketDetailsRepository ticketDetailsRepository;

	@Autowired
	ServiceEngineerDetailsRepository serviceEngineerDetailsRepository;

	/*
	 * extracts Departments table
	 */
	public List<Departments> getDepartments() {
		return departmentsRepository.findAll();
	}

	/*
	 * extracts Priorities table
	 */
	public List<Priorities> getPriorities() {
		return prioritiesRepository.findAll();
	}

	/*
	 * First we check if the priority of the raised ticket is more than the priority
	 * of the ticket the service engineer is working on, if yes, then we change the
	 * status of the current ticket to pending and change the status of the raised
	 * ticket to InProgress, if not then the priority of the raised ticket will be
	 * set to Pending
	 */
	public void submitTicket(TicketDetails details) {
		Optional<ServiceEngineerDetails> optionalDetails2 = serviceEngineerDetailsRepository
				.findById(details.getDetails().getID());
		ServiceEngineerDetails details2 = optionalDetails2.get();
		details.setTicketStatus("Pending");
		if (details2.getPriorities().getPriorityID() < details.getPriorities().getPriorityID()) {
			Optional<TicketDetails> optionalChangeStatus = ticketDetailsRepository
					.findById(details2.getCurrentHighPriorityTicketID());
			if (optionalChangeStatus.isPresent()) {
				TicketDetails changeStatus = optionalChangeStatus.get();
				changeStatus.setTicketStatus("Pending");
				ticketDetailsRepository.save(changeStatus);
			}
			details.setTicketStatus("InProgress");
			Optional<Priorities> optionalPriorities = prioritiesRepository
					.findById(details.getPriorities().getPriorityID());
			Priorities priorities = optionalPriorities.get();
			details2.setPriorities(priorities);
		}

		details.setCompletionTime(null);
		if (details.getRequestedEndDate().equals(""))
			details.setRequestedEndDate(null);
		ticketDetailsRepository.save(details);
		details2.setCurrentHighPriorityTicketID(details.getTicketID());
		serviceEngineerDetailsRepository.save(details2);

	}

	/*
	 * Returns a list of service engineers belonging to a particular department
	 */
	public List<ServiceEngineerDetails> getCorrespondingEngineerTable(TicketDetails details) {
		return serviceEngineerDetailsRepository
				.getCorrespondingEngineers(details.getDetails().getDepartments().getDepartmentID());
	}

	/*
	 * Returns a ServiceEngineer who is assigned with the most recently appointed
	 * lower priority tickets of a given department
	 */
	public ServiceEngineerDetails getMostRecentlyLowPriorityTicketAppointedServiceEngineer(TicketDetails details) {
		List<TicketDetails> details3 = ticketDetailsRepository
				.getMostRecentlyLowerPriorityTicketAssignedServiceEngineer(
						details.getDetails().getDepartments().getDepartmentID());
		ServiceEngineerDetails details2 = new ServiceEngineerDetails();
		details2.setID(details3.get(0).getDetails().getID());
		details.setDetails(details2);
		return serviceEngineerDetailsRepository.findById(details.getDetails().getID()).get();
	}

	/*
	 * Returns a list of tickets raised by user
	 */
	public List<TicketDetails> getUserTickets(TicketDetails ticketDetails) {
		List<TicketDetails> list = ticketDetailsRepository.getUserTickets(ticketDetails.getCredentials().getUsername());
		return list;
	}
}
