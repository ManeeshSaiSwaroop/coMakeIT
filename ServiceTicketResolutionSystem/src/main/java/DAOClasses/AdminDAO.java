package DAOClasses;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import BeanClasses.Departments;
import BeanClasses.LoginCredentials;
import BeanClasses.Priorities;
import BeanClasses.Roles;
import BeanClasses.ServiceEngineerDetails;
import BeanClasses.TicketDetails;
import Interfaces.AdminDAOInterface;
import repositories.DepartmentsRepository;
import repositories.LoginCredentialsRepository;
import repositories.PrioritiesRepository;
import repositories.RolesRepository;
import repositories.ServiceEngineerDetailsRepository;
import repositories.TicketDetailsRepository;

@Service
public class AdminDAO implements AdminDAOInterface{

	@Autowired
	RolesRepository rolesRepository;

	@Autowired
	LoginCredentialsRepository loginCredentialsRepository;

	@Autowired
	PrioritiesRepository prioritiesRepository;

	@Autowired
	DepartmentsRepository departmentsRepository;

	@Autowired
	ServiceEngineerDetailsRepository serviceEngineerDetailsRepository;

	@Autowired
	TicketDetailsRepository ticketDetailsRepository;

	/*
	 * Admin Can register User here
	 */
	public String registerUser(LoginCredentials user) {

		Optional<LoginCredentials> credentials = loginCredentialsRepository.findById(user.getUsername());
		if (credentials.isEmpty()) {
			Optional<Roles> roles = rolesRepository.findById(1);
			user.setRoles(roles.get());
			loginCredentialsRepository.save(user);

			return "Account for User " + user.getUsername() + " is created successfully";
		} else {
			return "Username already exists, try a different one";
		}

	}

	/*
	 * Admin can register ServiceEngineer here
	 */
	public String registerServiceEngineer(LoginCredentials user) {

		Optional<LoginCredentials> credentials = loginCredentialsRepository.findById(user.getUsername());

		if (credentials.isEmpty()) {
			Optional<Roles> roles = rolesRepository.findById(2);
			user.setRoles(roles.get());
			loginCredentialsRepository.save(user);

			return "Account for ServiceEngineer " + user.getUsername() + " is created successfully";
		} else {
			return "Username already exists, try a different one";
		}
	}

	/*
	 * This function is called following the addition of service engineer in the
	 * LoginCredentials table, Here we add the new service engineer into the
	 * ServiceEngineerDetails table by setting default values
	 */
	public void insertIntoServiceEngineerDetails(ServiceEngineerDetails engineerDetails) {

		Optional<Priorities> priorities = prioritiesRepository.findById(0);
		Optional<Departments> departments = departmentsRepository
				.findById(engineerDetails.getDepartments().getDepartmentID());
		engineerDetails.setPriorities(priorities.get());
		engineerDetails.setDepartments(departments.get());
		engineerDetails.setTotalTicketsWorkedOn(0);
		engineerDetails.setCurrentHighPriorityTicketID("0");

		serviceEngineerDetailsRepository.save(engineerDetails);
	}

	/*
	 * Extracts Roles table
	 */
	public List<Roles> getRoles() {

		return rolesRepository.findAll();
	}

	/*
	 * Extracts only users from LoginCredentials table
	 */
	public List<LoginCredentials> getUsers() {

		return loginCredentialsRepository.getUsers(1);
	}

	/*
	 * Extracts ServiceEngineerDetails table
	 */
	public List<ServiceEngineerDetails> getServiceEngineers() {

		return serviceEngineerDetailsRepository.findAll();
	}

