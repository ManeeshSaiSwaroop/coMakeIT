package DAOClasses;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.cfg.Configuration;

import BeanClasses.Departments;
import BeanClasses.LoginCredentials;
import BeanClasses.Priorities;
import BeanClasses.ServiceEngineerDetails;
import BeanClasses.TicketDetails;

public class UserDAO {

	public List<Departments> getDepartments() {
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query q = entityManager.createNamedQuery("selectDepartments");
		@SuppressWarnings("unchecked")
		List<Departments> departments = q.getResultList();
		entityManager.close();
		entityManagerFactory.close();
		return departments;
	}

	public List<Priorities> getPriorities() {
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query q = entityManager.createNamedQuery("selectPriorities");
		@SuppressWarnings("unchecked")
		List<Priorities> priorities = q.getResultList();
		entityManager.close();
		entityManagerFactory.close();
		return priorities;
	}

	public void submitTicket(TicketDetails details) {
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		ServiceEngineerDetails details2 = entityManager.find(ServiceEngineerDetails.class,
				details.getDetails().getID());
		details.setTicketStatus("Pending");
		if (details2.getPriorities().getPriorityID() < details.getPriorities().getPriorityID()) {

			TicketDetails changeStatus = entityManager.find(TicketDetails.class,
					details2.getCurrentHighPriorityTicketID());

			if (changeStatus != null) {
				changeStatus.setTicketStatus("Pending");
				entityManager.persist(changeStatus);
			}

			details2.setCurrentHighPriorityTicketID(details.getTicketID());
			details.setTicketStatus("InProgress");

			Priorities priorities = entityManager.find(Priorities.class, details.getPriorities().getPriorityID());
			details2.setPriorities(priorities);
		}

		details.setCompletionTime(null);
		if (details.getRequestedEndDate().equals(""))
			details.setRequestedEndDate(null);
		entityManager.persist(details);
		entityManager.persist(details2);
		entityManager.getTransaction().commit();
		
		entityManager.close();
		entityManagerFactory.close();

	}

	public List<ServiceEngineerDetails> getCorrespondingEngineerTable(TicketDetails details) {
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query q = entityManager.createNamedQuery("selectCorrespondingEngineers");
		q.setParameter(1, details.getDetails().getDepartments().getDepartmentID());
		@SuppressWarnings("unchecked")
		List<ServiceEngineerDetails> engineers = q.getResultList();
		
		entityManager.close();
		entityManagerFactory.close();
		
		return engineers;
	}

	public ServiceEngineerDetails getMostRecentlyLowPriorityTicketAppointedServiceEngineer(TicketDetails details) {

		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		List<TicketDetails> details3 = entityManager.createQuery(
				"SELECT t from TicketDetails t where t.details IN (select s from ServiceEngineerDetails s where s.departments = (select d from Departments d where departmentID =?1))  order by t.priorities ASC, t.dateOfIssue ASC",
				TicketDetails.class).setParameter(1, details.getDetails().getDepartments().getDepartmentID()).getResultList();
		
		for(TicketDetails det : details3) {
			System.out.println("TICKET DETAILS : " + det.getDetails().getID());
		}
		
		ServiceEngineerDetails details2 = new ServiceEngineerDetails();

		System.out.println(details3.get(0) + " " + details2);
		details2.setID(details3.get(0).getDetails().getID());
		details.setDetails(details2);
		details2 = entityManager.find(ServiceEngineerDetails.class, details.getDetails().getID());

		entityManager.close();
		entityManagerFactory.close();
		
		return details2;
	}

	public List<TicketDetails> getTickets(LoginCredentials credentials) {
		
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		List<TicketDetails> list = entityManager.createQuery("SELECT t from TicketDetails t where t.credentials=(SELECT c from LoginCredentials c where c.username=?1))", TicketDetails.class).setParameter(1, credentials.getUsername()).getResultList();
		
		entityManager.close();
		entityManagerFactory.close();
		
		return list;
	}

}
