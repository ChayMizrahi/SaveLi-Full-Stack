package chay.com.MoneyManager.exception;

@SuppressWarnings("serial")
public class ActionNotBelongCustomer extends MoneyManagerException {

	public ActionNotBelongCustomer(long customerId, long actionId) {
		super("The action with id " + actionId + " not belong to the customer with id " + customerId);

	}

}
