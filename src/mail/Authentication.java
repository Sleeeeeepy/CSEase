package mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Authentication extends Authenticator {
	private String user;
	private String password;
	
	public Authentication(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}
	
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, password);
	}
}
