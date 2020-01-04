package chay.com.MoneyManager.rest;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import chay.com.MoneyManager.entities.CustomLogin;
import chay.com.MoneyManager.entities.Customer;
import chay.com.MoneyManager.entities.LoginType;
import chay.com.MoneyManager.exception.CustomerAlreadyExists;
import chay.com.MoneyManager.exception.InvalidLogin;
import chay.com.MoneyManager.services.SystemService;

@RestController
public class LoginRest {

	@Autowired
	private SystemService systemService;
	
	@Value("${money.manager.project.sessin.name}")
	private String sessionName;

	@RequestMapping(path = "login", method = RequestMethod.POST)
	public boolean login(@RequestParam String email, @RequestParam String password, @RequestParam LoginType loginType,
			HttpServletResponse response, HttpServletRequest request) throws InvalidLogin {
		try {
			HttpSession session = request.getSession(true);
			CustomLogin customLogin = systemService.login(email.trim(), password.trim(), loginType);
			session.setAttribute(sessionName, customLogin);
			return true;
		} catch (InvalidLogin e) {
			return false;
		}
	}

	@RequestMapping(path = "logout", method = RequestMethod.POST)
	public boolean logout(HttpServletResponse response, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			session.invalidate();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@RequestMapping(path="registration", method=RequestMethod.POST)
	public boolean registration(@RequestBody Customer customer, HttpServletResponse response, HttpServletRequest request) throws CustomerAlreadyExists {
		try {
			HttpSession session = request.getSession(true);
			CustomLogin customLogin = systemService.registration(customer);
			session.setAttribute(sessionName, customLogin);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	@RequestMapping(path="email", method=RequestMethod.GET)
	public Collection<String> getAllEmails(){
		return systemService.getAllEmailAddress();
	}
	
	@RequestMapping(path="check/emailAlreadyExists", method=RequestMethod.GET)
	public boolean checkIfEmailAlreadyExists(@RequestParam String email) {
		return systemService.checkIfEmailAlreadyExists(email);
	}
	
}
