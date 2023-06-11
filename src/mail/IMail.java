package mail;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface IMail {
	void send(String userEmail, String title, String contents) throws AddressException, MessagingException;
}
