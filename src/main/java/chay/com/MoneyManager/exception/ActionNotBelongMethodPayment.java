package chay.com.MoneyManager.exception;

/**
 * The exception will appear when the client try to gain access to action with
 * methodPaymentId that not belong to him.
 * 
 * @author Chay Mizrahi
 *
 */
@SuppressWarnings("serial")
public class ActionNotBelongMethodPayment extends MoneyManagerException {

	public ActionNotBelongMethodPayment(long actionId, long methodPaymentId) {
		super("The action with id " + actionId + " not belong to the methodPayment with id " + methodPaymentId);
	}

}
