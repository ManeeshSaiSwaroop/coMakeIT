package service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bean.LoginCredentials;
import bean.Priorities;
import bean.ServiceEngineerDetails;
import bean.TicketDetails;
import interfaces.ServiceEngineerServiceInterface;
import repository.LoginCredentialsRepository;
import repository.PrioritiesRepository;
import repository.ServiceEngineerDetailsRepository;
import repository.TicketDetailsRepository;

@Service
public class ServiceEngineerService implements ServiceEngineerServiceInterface {

	@Autowired
	TicketDetailsRepository ticketDetailsRepository;

	@Autowired
	PrioritiesRepository prioritiesRepository;

	@Autowired
	ServiceEngineerDetailsRepository serviceEngineerDetailsRepository;

	@Autowired
	LoginCredentialsRepository loginCredentialsRepository;

	/*
	 * Extracts all the tickets belonging to that service engineer
	 */
	public List<TicketDetails> getServiceEngineerTickets(LoginCredentials credentials) {
		List<TicketDetails> list = ticketDetailsRepository.getServiceEngineerTickets(credentials.getUsername());
		return list;
	}

	/*
	 * This function is called when changing the ticketStatus to closed We set the
	 * ticketStatus to closed, and also set CompletionTime as Current time The
	 * CurrentHighPriorityTicketID and the Current ticket Priority to ) to indicate
	 * that the service engineer is free This function will be followed by
	 * changeToWorking if the serviceEngineer has another ticket assigned to him
	 */
	public void changeToClosed(String ticketID, long ID) {
		Optional<TicketDetails> optionalTicketDetails = ticketDetailsRepository.findById(ticketID);
		TicketDetails ticketDetails = optionalTicketDetails.get();
		Optional<ServiceEngineerDetails> optionalEngineerDetails = serviceEngineerDetailsRepository.findById(ID);
		ServiceEngineerDetails engineerDetails = optionalEngineerDetails.get();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		ticketDetails.setTicketStatus("Closed");
		ticketDetails.setCompletionTime(dateFormat.format(date));
		engineerDetails.setCurrentHighPriorityTicketID("0");
		Optional<Priorities> priorities = prioritiesRepository.findById(0);
		engineerDetails.setPriorities(priorities.get());
		ticketDetailsRepository.save(ticketDetails);
		serviceEngineerDetailsRepository.save(engineerDetails);
	}

	/*
	 * This function changes the ticketStatus to InProgress and set Start Time to
	 * current dateAndtime if null and then the set the CurrentHighPriorityTicketId
	 * to the current ticketID and the CurrentTicketPriority to the priority of this
	 * ticket in progress
	 */
	public void changeToWorking(String ticketID, long ID) {
		Optional<TicketDetails> optionalTicketDetails = ticketDetailsRepository.findById(ticketID);
		TicketDetails ticketDetails = optionalTicketDetails.get();
		ticketDetails.setTicketStatus("InProgress");
		if (ticketDetails.getStartTime() == null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			ticketDetails.setStartTime(dateFormat.format(date));
		}
		Optional<ServiceEngineerDetails> optionalEngineerDetails = serviceEngineerDetailsRepository.findById(ID);
		ServiceEngineerDetails engineerDetails = optionalEngineerDetails.get();
		engineerDetails.setCurrentHighPriorityTicketID(ticketDetails.getTicketID());
		Optional<Priorities> priorities = prioritiesRepository.findById(ticketDetails.getPriorities().getPriorityID());
		engineerDetails.setPriorities(priorities.get());
		ticketDetails.setDetails(engineerDetails);
		ticketDetailsRepository.save(ticketDetails);
	}

	/*
	 * This function is called when a ServiceEngineer closes a ticket This
	 * increments the ticketsWorkedOn of that respective service engineer
	 */
	public void incrementTicketsWorkedOn(long id) {
		Optional<ServiceEngineerDetails> optionalEngineerDetails = serviceEngineerDetailsRepository.findById(id);
		ServiceEngineerDetails engineerDetails = optionalEngineerDetails.get();
		engineerDetails.setTotalTicketsWorkedOn(engineerDetails.getTotalTicketsWorkedOn() + 1);
		serviceEngineerDetailsRepository.save(engineerDetails);
	}

	/*
	 * Extracts the Priorities table
	 */
	public List<Priorities> getPriorities() {
		return prioritiesRepository.findAll();
	}

