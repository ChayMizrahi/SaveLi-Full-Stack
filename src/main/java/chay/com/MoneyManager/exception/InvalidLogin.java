package chay.com.MoneyManager.exception;

@SuppressWarnings("serial")
public class InvalidLogin extends MoneyManagerException {

	public InvalidLogin() {
		super("The email or password incorrect.");
	}

}
