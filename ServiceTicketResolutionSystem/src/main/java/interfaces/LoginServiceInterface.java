package interfaces;

import bean.LoginCredentials;

public interface LoginServiceInterface {

	public String validate(LoginCredentials credentials) throws Exception;
}
