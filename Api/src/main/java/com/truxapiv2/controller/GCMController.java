package com.truxapiv2.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

@Controller
@RequestMapping(value = "/gcm")
public class GCMController {
	public static final String GCM_API_KEY = "AIzaSyBqIV37TUmB8WmtV2a9pa4f_V0IQrDyGqU";
	public static final String MESSAGE_VALUE = "Hello, Sending Notifications using GCM";
	public static final String MESSAGE_KEY = "message";
	public static final String REG_ID = "APA91bEukLbwe3GmHMszBXphwmYt1xlmgdz0S2J1Mz-7bAwlXrPRBZ-uSAzLCNrstTIUNwW7Qshy-DwjaT6KVLyM8M3JENLCOnlxMOtC47tHOzLL_121zWLu_oF7uxUQBUfqACGQAlVO";

	@RequestMapping("/send")
	public String sendMessage() {

		Sender sender = new Sender(GCM_API_KEY);
		ArrayList<String> devicesList = new ArrayList<String>();
		devicesList.add(REG_ID);
		Message message = new Message.Builder().timeToLive(30).delayWhileIdle(true).addData(MESSAGE_KEY, MESSAGE_VALUE)
				.build();
		try {
			System.out.println("Sending POST to GCM");
			MulticastResult result = sender.send(message, devicesList, 1);
			System.out.println(result.toString());
			System.out.println("Message Send Successfully");
		} catch (Exception E) {
			E.printStackTrace();
		}

		return "gcm";
	}

}
