package chay.com.MoneyManager.exception;

/**
 * The exception intent is to limit the {@link Customer} to some amount of
 * objects that he could create. every customer could create: maximum 3
 * {@link User}s and maximum 5 {@link MethodPayment}s.
 * 
 * @author Chay Mizrahi
 *
 */
@SuppressWarnings("serial")
public class CustomerIsNotAllowedToCreateObject extends MoneyManagerException {

	public CustomerIsNotAllowedToCreateObject(String objectType) {
		super("customer isn't allowed to create another object from type " + objectType);
	}

}
