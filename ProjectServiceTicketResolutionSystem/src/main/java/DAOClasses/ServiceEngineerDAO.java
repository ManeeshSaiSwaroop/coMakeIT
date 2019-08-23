package DAOClasses;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.hibernate.cfg.Configuration;

import BeanClasses.LoginCredentials;
import BeanClasses.Priorities;
import BeanClasses.ServiceEngineerDetails;
import BeanClasses.TicketDetails;
import BusinessClasses.ServiceEngineerOperations;

public class ServiceEngineerDAO {

	public List<TicketDetails> getTickets(LoginCredentials credentials) {

		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		List<TicketDetails> list = entityManager.createQuery(
				"SELECT t from TicketDetails t where t.details = (SELECT d from ServiceEngineerDetails d where d.credentials = (SELECT c from LoginCredentials c where c.username = ?1)) order by field(ticketStatus, 'InProgress', 'Pending', 'Closed'), t.priorities DESC, -t.requestedEndDate ASC, t.dateOfIssue ASC",
				TicketDetails.class).setParameter(1, credentials.getUsername()).getResultList();
		entityManager.close();
		entityManagerFactory.close();
		return list;
	}

	public void changeToClosed(String ticketID, long ID) {
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager
				.createQuery(
						"UPDATE TicketDetails t SET t.ticketStatus=?2, t.completionTime=sysdate() where t.ticketID=?1")
				.setParameter(1, ticketID).setParameter(2, "Closed").executeUpdate();

		ServiceEngineerDetails engineerDetails = entityManager.find(ServiceEngineerDetails.class, ID);
		engineerDetails.setCurrentHighPriorityTicketID("0");
		Priorities priorities = entityManager.find(Priorities.class, 0);
		engineerDetails.setPriorities(priorities);

		entityManager.persist(engineerDetails);

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
	}

	public void changeToWorking(String ticketID, long ID) {
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		TicketDetails ticketDetails = entityManager.find(TicketDetails.class, ticketID);
		ticketDetails.setTicketStatus("InProgress");
		if (ticketDetails.getStartTime() == null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			ticketDetails.setStartTime(dateFormat.format(date));
		}

		ServiceEngineerDetails engineerDetails = entityManager.find(ServiceEngineerDetails.class, ID);
		engineerDetails.setCurrentHighPriorityTicketID(ticketDetails.getTicketID());
		Priorities priorities = entityManager.find(Priorities.class, ticketDetails.getPriorities().getPriorityID());
		engineerDetails.setPriorities(priorities);
		ticketDetails.setDetails(engineerDetails);

		entityManager.persist(ticketDetails);

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
	}

