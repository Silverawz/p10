package com.deroussenicolas;



import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SmtpMailSender {

	final static Logger logger = LogManager.getLogger(SmtpMailSender.class);
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	public void send(String to, String subject, String body) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();		
		MimeMessageHelper helper;
		helper = new MimeMessageHelper(message, true); // true indicates multipart message
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(body, true); // true indicates html
		//continue using helper object for more functionalities	
		javaMailSender.send(message);
	}
	
	
}
