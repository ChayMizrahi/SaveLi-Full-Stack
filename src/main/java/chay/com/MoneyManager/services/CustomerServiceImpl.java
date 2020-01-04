package chay.com.MoneyManager.services;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chay.com.MoneyManager.entities.Action;
import chay.com.MoneyManager.entities.Category;
import chay.com.MoneyManager.entities.Customer;
import chay.com.MoneyManager.entities.MethodPayment;
import chay.com.MoneyManager.entities.User;
import chay.com.MoneyManager.exception.ActionNotBelongCustomer;
import chay.com.MoneyManager.exception.CouldNotAddMethodPayment;
import chay.com.MoneyManager.exception.CustomerIsNotAllowedToCreateObject;
import chay.com.MoneyManager.exception.InvalidActionValue;
import chay.com.MoneyManager.exception.MethodPaymentNotBelongCustomer;
import chay.com.MoneyManager.exception.MoneyManagerException;
import chay.com.MoneyManager.exception.NotPossibleRemoveObject;
import chay.com.MoneyManager.exception.ObjectAlreadyExists;
import chay.com.MoneyManager.exception.ObjectNotExists;
import chay.com.MoneyManager.exception.UserNotBelongCustomer;
import chay.com.MoneyManager.repositories.ActionRepository;
import chay.com.MoneyManager.repositories.CategoryRepository;
import chay.com.MoneyManager.repositories.CustomerRepository;
import chay.com.MoneyManager.repositories.MethodPaymentRepository;
import chay.com.MoneyManager.repositories.UserRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	/**
	 * Allows us to access to the {@link customer} table in the DB
	 */
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ActionRepository actionRepository;
	@Autowired
	private MethodPaymentRepository methodPaymentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public boolean performLogin(String email, String password) {
		return (customerRepository.findByEmailAndPassword(email, password) == null) ? false : true;
	}

	@Override
	public Customer getCustomer(long customerId) throws ObjectNotExists {
		throwExceptionIfCustomerNotExists(customerId);
		return customerRepository.findById(customerId).get();
	}

	@Override
	public Customer updateCustomer(Customer customer) throws ObjectNotExists, ObjectAlreadyExists {
		throwExceptionIfCustomerNotExists(customer.getId());
		if (isCustomerEmailChanged(customer)) {
			throwExceptionIfEmailAlreadyExists(customer.getEmail());
		}
		return customerRepository.save(customer);
	}

	@Override
	public void removeCustomer(long customerId) throws ObjectNotExists {
		throwExceptionIfCustomerNotExists(customerId);
		customerRepository.delete(customerRepository.findById(customerId).get());
	}

	@Override
	public User createUser(long customerId, User user)
			throws ObjectAlreadyExists, ObjectNotExists, CustomerIsNotAllowedToCreateObject {
		user.setName(user.getName().trim());
		throwExceptionIfCustomerNotExists(customerId);
		throwExceptionIfCustomerHasThreeUsers(customerId);
		throwExceptionIfUserNameAlreadyExists(customerId, user.getName());
		user.setCustomer(customerRepository.findById(customerId).get());
		userRepository.save(user);
		return user;
	}

	@Override
	public User updateUser(long customerId, User user)
			throws ObjectNotExists, ObjectAlreadyExists, UserNotBelongCustomer {
		throwExceptionIfCustomerNotExists(customerId);
		throwExceptionIfUserNotExists(user.getId());
		throwExceptionIfUserNotBelongCustomer(customerId, user.getId());
		user.setName(user.getName().trim());
		if (isUserNameChanged(user)) {
			throwExceptionIfUserNameAlreadyExists(customerId, user.getName());
		}
		user.setCustomer(customerRepository.findById(customerId).get());
		userRepository.save(user);
		return user;
	}

	@Override
	public void removeUser(long customerId, long userId)
			throws ObjectNotExists, UserNotBelongCustomer, NotPossibleRemoveObject {
		throwExceptionIfCustomerNotExists(customerId);
		throwExceptionIfUserNotExists(userId);
		throwExceptionIfUserNotBelongCustomer(customerId, userId);
		if (!userRepository.findById(userId).get().getActions().isEmpty()) {
			throw new NotPossibleRemoveObject("User");
		} else {
			userRepository.delete(userRepository.findById(userId).get());
		}
	}

	@Override
	public MethodPayment createMethodPayment(long customerId, MethodPayment methodPayment)
			throws ObjectAlreadyExists, ObjectNotExists, CustomerIsNotAllowedToCreateObject {
		throwExceptionIfCustomerNotExists(customerId);
		throwExceptionIfCustomerHasFiveMethodPayments(customerId);
		methodPayment.setName(methodPayment.getName().trim());
		throwExceptionIfMethodPaymentNameAlreadyExists(customerId, methodPayment.getName());
		methodPayment.setCustomer(customerRepository.findById(customerId).get());
		methodPaymentRepository.save(methodPayment);
		return methodPayment;
	}

	@Override
	public MethodPayment updateMethodPayment(long customerId, MethodPayment methodPayment)
			throws ObjectAlreadyExists, ObjectNotExists, MethodPaymentNotBelongCustomer {
		throwExceptionIfCustomerNotExists(customerId);
		throwExceptionIfMethodPaymentNotExists(methodPayment.getId());
		throwExceptionIfMethodPaymentNotBelongToCustomer(customerId, methodPayment.getId());
		methodPayment.setName(methodPayment.getName().trim());
		if (isMethodPaymentNameChanged(methodPayment)) {
			throwExceptionIfMethodPaymentNameAlreadyExists(customerId, methodPayment.getName());
		}
		methodPayment.setCustomer(customerRepository.findById(customerId).get());
		methodPaymentRepository.save(methodPayment);
		return methodPayment;
	}

	@Override
	public void removeMethodPayment(long customerId, long methodPaymentId)
			throws ObjectNotExists, MethodPaymentNotBelongCustomer, NotPossibleRemoveObject {
		if (methodPaymentRepository.findById(methodPaymentId).get().getActions().isEmpty()) {
			methodPaymentRepository.deleteById(methodPaymentId);
		} else {
			throw new NotPossibleRemoveObject("MethodPayment");
		}
	}

	@Override
	public Action addAction(long customerId, Action action) throws MoneyManagerException {
		throwExceptionIfCustomerNotExists(customerId);
		throwExceptionIfActionInvalid(customerId, action);
		actionRepository.save(action);
		return action;
	}

	@Override
	public void removeAction(long customerId, long actionId) throws ObjectNotExists, ActionNotBelongCustomer {
		throwExceptionIfCustomerNotExists(customerId);
		throwExceptionIfActionNotBelongToCustomer(customerId, actionId);
		actionRepository.delete(actionRepository.findById(actionId).get());
	}

	@Override
	public Action updateAction(long customerId, Action action) throws MoneyManagerException {
		throwExceptionIfCustomerNotExists(customerId);
		throwExceptionIfActionNotExists(action.getId());
		throwExceptionIfActionNotBelongToCustomer(customerId, action.getId());
		throwExceptionIfActionInvalid(customerId, action);
		actionRepository.save(action);
		return action;
	}

	@Override
	public Collection<Action> getAllActions(long customerId) throws ObjectNotExists {
		throwExceptionIfCustomerNotExists(customerId);
		return actionRepository.findByMethodPaymentCustomerId(customerId);
	}

	@Override
	public Collection<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	/**
	 * The function throws the exception {@link ObjectAlreadyExists} if the received
	 * email already belong to other {@link customer} in the DB.
	 * 
	 * @param email must be string.
	 * @throws ObjectAlreadyExists if the received email already belong to other
	 *                             {@link customer} in the DB.
	 */
	private void throwExceptionIfEmailAlreadyExists(String email) throws ObjectAlreadyExists {
		if (customerRepository.findByEmail(email) != null) {
			throw new ObjectAlreadyExists("customer", "email", email);
		}
	}

	/**
	 * The function gets from the DB the customer with the id that equal to the
	 * received customer's id and return true if the email of the customer from the
	 * DB difference from the received customer's email.
	 * 
	 * @param customer must contain all the fields of {@link Customer}
	 * @return {@code true} if the email of the customer from the DB difference from
	 *         the received customer's email else {@code false}
	 */
	private boolean isCustomerEmailChanged(Customer customer) {
		return (customerRepository.findById(customer.getId()).get().getEmail().equals(customer.getEmail())) ? false
				: true;
	}

	/**
	 * The function throws exception if not exists in the DB customer with the
	 * received id.
	 * 
	 * @param customerId must be long.
	 * @throws ObjectNotExists if not exists in the DB customer with the received
	 *                         id.
	 */
	private void throwExceptionIfCustomerNotExists(long customerId) throws ObjectNotExists {
		if (!customerRepository.findById(customerId).isPresent()) {
			throw new ObjectNotExists("Customer", customerId);
		}
	}

	/**
	 * The function returns {@code true} if the {@code name} of the {@link User}
	 * different from the {@code name} of the {@link user} in the DB with the
	 * {@code id} that equal to received user's id .
	 * 
	 * @param user that will be checked.
	 * @return true if the user name changed, else false.
	 */
	private boolean isUserNameChanged(User user) {
		return (userRepository.findById(user.getId()).get().getName().equals(user.getName())) ? false : true;
	}

	/**
	 * The function throws the exception {@link CustomerIsNotAllowedToCreateObject}
	 * if the users amount of the received customer bigger then 2.
	 * 
	 * @param customerId the id of the {@link Customer} that will be checked.
	 * @throws CustomerIsNotAllowedToCreateObject if the users amount of the
	 *                                            received customer bigger then 2.
	 */
	private void throwExceptionIfCustomerHasThreeUsers(long customerId) throws CustomerIsNotAllowedToCreateObject {
		if (customerRepository.findById(customerId).get().getUsers().size() > 2) {
			throw new CustomerIsNotAllowedToCreateObject("User");
		}
	}

	/**
	 * Help function that throws exception if not exists in the DB user with the id
	 * that received as argument.
	 * 
	 * @param userId must be long.
	 * @throws ObjectNotExists if not exists in the DB user with the id that
	 *                         received as argument
	 */
	private void throwExceptionIfUserNotExists(long userId) throws ObjectNotExists {
		if (!userRepository.findById(userId).isPresent()) {
			throw new ObjectNotExists("User", userId);
		}
	}

	/**
	 * Help function that throws exception if the user with the id that received not
	 * belong to the
	 * 
	 * @param customerId
	 * @param userId
	 * @throws UserNotBelongCustomer
	 * @throws ObjectNotExists
	 */
	private void throwExceptionIfUserNotBelongCustomer(long customerId, long userId)
			throws ObjectNotExists, UserNotBelongCustomer {
		if (userRepository.findByIdAndCustomerId(userId, customerId) == null) {
			throw new UserNotBelongCustomer(customerId, userId);
		}
	}

	/**
	 * The function throws the exception {@link CustomerIsNotAllowedToCreateObject}
	 * if the {@link Customer} with the received {@code customerId} already has 5
	 * method payments.
	 * 
	 * @param customerId the {@code id} of the {@link Customer} that will be
	 *                   checked.
	 * @throws CustomerIsNotAllowedToCreateObject if the {@link Customer} with the
	 *                                            received {@code customerId}
	 *                                            already has 5 method payments.
	 */
	private void throwExceptionIfCustomerHasFiveMethodPayments(long customerId)
			throws CustomerIsNotAllowedToCreateObject {
		if (methodPaymentRepository.findByCustomerId(customerId).size() > 4) {
			throw new CustomerIsNotAllowedToCreateObject("MethodPayment");
		}
	}

	/**
	 * The function throws the exception {@link ObjectNotExists} if not exists in
	 * the DB {@link MethodPayment} with the received {@code methodPaymentId}
	 * 
	 * @param methodPaymentId must be long.
	 * @throws ObjectNotExists if the methodPayment not exists.
	 */
	private void throwExceptionIfMethodPaymentNotExists(long methodPaymentId) throws ObjectNotExists {
		if (!methodPaymentRepository.findById(methodPaymentId).isPresent()) {
			throw new ObjectNotExists("MethodPayment", methodPaymentId);
		}
	}

	/**
	 * Help function that checks if the action with the id that received exists.
	 * 
	 * @param actionId must be long
	 * @throws ObjectNotExists if the action not exists.
	 */
	private void throwExceptionIfActionNotExists(long actionId) throws ObjectNotExists {
		if (!actionRepository.findById(actionId).isPresent()) {
			throw new ObjectNotExists("Action", actionId);
		}
	}

	/**
	 * The function throws the exception {@link MethodPaymentNotBelongCustomer} if
	 * the received {@code customerId} not equel to the {@code id} of the
	 * {@link Customer} field of the received {@code methodPaymentId}.
	 * 
	 * @param customerId      must be long.
	 * @param methodPaymentId must be long
	 * @throws MethodPaymentNotBelongCustomer if the methodPayment not belong to the
	 *                                        customer.
	 */
	private void throwExceptionIfMethodPaymentNotBelongToCustomer(long customerId, long methodPaymentId)
			throws MethodPaymentNotBelongCustomer {
		if (methodPaymentRepository.findById(methodPaymentId).get().getCustomer().getId() != customerId) {
			throw new MethodPaymentNotBelongCustomer(methodPaymentId);
		}
	}

	/**
	 * The function returns {@code true} if the {@code name} of the received
	 * {@link MethodPayment} different from the {@code name} of the
	 * {@link MethodPayment} in the DB with the {@code id} of the received
	 * {@code MethodPayment}.
	 * 
	 * @param methodPayment must contain all the fields of m{@link MethodPayment}.
	 * @return true if the name has changed.
	 */
	private boolean isMethodPaymentNameChanged(MethodPayment methodPayment) {
		return methodPaymentRepository.findById(methodPayment.getId()).get().getName().equals(methodPayment.getName())
				? false
				: true;
	}

	/**
	 * The function throws the exception {@link ObjectAlreadyExists} if the
	 * {@link Customer} with the received {@code id} already has
	 * {@link MethodPayment} with the received {@code name}.
	 * 
	 * @param name       must be string
	 * @param customerId the {@code id} of the {@link Customer} that will be
	 *                   checked.
	 * @throws ObjectAlreadyExists if the {@link Customer} has already
	 *                             {@link MethodPayment} with the same {@code name}.
	 */
	private void throwExceptionIfMethodPaymentNameAlreadyExists(long customerId, String name)
			throws ObjectAlreadyExists {
		if (methodPaymentRepository.findByNameAndCustomerId(name, customerId) != null) {
			throw new ObjectAlreadyExists("methodPayment", "name", name);
		}
	}

	private void throwExceptionIfActionInvalid(long customerId, Action action)
			throws InvalidActionValue, MethodPaymentNotBelongCustomer, ObjectNotExists {
		MethodPayment mp = action.getMethodPayment();
		Category category = action.getCategory();
		User user = action.getUser();
		throwExceptionIfMethodPaymentNotExists(mp.getId());
		throwExceptionIfMethodPaymentNotBelongToCustomer(customerId, mp.getId());
		if (user == null) {
			throw new InvalidActionValue("The field operation must be the name of one of the user of the customer");
		}
		if (category == null) {
			throw new InvalidActionValue("The category to which the action belongs is not exists.");
		}
		if (category.getType() != action.getActionType()) {
			throw new InvalidActionValue("The category and action must belong to the same ActionType");
		}
	}

	/**
	 * The function throws the exception {@link ObjectAlreadyExists} if already
	 * exists in the {@link Customer} with the received {@code customerId}
	 * {@link user} with the field {@code name} that equal to the received
	 * {@code name}.
	 * 
	 * @param customerId of the customer that perform the action.
	 * @param name       of the user.
	 * @throws ObjectAlreadyExists if already exists user with the name that
	 *                             received.
	 */
	private void throwExceptionIfUserNameAlreadyExists(long customerId, String name) throws ObjectAlreadyExists {
		for (User user : customerRepository.findById(customerId).get().getUsers()) {
			if (user.getName().equals(name)) {
				throw new ObjectAlreadyExists("User", "name", name);
			}
		}
	}

	private boolean throwExceptionIfActionNotBelongToCustomer(long customerId, long actionId)
			throws ActionNotBelongCustomer {
		Action action = actionRepository.findByIdAndMethodPaymentCustomerId(actionId, customerId);
		if (action == null) {
			throw new ActionNotBelongCustomer(customerId, actionId);
		} else {
			return true;
		}
	}

}
