package com.truxapiv2.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;

public class TruxUtils {
	static DecimalFormat df2 = new DecimalFormat("#.##");

	public static String computeMD5Hash(String inputString) {
		String hashedString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(inputString.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			hashedString = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hashedString;
	}

	public static String randomAlphanumberGenerator() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

	public static Date convertStringToDate(String dateString) throws ParseException {

		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
		return date;
	}

	public static String generateForgotPaswordCode(String email)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String forgot_password_key = new BASE64Encoder().encode(Hash256(email + "." + System.currentTimeMillis()));
		return forgot_password_key;
	}

	private static byte[] Hash256(String string) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			String text = string;

			md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if
												// needed
			byte[] digest = md.digest();

			return digest;
		} catch (Exception er) {
			er.printStackTrace();
			return new byte[0];
		}
	}

	public static void pageRedisrection(String pagePath, HttpServletRequest request, HttpServletResponse response) {

		RequestDispatcher dispatcher = request.getRequestDispatcher(pagePath);
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param toRecipient
	 * @param textTobeSend
	 * @param subject
	 * @param mailType
	 *            (0 for forget password & 1 for payment reciept)
	 */

	public static void main(String[] args) {

		sendMail("hussain.shahzad250@gmail.com", "Hi Friends", "Test", 1);
	}

	public static void sendMail(String toRecipient, String textTobeSend, String msgSubject, Integer mailType) {
		try {
			String host = "smtp.gmail.com";
			final String user = "noreply@truxapp.com";// change accordingly
			final String password = "Trux@123";// change accordingly

			String to = toRecipient;// change accordingly

			// Get the session object
			Properties props = new Properties();
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});

			// Compose the message
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(user));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				if (mailType == 0) {
					message.setSubject(msgSubject);
					message.setText("Mail Content: " + textTobeSend);
				} else if (mailType == 1) {

					message.setSubject(msgSubject);

					// String htmlText = "<H1>Dear customer:
					// "+customerBookingDetails.getCustomerName()+"</H1> ";

					String htmlText = "";
					try {
						// htmlText =
						// readFile("/home/ec2-user/tomcat/apache-tomcat-7.0.57/webapps/emailtemplate.html");
						htmlText = readFile("D:/code/mail.html");
						String[] values = new String[2];
						values[0] = "Ganesha";
						values[1] = new Date().toString();
						htmlText = getMessage(htmlText, values);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					MimeMultipart multipart = new MimeMultipart();
					BodyPart messageBodyPart = new MimeBodyPart();

					messageBodyPart.setContent(htmlText, "text/html");
					multipart.addBodyPart(messageBodyPart);
					message.setContent(multipart);
					// message.setText("Dear customer: " +
					// customerBookingDetails.getConsigneeName()+"/n Your builty
					// details for booking id:
					// "+customerBookingDetails.getBookingId());
				}

				// send the message
				Transport.send(message);

				System.out.println("message sent successfully...");

			} catch (MessagingException e) {
				e.printStackTrace();
			}
		} catch (Exception er) {
			er.printStackTrace();
		}
	}

	static String getMessage(String template, String... values) {
		try {
			if (values != null) {
				for (int i = values.length - 1; i >= 0; i--) {
					System.out.println(i);
					if (values[i] != null) {
						template = template.replace("$" + (i + 1), values[i]);
					}
				}
				return template;
			}

			else
				return template;
		} catch (Exception er) {
			return "";
		}
	}

	public static String getIPAddress(HttpServletRequest request) {
		try {
			String ipAddress = request.getHeader("X-FORWARDED-FOR");

			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
			if (ipAddress.contains(",")) {
				String[] ipaddlist = ipAddress.split(",");
				ipAddress = ipaddlist[0];
			}

			return ipAddress;
		} catch (Exception er) {
			er.printStackTrace();
			return "";
		}
	}

	private static String readFile(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}

		return stringBuilder.toString();
	}

	public static double roundTo5Decimals(double val) {
		DecimalFormat df2 = new DecimalFormat("#.#####");
		return Double.valueOf(df2.format(val));
	}

	public static double roundTo1Decimals(double val) {
		DecimalFormat df2 = new DecimalFormat("#.#");
		System.out.println("Lat Long Val:" + val);
		return Double.valueOf(df2.format(val));
	}

	public static Date getChangedTimezoneDate(Date date) {

		if (date != null)
			return new Date(date.getTime() + 1800 * 1000 * 11);

		return null;
	}

	public static int getRandomNumber(int min, int max) {
		return (int) Math.floor(Math.random() * (max - min + 1)) + min;
	}
}
