package Interfaces;

import BeanClasses.LoginCredentials;

public interface LoginDAOInterface {

	public String validate(LoginCredentials credentials);
}
