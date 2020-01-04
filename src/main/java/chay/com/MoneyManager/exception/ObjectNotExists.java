package chay.com.MoneyManager.exception;

/**
 * Exception intended to prevent perform some action on object that not exists
 * in the DB.
 * 
 * @author Chay Mizrahi
 *
 */
@SuppressWarnings("serial")
public class ObjectNotExists extends MoneyManagerException {

	public ObjectNotExists(String objectType, long objectId) {
		super("Not exists object from type " + objectType + " whit id: " + objectId);
	}

}
