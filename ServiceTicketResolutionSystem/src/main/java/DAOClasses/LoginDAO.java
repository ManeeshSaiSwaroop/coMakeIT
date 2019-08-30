package DAOClasses;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import BeanClasses.LoginCredentials;
import Interfaces.LoginDAOInterface;
import repositories.LoginCredentialsRepository;

@Service
public class LoginDAO implements LoginDAOInterface{

	@Autowired
	LoginCredentialsRepository credentialsRepository;

	/*
	 * This function takes care of the LoginValidation And the process is as
	 * follows: First we check if the entered username actually exists, Then we
	 * check if the entered password matches with his actual password, If the above
	 * two cases satisfy, then we return the type of the user
	 */
	public String validate(LoginCredentials credentials) {
		Optional<LoginCredentials> user = credentialsRepository.findById(credentials.getUsername());

		if (user.isEmpty()) {
			return "Username does not exist";
		} else if (user.get().getPassword().equals(credentials.getPassword())) {
			return user.get().getRoles().getRoleName();
		} else {
			return "Wrong password";
		}

	}
}
