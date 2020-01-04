package chay.com.MoneyManager.exception;

@SuppressWarnings("serial")
public class UserNotBelongCustomer extends MoneyManagerException {

	public UserNotBelongCustomer(long customerId, long userId) {
		super("The user with id " + userId + " not belong to the customer with id " + customerId);
	}

}
