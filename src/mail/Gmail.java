package mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Gmail implements IMail {

	@Override
	public void send(String userEmail, String title, String contents) throws AddressException, MessagingException {
		String user = MailConstants.GmailId;
		String pw = MailConstants.GmailPassword;

		Properties p = new Properties();
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.port", 465);
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.ssl.enable", "true");
		p.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getInstance(p, new Authentication(user, pw));

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(user));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
		message.setSubject(title);
		message.setContent(contents, "text/html");
		Transport.send(message);
	}

}
