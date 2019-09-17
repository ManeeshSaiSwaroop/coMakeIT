package business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bean.LoginCredentials;
import service.LoginService;

@Component
public class LoginOperations {

	@Autowired
	LoginService dao;

	public String validate(LoginCredentials credentials) throws Exception{
		return dao.validate(credentials);
	}

}
