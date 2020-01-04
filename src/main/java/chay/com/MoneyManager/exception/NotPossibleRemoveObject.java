package chay.com.MoneyManager.exception;

/**
 * Exception intended to prevent removing of objects that associated to some
 * actions.
 * 
 * @author Chay Mizrahi
 *
 */
@SuppressWarnings("serial")
public class NotPossibleRemoveObject extends MoneyManagerException {

	public NotPossibleRemoveObject(String objectType) {
		super("Not possible to remove the object " + objectType
				+ " because there is some actions that associated with this object.");
	}

}