	/*
	 * This function is called when admin wants to delete a service engineer And the
	 * process is as follows: First we check if the Service Engineer is free, Then
	 * we check if the department has more than one service engineer, Only if the
	 * above two cases satisfy can an admin delete a service engineer
	 */
	public String deleteServiceEngineer(ServiceEngineerDetails engineerDetails) {

		Optional<ServiceEngineerDetails> optionalEngineerDetails = serviceEngineerDetailsRepository
				.findById(engineerDetails.getID());
		engineerDetails = optionalEngineerDetails.get();

		Optional<LoginCredentials> optionalCredentials = loginCredentialsRepository
				.findById(engineerDetails.getCredentials().getUsername());
		LoginCredentials credentials = optionalCredentials.get();

		if (!engineerDetails.getCurrentHighPriorityTicketID().equals("0")) {
			return "ServiceEngineer " + engineerDetails.getCredentials().getUsername()
					+ " cannot be deleted because he is currently working on some ticket";
		} else if (serviceEngineerDetailsRepository
				.getCountOfEngineersForDepartment(engineerDetails.getDepartments().getDepartmentID()) > 1) {
			ticketDetailsRepository.replaceWithDeletedServiceEngineer(10580834400L, engineerDetails.getID());
			serviceEngineerDetailsRepository.delete(engineerDetails);
			loginCredentialsRepository.delete(credentials);

			return "Deleted";
		}

		return "ServiceEngineer " + engineerDetails.getCredentials().getUsername()
				+ " cannot be deleted because there is only one ServiceEngineer for that Department";
	}

	/*
	 * This function is called when admin wants to delete user And the process is as
	 * follows: First we change the Customer username of the user to be deleted to a
	 * Dummy username which is present in the LoginCredentials mapped to the
	 * TicketDetails table, Then we delete the user from the LoginCredentials, Then
	 * we get the list of service engineers working on the tickets raised by the
	 * deleted user, We delete those tickets and set the CurrentHighPriorityTicketId
	 * and CurrentTicketPriority of the service engineers as required,
	 */
	public String deleteUser(LoginCredentials loginCredentials) {

		Optional<LoginCredentials> optionalCredentials = loginCredentialsRepository
				.findById(loginCredentials.getUsername());

		LoginCredentials credentials = optionalCredentials.get();
		Optional<LoginCredentials> Deleted = loginCredentialsRepository.findById("Deleted");

		ticketDetailsRepository.updateUserDeleted(Deleted.get(), credentials.getUsername());
		loginCredentialsRepository.delete(credentials);
		List<TicketDetails> ticketDetails = ticketDetailsRepository
				.getServiceEngineersListWorkingOnDeletedUser("Deleted", "InProgress");

		for (int i = 0; i < ticketDetails.size(); i++) {
			ServiceEngineerDetails serviceEngineerDetails = serviceEngineerDetailsRepository
					.getServiceEngineersListWorkingOnDeletedUser(ticketDetails.get(i).getDetails().getID());

			ticketDetailsRepository.deleteOpenTicketsWhichAreNotClosedOrInProgress("Deleted", "Closed", "InProgress");

			List<TicketDetails> respectiveTickets = ticketDetailsRepository
					.getServiceEngineerTickets(ticketDetails.get(i).getDetails().getCredentials().getUsername());

			if (respectiveTickets.size() > 1) {
				respectiveTickets.get(1).setTicketStatus("InProgress");
				serviceEngineerDetails.setCurrentHighPriorityTicketID(respectiveTickets.get(1).getTicketID());
				Optional<Priorities> priorities = prioritiesRepository
						.findById(respectiveTickets.get(1).getPriorities().getPriorityID());
				serviceEngineerDetails.setPriorities(priorities.get());

				ticketDetailsRepository.save(respectiveTickets.get(1));
				serviceEngineerDetailsRepository.save(serviceEngineerDetails);
				ticketDetailsRepository.delete(respectiveTickets.get(0));
			} else if (respectiveTickets.size() == 1) {
				serviceEngineerDetails.setCurrentHighPriorityTicketID("0");
				Optional<Priorities> priorities = prioritiesRepository.findById(0);
				serviceEngineerDetails.setPriorities(priorities.get());

				ticketDetailsRepository.delete(respectiveTickets.get(0));
			} else {

			}
		}

		return "Deleted";

	}

	/*
	 * This function is called when admin wants to add department And the process is
	 * as follows: First we check if the given departmentID is not present in the
	 * Departments table, then we check if the departmentName is not present in the
	 * Departments table, only if the above two cases satisfy can an admin add a
	 * Department
	 */
	public String addDepartment(Departments department) {
		if (departmentsRepository.findById(department.getDepartmentID()).isPresent()) {
			return "departmentID already exists";
		} else if (departmentsRepository.findByName(department.getDepartmentName()) != null) {
			return "departmentName already exists";
		}

		departmentsRepository.save(department);

		return "Department " + department.getDepartmentName() + " is created";
	}

}
