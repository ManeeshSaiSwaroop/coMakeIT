package BusinessClasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import BeanClasses.LoginCredentials;
import DAOClasses.LoginDAO;

@Component
public class LoginOperations {

	@Autowired
	LoginDAO dao;

	public String validate(LoginCredentials credentials) {
		return dao.validate(credentials);
	}

}
