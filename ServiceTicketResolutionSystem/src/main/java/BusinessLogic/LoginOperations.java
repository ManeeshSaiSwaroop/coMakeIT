package BusinessLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Beans.LoginCredentials;
import Services.LoginService;

@Component
public class LoginOperations {

	@Autowired
	LoginService dao;

	public String validate(LoginCredentials credentials) {
		return dao.validate(credentials);
	}

}
