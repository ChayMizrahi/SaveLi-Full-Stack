package chay.com.MoneyManager.exception;

/**
 * Exception intended to prevent creation or updation of objects which one of
 * his unique fields already exists in other object from the some type.
 * 
 * @author Chay Mizrahi
 *
 */
@SuppressWarnings("serial")
public class ObjectAlreadyExists extends MoneyManagerException {

	/**
	 * Exception intended to prevent creation or updation of objects which one of
	 * his unique fields already exists in other object from the some type.
	 * 
	 * @param objectType      which type the client try to insert/update.
	 * @param theUniqueFieldd which field is unique.
	 * @param value           what the value that already exists.
	 */
	public ObjectAlreadyExists(String objectType, String theUniqueFieldd, String value) {
		super(objectType + " with " + theUniqueFieldd + " " + value + " already exists.");

	}

}
