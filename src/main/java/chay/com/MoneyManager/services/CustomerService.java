package chay.com.MoneyManager.services;

import java.util.Collection;

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
import chay.com.MoneyManager.exception.ObjectAlreadyInactive;
import chay.com.MoneyManager.exception.ObjectNotExists;
import chay.com.MoneyManager.exception.UserNotBelongCustomer;

public interface CustomerService {

	/**
	 * The function returns {@code true} if exists in the DB {@link Customer} with
	 * password and email that match to the received password and email.
	 * 
	 * @param email    must be String.
	 * @param password must be String.
	 * @return {@code true} if exists in the DB {@link Customer} with password and
	 *         email that match to the received password and email else
	 *         {@code false}
	 */
	boolean performLogin(String email, String password);

	/**
	 * The function returns the {@link Customer} with the id that equal to the
	 * received id.
	 * 
	 * @param customerId the id of the customer that will return.
	 * @return Customer with the received id.
	 * @throws ObjectNotExists if not exists customer with the received id.
	 */
	Customer getCustomer(long customerId) throws ObjectNotExists;

	/**
	 * The function updates the details of exists customer in the details of the
	 * received customer.
	 * 
	 * @param customer must contain all the fields of {@link Customer}, and id of
	 *                 exists customer.
	 * @return {@link Customer} after the update.
	 * @throws ObjectNotExists     If not exists {@link Customer} with the received
	 *                             {@link Customer} id.
	 * @throws ObjectAlreadyExists if the received {@link Customer} contain email
	 *                             address that already belong to other
	 *                             {@link Customer}.
	 */
	Customer updateCustomer(Customer customer) throws ObjectNotExists, ObjectAlreadyExists;

	/**
	 * The function removes the {@link Customer} with the received id from the DB.
	 * 
	 * @param customerId id of the {@link Customer} that will be removed.
	 * @throws ObjectNotExists if not exists {@link Customer} with the received id.
	 */
	void removeCustomer(long customerId) throws ObjectNotExists;

	/**
	 * The function insert to the DB the received {@link User} and sets the
	 * {@link Customer} with the received {@code customerId} in the field
	 * {@code customer} of the {@link User}.
	 * 
	 * @param customerId of the {@link Customer} that will set as the
	 *                   {@code customer} of the {@link User}.
	 * @param user       that will be inserted to the DB.
	 * @return {@link User} after the inserted to the DB.
	 * @throws ObjectNotExists                    if not exists in the DB
	 *                                            {@link Customer} with the received
	 *                                            id.
	 * @throws ObjectAlreadyExists                if already exists in the DB
	 *                                            {@link User} with the received
	 *                                            {@code customer} and the received
	 *                                            user's name.
	 * @throws CustomerIsNotAllowedToCreateObject if received customer already has
	 *                                            three uses.
	 */
	User createUser(long customerId, User user)
			throws ObjectNotExists, ObjectAlreadyExists, CustomerIsNotAllowedToCreateObject;

	/**
	 * The function update the details of the received {@link User} in the DB.
	 * 
	 * @param customerId of the customer that perform the action.
	 * @param user       existed user with update details.
	 * @return {@link User} after the updating.
	 * @throws ObjectNotExists       if not exists in the DB {@link Customer} with
	 *                               the received id or {@link user} with the
	 *                               received user's id.
	 * @throws ObjectAlreadyExists   if the name of the received {@link User}
	 *                               already exists in another {@code user}
	 * @throws UserNotBelongCustomer if the {@code id} of {@code customer} field of
	 *                               the received {@link user} not equal to the
	 *                               received {@code customerId}
	 */
	User updateUser(long customerId, User user) throws ObjectNotExists, ObjectAlreadyExists, UserNotBelongCustomer;

	/**
	 * The function removes the {@link User} with the received {@code userId} from
	 * the DB as long as there in no {@link action} with the field {@code user} the
	 * equal to the received {@code userId}.
	 * 
	 * @param customerId of the customer that perform the action.
	 * @param userId     of the user that will be removed.
	 * @throws ObjectNotExists         if not exists in the DB {@link Customer} with
	 *                                 the received id or {@link user} with the
	 *                                 received user's id.
	 * @throws UserNotBelongCustomer   if the {@code id} of {@code customer} field
	 *                                 of the received {@link user} not equal to the
	 *                                 received {@code customerId}
	 * @throws NotPossibleRemoveObject if the {@link user} with the received
	 *                                 {@code id} related to other {@link Action}
	 */
	void removeUser(long customerId, long userId)
			throws ObjectNotExists, UserNotBelongCustomer, NotPossibleRemoveObject;

	/**
	 * The function insert to the DB the received {@link MethodPayment} and sets the
	 * {@link Customer} with the received {@code customerId} in the field
	 * {@code customer} of the {@link MethodPayment}
	 * 
	 * @param customerId of the {@link Customer} that create the {@link MethodPayment}.
	 * @param methodPayment that will be inserted to the DB.
	 * @return {@link MethodPayment} after the inserting to the DB.
	 * @throws ObjectAlreadyExists if already exists {@link MethodPayment} with the received {@link MethodPayment}'s {@code name} and with received the {@code customerId}
	 * @throws ObjectNotExists if not exists customer with the received {@code customerId}.
	 * @throws CustomerIsNotAllowedToCreateObject if has to the {@link Customer} with the received {@code id} already 5 {@link MethodPayment}s.
	 */
	MethodPayment createMethodPayment(long customerId, MethodPayment methodPayment)
			throws ObjectAlreadyExists, ObjectNotExists, CustomerIsNotAllowedToCreateObject ;

	/**
	 *  The function update the details of the received {@link MethodPayment} in the DB.
	 * @param customerId must be matched to the {@link MethodPayment}s {@code customer}'s {@code id}.
	 * @param methodPayment must be exists in the DB.
	 * @return {@link MethodPayment} after the updating. 
	 * @throws ObjectAlreadyExists if already exists in the DB {@link MethodPayment} with the {@code name} of the received  {@code methodPayment}
	 * @throws ObjectNotExists  if not exists in the DB {@link Customer} with the received {@code customerId} or {@link MethodPayment} with the received {@code MethdPayment}'s {@code id}.
	 * @throws MethodPaymentNotBelongCustomer if the received {@code methodPayment}'s {@code customer}'s id not match to the received {@code customerId}.
	 */
	MethodPayment updateMethodPayment(long customerId, MethodPayment methodPayment)
			throws ObjectAlreadyExists, ObjectNotExists, MethodPaymentNotBelongCustomer;

	void removeMethodPayment(long customerId, long methodPaymentId) throws MoneyManagerException;

	// MethodPayment getMethodPaymentById(long customerId, long methodPaymentId)
	// throws MethodPaymentNotBelongCustomer, ObjectNotExists;

	// Collection<MethodPayment> getAllMethodPayments(long customerId) throws
	// ObjectNotExists;

	// Action:

	Collection<Category> getAllCategory();

	Action addAction(long customerId, Action action) throws ObjectNotExists, MethodPaymentNotBelongCustomer,
			ObjectAlreadyInactive, UserNotBelongCustomer, MoneyManagerException;

	void removeAction(long customerId, long actionId) throws ObjectNotExists, ActionNotBelongCustomer;

	Action updateAction(long customerId, Action action) throws ObjectNotExists, ActionNotBelongCustomer,
			InvalidActionValue, MethodPaymentNotBelongCustomer, MoneyManagerException;

	Collection<Action> getAllActions(long customerId) throws ObjectNotExists;

}
