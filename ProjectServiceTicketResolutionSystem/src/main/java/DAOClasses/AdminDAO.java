package DAOClasses;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.cfg.Configuration;

import BeanClasses.LoginCredentials;
import BeanClasses.Priorities;
import BeanClasses.Roles;
import BeanClasses.ServiceEngineerDetails;

public class AdminDAO {

	public void registerServiceEngineer(LoginCredentials credentials) {
		EntityManagerFactory retriever = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		EntityManager entityManager = retriever.createEntityManager();
		
		Roles roles = new Roles();
		roles = entityManager.find(Roles.class, 2);
		credentials.setRoles(roles);
		
		entityManager.getTransaction().begin();
		entityManager.persist(credentials);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		retriever.close();
	}

	public void insertIntoServiceEngineerDetails(ServiceEngineerDetails engineerDetails) {
		EntityManagerFactory retriever = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		EntityManager entityManager = retriever.createEntityManager();
		entityManager.getTransaction().begin();
		
		Priorities priorities = entityManager.find(Priorities.class, 0);
		engineerDetails.setPriorities(priorities);
		engineerDetails.setTotalTicketsWorkedOn(0);
		engineerDetails.setCurrentHighPriorityTicketID("0");
		
		entityManager.persist(engineerDetails);
		entityManager.getTransaction().commit();
		entityManager.close();
		retriever.close();
	}

	public List<Roles> getRoles() {
		
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		@SuppressWarnings("unchecked")
		List<Roles> roles = entityManager.createQuery("SELECT r from Roles r").getResultList();
		
		entityManager.close();
		entityManagerFactory.close();
		
		return roles;
	}

	public List<LoginCredentials> getUsers(Roles roles) {

		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		@SuppressWarnings("unchecked")
		List<LoginCredentials> credentialsList = entityManager.createQuery("SELECT l from LoginCredentials l where roles = (SELECT r from Roles r where r.roleID=?1)").setParameter(1, roles.getRoleID()).getResultList();
		
		entityManager.close();
		entityManagerFactory.close();
		
		return credentialsList;
	}

	public List<ServiceEngineerDetails> getServiceEngineers() {
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		@SuppressWarnings("unchecked")
		List<ServiceEngineerDetails> serviceEngineerDetails = entityManager.createQuery("SELECT s from ServiceEngineerDetails s").getResultList();
		
		entityManager.close();
		entityManagerFactory.close();
		
		return serviceEngineerDetails;
	}

	public void deleteServiceEngineer(ServiceEngineerDetails engineerDetails) {
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		engineerDetails = entityManager.find(ServiceEngineerDetails.class, engineerDetails.getID());
		LoginCredentials credentials = entityManager.find(LoginCredentials.class, engineerDetails.getCredentials().getUsername());

		entityManager.remove(credentials);
		entityManager.remove(engineerDetails);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
	}

	public void deleteUser(LoginCredentials loginCredentials) {
		EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		LoginCredentials credentials = entityManager.find(LoginCredentials.class, loginCredentials.getUsername());
		
		entityManager.remove(credentials);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
	}

}
