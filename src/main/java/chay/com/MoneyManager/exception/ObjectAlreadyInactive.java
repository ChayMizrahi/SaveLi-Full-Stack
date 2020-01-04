package chay.com.MoneyManager.exception;

@SuppressWarnings("serial")
public class ObjectAlreadyInactive extends MoneyManagerException {

	public ObjectAlreadyInactive( String objectType ,long  objectId) {
		super("The "+objectType+" with id " + objectId
				+ "is disinable .Unable to perform with a disabled form of payment.");
	}

}
