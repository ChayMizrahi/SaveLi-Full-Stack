package chay.com.MoneyManager.rest;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import chay.com.MoneyManager.entities.Action;
import chay.com.MoneyManager.entities.ActionType;
import chay.com.MoneyManager.entities.Category;
import chay.com.MoneyManager.entities.CustomLogin;
import chay.com.MoneyManager.entities.Customer;
import chay.com.MoneyManager.entities.MethodPayment;
import chay.com.MoneyManager.entities.User;
import chay.com.MoneyManager.exception.ActionNotBelongCustomer;
import chay.com.MoneyManager.exception.CouldNotAddMethodPayment;
import chay.com.MoneyManager.exception.CouldNotAddUser;
import chay.com.MoneyManager.exception.MethodPaymentNotBelongCustomer;
import chay.com.MoneyManager.exception.MoneyManagerException;
import chay.com.MoneyManager.exception.NotPossibleRemoveObject;
import chay.com.MoneyManager.exception.ObjectAlreadyExists;
import chay.com.MoneyManager.exception.ObjectAlreadyInactive;
import chay.com.MoneyManager.exception.ObjectNotExists;
import chay.com.MoneyManager.exception.UserNotBelongCustomer;
import chay.com.MoneyManager.services.CustomerService;

@RestController
@RequestMapping(path = "moneyManager/customer")
public class CustomerRest {

	/**
	 * Allow us to use in the function of customerService.
	 */
	@Autowired
	private CustomerService customerService;

	@Autowired
	private HttpServletRequest request;

	/**
	 * The name of the attribute that contain the details of the logged-in client.
	 */
	@Value("${money.manager.project.sessin.name}")
	private String attributeName;

	/**
	 * The function return form the attribute the customLogin with the details of
	 * the logged-in client.
	 * 
	 * @return CustomLogin
	 */
	private CustomLogin getSession() {
		HttpSession session = request.getSession();
		CustomLogin customLogin = (CustomLogin) session.getAttribute(attributeName);
		return customLogin;
	}

	/**
	 * The function return the details about the customer that received from the
	 * CustomLogin.
	 * 
	 * @return Customer as JSON
	 * @throws ObjectNotExists if not exists customer with the id that received from
	 *                         the session.
	 */
	@GetMapping
	public Customer getCustomer() throws ObjectNotExists {
		return customerService.getCustomer(getSession().getId());
	}

	@PutMapping
	public Customer updateCustomer(@RequestBody Customer customer) throws MoneyManagerException {
		return customerService.updateCustomer(getSession().getId(), customer);
	}
	
	@DeleteMapping
	public void removeCustomer() throws ObjectNotExists {
		customerService.removeCustomer(getSession().getId());
	}

	@RequestMapping(path = "user", method = RequestMethod.POST)
	public User addUser(@RequestBody User user) throws ObjectNotExists, ObjectAlreadyExists, CouldNotAddUser {
		return customerService.createUser(getSession().getId(), user);
	}

	@RequestMapping(path = "user", method = RequestMethod.PUT)
	public User updateUser(@RequestBody User user) throws MoneyManagerException {
		return customerService.updateUser(getSession().getId(), user);
	}

	@RequestMapping(path = "user/{id}", method = RequestMethod.DELETE)
	public void removeUser(@PathVariable long id)
			throws ObjectNotExists, UserNotBelongCustomer, NotPossibleRemoveObject {
		customerService.removeUser(getSession().getId(), id);
	}

	@RequestMapping(path = "user/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable long id) throws ObjectNotExists, UserNotBelongCustomer {
		return customerService.getUserById(getSession().getId(), id);
	}

	@RequestMapping(path = "user", method = RequestMethod.GET)
	public Collection<User> getAllUsers() throws ObjectNotExists {
		return customerService.getUsersByCustomer(getSession().getId());
	}

	/*-----------------------------------------------------------method payment---------------------------------------------------------------*/

	@RequestMapping(path = "methodPayment", method = RequestMethod.POST)
	public MethodPayment createMethodPayment(@RequestBody MethodPayment methodPayment)
			throws ObjectAlreadyExists, ObjectNotExists, CouldNotAddMethodPayment {
		return customerService.createMethodPayment(getSession().getId(), methodPayment);
	}

	@RequestMapping(path = "methodPayment", method = RequestMethod.PUT)
	public MethodPayment updateMethodPayment(@RequestBody MethodPayment methodPayment) throws MoneyManagerException {
		return customerService.updateMethodPayment(getSession().getId(), methodPayment);
	}

	@RequestMapping(path = "methodPayment/{id}", method = RequestMethod.DELETE)
	public void removeMethodPayment(@PathVariable long id) throws MoneyManagerException {
		customerService.removeMethodPayment(getSession().getId(), id);
	}

	@RequestMapping(path = "methodPayment/{id}", method = RequestMethod.GET)
	public MethodPayment getMethodPaymentById(@PathVariable long id)
			throws MethodPaymentNotBelongCustomer, ObjectNotExists {
		return customerService.getMethodPaymentById(getSession().getId(), id);
	}

	@RequestMapping(path = "methodPayment", method = RequestMethod.GET)
	public Collection<MethodPayment> getAllMethodPayments() throws ObjectNotExists {
		return customerService.getAllMethodPayments(getSession().getId());
	}

	@RequestMapping(path = "action", method = RequestMethod.POST)
	public Action createAction(@RequestBody Action action) throws MoneyManagerException {
		return customerService.addAction(getSession().getId(), action);
	}

	@RequestMapping(path = "action/{id}", method = RequestMethod.DELETE)
	public void removeAction(@PathVariable long id) throws ObjectNotExists, ActionNotBelongCustomer {
		customerService.removeAction(getSession().getId(), id);
	}

	@RequestMapping(path = "action", method = RequestMethod.PUT)
	public Action updateAction(@RequestBody Action action) throws MoneyManagerException {
		return customerService.updateAction(getSession().getId(), action);
	}

	@RequestMapping(path = "action/{actionId}", method = RequestMethod.GET)
	public Action getActionById(@PathVariable long actionId) throws ObjectNotExists, ActionNotBelongCustomer {
		return customerService.getActionById(getSession().getId(), actionId);
	}

	@RequestMapping(path = "action", method = RequestMethod.GET)
	public Collection<Action> getAllActions() throws ObjectNotExists, MethodPaymentNotBelongCustomer {
		return customerService.getAllActions(getSession().getId());
	}

	@RequestMapping(path = "category", method = RequestMethod.GET)
	public Collection<Category> getAllCategory() {
		return customerService.getAllCategory();
	}

}
