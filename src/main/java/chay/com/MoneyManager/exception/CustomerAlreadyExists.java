package chay.com.MoneyManager.exception;

@SuppressWarnings("serial")
public class CustomerAlreadyExists extends MoneyManagerException {
	/**
	 * The exception thrown if the admin will try to insert customer with email that already belong to other customer. 
	 * @param email must be string. Represent the email that already exists.
	 */
	public CustomerAlreadyExists(String email) {
		super("Customer with email " + email + " alreay exists.");
	}

}
