package com.truxapiv2.controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.truxapiv2.model.RestResponce;

@Controller
@RequestMapping(value = "/send")
public class JavaMailController {
	@ResponseBody
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public RestResponce sendMail() {

		// String to = "shantanu.kumar@truxapp.com";
		// String from = "noreply@truxapp.com";
		// final String username = "AKIAJXRL5O4LGPJ7JW7Q";
		// final String password =
		// "AsU5SXjMzCSNKJyjRCIgdZbGvAKzUdsgKy6HhYfZM2ZU";

		String to = "hussain.shahzad250@gmail.com";
		String from = "hussain.shahzad250@gmail.com";
		final String username = "hussain.shahzad250@gmail.com";
		final String password = "*************";

		String host = "smtp.gmail.com";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		Session session1 = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		RestResponce restResponce = new RestResponce();
		try {
			System.out.println("");
			Message message = new MimeMessage(session1);

			message.setFrom(new InternetAddress(from));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			message.setSubject("Resume");

			message.setText("Hello, Please Check Attachment file.");

			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return null;

	}

}