	public void incrementTicketsWorkedOn(long id) {
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		ServiceEngineerDetails engineerDetails = entityManager.find(ServiceEngineerDetails.class, id);
		engineerDetails.setTotalTicketsWorkedOn(engineerDetails.getTotalTicketsWorkedOn() + 1);

		entityManager.persist(engineerDetails);

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
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

	public void updateTicketPriority(TicketDetails ticketDetails) {
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		TicketDetails ticketDetails2 = entityManager.find(TicketDetails.class, ticketDetails.getTicketID());
		Priorities priorities = entityManager.find(Priorities.class, ticketDetails.getPriorities().getPriorityID());
		ticketDetails2.setPriorities(priorities);
		ticketDetails2.setTicketStatus("Pending");
		
		ServiceEngineerDetails engineerDetails = entityManager.find(ServiceEngineerDetails.class, ticketDetails2.getDetails().getID());
		if(engineerDetails.getCurrentHighPriorityTicketID().equals(ticketDetails2.getTicketID()))
		{
			engineerDetails.setPriorities(priorities);
			ticketDetails2.setDetails(engineerDetails);
			
		}
		else
		{
			new ServiceEngineerDAO().changeToPending(engineerDetails.getCurrentHighPriorityTicketID(), ticketDetails2.getDetails().getID());
		}
		entityManager.persist(ticketDetails2);
		
		entityManager.getTransaction().commit();
		
		LoginCredentials credentials = entityManager.find(LoginCredentials.class, ticketDetails2.getDetails().getCredentials().getUsername());
		new ServiceEngineerOperations().sortOutStatus(credentials);

		entityManager.close();
		entityManagerFactory.close();
		
	}

	public boolean checkIfTicketExists(TicketDetails ticketDetails) {
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		if (entityManager.find(TicketDetails.class, ticketDetails.getTicketID()) != null) {
			entityManager.close();
			entityManagerFactory.close();
			return true;
		}
		entityManager.close();
		entityManagerFactory.close();
		return false;
	}

	public List<String> getAverageSeverity(List<Priorities> priorities) {
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		List<String> averageSeverityList = new ArrayList<String>();
		for (int i = 0; i < priorities.size(); i++) {
			if (priorities.get(i).getPriorityID() != 0) {
				
				@SuppressWarnings("unchecked")
				List<String> a =	entityManager.createNativeQuery(
						"select CONCAT('', SEC_TO_TIME(AVG(TIMESTAMPDIFF(SECOND, startTime, completionTime)))) from TicketDetails where ticketStatus = ?1 and ticketPriorityID=?2")
						.setParameter(1, "Closed").setParameter(2, priorities.get(i).getPriorityID()).getResultList();
					averageSeverityList.add(a.get(0));
			}
		}
		
		entityManager.close();
		entityManagerFactory.close();
		return averageSeverityList;
	}

	public void changeToPending(String ticketID, long ID) {
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		TicketDetails ticketDetails = entityManager.find(TicketDetails.class, ticketID);
		ticketDetails.setTicketStatus("Pending");

		ServiceEngineerDetails engineerDetails = entityManager.find(ServiceEngineerDetails.class, ID);
		engineerDetails.setCurrentHighPriorityTicketID(ticketDetails.getTicketID());
		Priorities priorities = entityManager.find(Priorities.class, ticketDetails.getPriorities().getPriorityID());
		engineerDetails.setPriorities(priorities);
		ticketDetails.setDetails(engineerDetails);

		entityManager.persist(ticketDetails);

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
	}
	
	public List<TicketDetails> getTicketsWhichAreNotClosed(LoginCredentials credentials) {

		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		List<TicketDetails> list = entityManager.createQuery(
				"SELECT t from TicketDetails t where t.ticketStatus!= 'Closed' and t.details = (SELECT d from ServiceEngineerDetails d where d.credentials = (SELECT c from LoginCredentials c where c.username = ?1)) order by t.priorities DESC, -t.requestedEndDate ASC, t.dateOfIssue ASC",
				TicketDetails.class).setParameter(1, credentials.getUsername()).getResultList();
		
		entityManager.close();
		entityManagerFactory.close();
		return list;
	}

	public List<ServiceEngineerDetails> getServiceEngineers() {
		
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		@SuppressWarnings("unchecked")
		List<ServiceEngineerDetails> engineerDetails = entityManager.createQuery("SELECT t from ServiceEngineerDetails t").getResultList();
		
		entityManagerFactory.close();
		entityManager.close();
		return engineerDetails;
	}

	public List<String> getReportStatisticsPerServiceEngineer(List<ServiceEngineerDetails> serviceEngineers) {
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		List<String> statistics = new ArrayList<String>();
		
		for(int i=0; i<serviceEngineers.size(); i++)
		{
			@SuppressWarnings("unchecked")
			List<String> a = entityManager.createNativeQuery("select CONCAT('', SEC_TO_TIME(AVG(TIMESTAMPDIFF(SECOND, startTime, completionTime)))) from TicketDetails where ticketStatus = ?1 and ID=?2").setParameter(1, "Closed").setParameter(2, serviceEngineers.get(i).getID()).getResultList();
			statistics.add(a.get(0));
		}
		
		entityManager.close();
		entityManagerFactory.close();
		return statistics;
	}

	public List<Object[]> getAverageAgeOfOpenTickets(LoginCredentials credentials) {
		
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
				
		@SuppressWarnings("unchecked")
		List<Object[]> result = entityManager.createNativeQuery("select ticketID, CONCAT('', SEC_TO_TIME(AVG(TIMESTAMPDIFF(SECOND, dateOfIssue, sysdate())))) from TicketDetails where ticketStatus!= ?1 and ID=(SELECT ID from ServiceEngineerDetails where username=?2) GROUP BY ID, ticketID").setParameter(1, "Closed").setParameter(2, credentials.getUsername()).getResultList();
		
		entityManager.close();
		entityManagerFactory.close();
		return result;
		
	}
}
