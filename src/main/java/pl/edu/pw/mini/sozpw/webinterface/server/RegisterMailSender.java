package pl.edu.pw.mini.sozpw.webinterface.server;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class RegisterMailSender {

	public static void sendMail(String username, String mail, String registerKey) {

		final String serverusername = "sozpwmailservice@gmail.com";
		final String password = "sozpwSOZPW";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(serverusername, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
			message.setSubject("SOZPW Rejestracja");

			String msgtext = "Aby zakończyć rejestrację kliknij link:  http://quintus.mini.pw.edu.pl/registrationService?"
					+ RegistrationServlet.REG_PARAM_NAME + "=" + registerKey + "&" + RegistrationServlet.USER_PARAM_NAME + "=" + username;

			message.setText(msgtext);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
