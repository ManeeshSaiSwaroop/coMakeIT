package DAOClasses;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;

import BeanClasses.LoginCredentials;
import BeanClasses.Roles;

public class LoginDAO {
	public LoginCredentials findByUsername(LoginCredentials user)
	{
		EntityManagerFactory retriever = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		EntityManager entityManager = retriever.createEntityManager();
		user = entityManager.find(LoginCredentials.class, user.getUsername());
		entityManager.close();
		retriever.close();
		return user;
	}

	public void registerUser(LoginCredentials user) {
		EntityManagerFactory retriever = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		EntityManager entityManager = retriever.createEntityManager();
		Roles roles = new Roles();
		roles = entityManager.find(Roles.class, 1);
		user.setRoles(roles);
		entityManager.getTransaction().begin();
		entityManager.persist(user);
		entityManager.getTransaction().commit();
		entityManager.close();
		retriever.close();
	}
}
