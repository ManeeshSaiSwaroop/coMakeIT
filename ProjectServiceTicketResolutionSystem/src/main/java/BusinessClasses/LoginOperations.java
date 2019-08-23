package BusinessClasses;

import BeanClasses.LoginCredentials;
import DAOClasses.LoginDAO;

public class LoginOperations {
	public LoginOperations()
	{
		
	}
	
	public boolean checkUserExists(LoginCredentials user)
	{
		if(new LoginDAO().findByUsername(user) != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean validatePassword(LoginCredentials user)
	{
		if(new LoginDAO().findByUsername(user).getPassword().equals(user.getPassword()))
			return true;
		return false;
	}
	
	public String getType(LoginCredentials user)
	{
		return new LoginDAO().findByUsername(user).getRoles().getRoleName();
	}
	
	public void addUser(LoginCredentials user)
	{
		new LoginDAO().registerUser(user);
	}

	
}
