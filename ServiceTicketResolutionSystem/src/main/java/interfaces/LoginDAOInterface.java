package interfaces;

import bean.LoginCredentials;

public interface LoginDAOInterface {

	public String validate(LoginCredentials credentials);
}
