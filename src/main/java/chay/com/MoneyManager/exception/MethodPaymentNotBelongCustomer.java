package chay.com.MoneyManager.exception;

/**
 * The exception intent is to prevent form {@link Customer} to make some action on {@link MethodPayment} that not belong to him
 * 
 * @author Chay Mizrahi
 *
 */
@SuppressWarnings("serial")
public class MethodPaymentNotBelongCustomer extends MoneyManagerException {

	public MethodPaymentNotBelongCustomer(long methodPaymentId) {
		super("The MethodPayment with id " + methodPaymentId + " not belong to this customer.");

	}

}
