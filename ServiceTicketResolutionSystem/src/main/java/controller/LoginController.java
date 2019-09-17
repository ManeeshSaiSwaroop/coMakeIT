package controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import bean.LoginCredentials;

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
		ModelAndView mv = new ModelAndView("/index");
		String message = "";
		try {
			message = restTemplate.postForObject(url, credentials, String.class);
			if (message.equals("User")) {
				mv = new ModelAndView("/UserHome");
			} else if (message.equals("ServiceEngineer")) {
				mv = new ModelAndView("/ServiceEngineerHome");
			} else if (message.equals("Admin")) {
				mv = new ModelAndView("/Admin");
			}
			mv.addObject("message", message);
		} catch(Exception e) {
			
				mv.addObject("message", "You have entered wrong username / password");
		}
		return mv;
	}

	/*
	 * It is invoked when user clicks on logout and then the session is invalidated
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
}
