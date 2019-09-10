package Interfaces;

import Beans.LoginCredentials;

public interface LoginDAOInterface {

	public String validate(LoginCredentials credentials);
}
