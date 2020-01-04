package chay.com.MoneyManager.exception;

@SuppressWarnings("serial")
public class ActionNotExists extends MoneyManagerException {

	public ActionNotExists() {
		super("Action does not exist.");

	}

}