	/*
	 * Changes the priority of the given ticketID as given and set ticketStatus to
	 * Pending, Get the list of tickets assigned to service engineer ordered by
	 * status, priority, dates, etc, Set the First ticket to InProgress and updates
	 * the CurrentHighTicketPriorityId and CurrentTicketPriority as required
	 */
	public LoginCredentials updateTicketPriority(TicketDetails ticketDetails) {
		Optional<TicketDetails> optionalTicketDetails2 = ticketDetailsRepository.findById(ticketDetails.getTicketID());
		TicketDetails ticketDetails2 = optionalTicketDetails2.get();
		Optional<Priorities> optionalPriorities = prioritiesRepository
				.findById(ticketDetails.getPriorities().getPriorityID());
		Priorities priorities = optionalPriorities.get();
		ticketDetails2.setPriorities(priorities);
		String ticketStatus = ticketDetails2.getTicketStatus();
		ticketDetails2.setTicketStatus("Pending");
		Optional<ServiceEngineerDetails> optionalEngineerDetails = serviceEngineerDetailsRepository
				.findById(ticketDetails2.getDetails().getID());
		ServiceEngineerDetails engineerDetails = optionalEngineerDetails.get();
		if (engineerDetails.getCurrentHighPriorityTicketID().equals(ticketDetails2.getTicketID())) {
			engineerDetails.setPriorities(priorities);
			ticketDetails2.setDetails(engineerDetails);
		} else if (!ticketStatus.equals("Closed")) {
			changeToPending(engineerDetails.getCurrentHighPriorityTicketID(), ticketDetails2.getDetails().getID());
		} else {
			ticketDetails2.setTicketStatus("Closed");
		}
		ticketDetailsRepository.save(ticketDetails2);
		Optional<LoginCredentials> credentials = loginCredentialsRepository
				.findById(ticketDetails2.getDetails().getCredentials().getUsername());
		return credentials.get();
	}

	/*
	 * Returns a boolean value to indicate if the ticket exists or not
	 */
	public boolean checkIfTicketExists(TicketDetails ticketDetails) {
		if (ticketDetailsRepository.findById(ticketDetails.getTicketID()).isPresent()) {
			return true;
		}
		return false;
	}

	/*
	 * This function calculates the averageSeverityPerPriority and returns the list
	 */
	public List<String> calculateAverageSeverity(List<Priorities> priorities) {
		List<String> averageSeverityList = new ArrayList<String>();
		for (int i = 0; i < priorities.size(); i++) {
			if (priorities.get(i).getPriorityID() != 0) {
				String a = prioritiesRepository.getAverageSeverityForGivenPriority("Closed",
						priorities.get(i).getPriorityID());
				averageSeverityList.add(a);
			}
		}
		return averageSeverityList;
	}

	/*
	 * Changes the ticketStatus to Pending and then we get a list of tickets
	 * assigned to the service engineer ordered by status, priority, dates, etc. and
	 * set the first ticket to Working and update the CurrentHighPriorityTicketID
	 * and CurrentTicketPriority as required
	 */
	public void changeToPending(String ticketID, long ID) {
		Optional<TicketDetails> optionalTicketDetails = ticketDetailsRepository.findById(ticketID);
		TicketDetails ticketDetails = optionalTicketDetails.get();
		ticketDetails.setTicketStatus("Pending");
		Optional<ServiceEngineerDetails> optionalEngineerDetails = serviceEngineerDetailsRepository.findById(ID);
		ServiceEngineerDetails engineerDetails = optionalEngineerDetails.get();
		engineerDetails.setCurrentHighPriorityTicketID(ticketDetails.getTicketID());
		Optional<Priorities> priorities = prioritiesRepository.findById(ticketDetails.getPriorities().getPriorityID());
		engineerDetails.setPriorities(priorities.get());
		ticketDetails.setDetails(engineerDetails);
		ticketDetailsRepository.save(ticketDetails);
	}

	/*
	 * Returns a list for TicketDetails table where the given service engineer
	 * tickets are open i.e, not closed
	 */
	public List<TicketDetails> getServiceEngineerOpenTickets(LoginCredentials credentials) {
		return ticketDetailsRepository.getServiceEngineerOpenTicketsInOrder(credentials.getUsername());
	}

	/*
	 * Extracts ServiceEngineerDetails table
	 */
	public List<ServiceEngineerDetails> getServiceEngineers() {
		return serviceEngineerDetailsRepository.findAll();
	}

	/*
	 * Calculates the average time taken to Close a ticket for every serviceEngineer
	 * and returns the list
	 */
	public List<String> getReportStatisticsPerServiceEngineer(List<ServiceEngineerDetails> serviceEngineers) {
		List<String> statistics = new ArrayList<String>();
		for (int i = 0; i < serviceEngineers.size(); i++) {
			String a = prioritiesRepository.getReportStatisticsPerServiceEngineer("Closed",
					serviceEngineers.get(i).getID());
			statistics.add(a);
		}
		return statistics;
	}

	/*
	 * Returns a list of objects which contains the ticketID and age of the open
	 * tickets for a given ServiceEngineer
	 */
	public List<Object[]> getAverageAgeOfOpenTickets(LoginCredentials credentials) {
		return prioritiesRepository.getAgeOfOpenTickets("Closed", credentials.getUsername());
	}
}
