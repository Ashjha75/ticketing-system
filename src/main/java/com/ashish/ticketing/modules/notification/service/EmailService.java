package com.ashish.ticketing.modules.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	public void sendEmail(String to, String subject, String body) {
		logger.info("Sending email to={} subject={} body={}", to, subject, body);
	}

}
