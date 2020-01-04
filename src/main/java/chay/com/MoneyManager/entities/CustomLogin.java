package chay.com.MoneyManager.entities;

public class CustomLogin {

	private LoginType loginType;
	private long id;

	public CustomLogin(LoginType loginType, long id) {
		this.loginType = loginType;
		this.id = id;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CustomLogin [loginType=" + loginType + ", id=" + id + "]";
	}

}
