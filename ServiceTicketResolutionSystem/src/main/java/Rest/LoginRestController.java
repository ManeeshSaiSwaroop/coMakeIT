package Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import Beans.LoginCredentials;
import BusinessLogic.LoginOperations;

@RestController
@RequestMapping("/Login")
public class LoginRestController {

	@Autowired
	LoginOperations operations;

	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public String sample(@RequestBody LoginCredentials user) {
		return operations.validate(user);
	}

}