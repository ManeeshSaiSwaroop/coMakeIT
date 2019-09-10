package Controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import Beans.LoginCredentials;

@Controller
public class LoginController {

	@Autowired
	Environment environment;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView greeting(LoginCredentials credentials, HttpSession session) {
		String port = environment.getProperty("local.server.port");

		session.setAttribute("username", credentials.getUsername());

		RestTemplate restTemplate = new RestTemplate();
		final String url = "http://localhost:" + port + "/Login/validate";
		String message = restTemplate.postForObject(url, credentials, String.class);
		ModelAndView mv = new ModelAndView("/index");
		if (message.equals("User")) {
			mv = new ModelAndView("/UserHome");
		} else if (message.equals("ServiceEngineer")) {
			mv = new ModelAndView("/ServiceEngineerHome");
		} else if (message.equals("Admin")) {
			mv = new ModelAndView("/Admin");
		}

		mv.addObject("message", message);
		return mv;
	}

}
