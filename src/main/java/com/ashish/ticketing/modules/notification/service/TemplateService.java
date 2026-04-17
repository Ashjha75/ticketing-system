package com.ashish.ticketing.modules.notification.service;

import com.ashish.ticketing.modules.booking.entity.Booking;
import com.ashish.ticketing.modules.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

	public String buildBookingConfirmation(User user, Booking booking) {
		String displayName = user.getName() == null || user.getName().isBlank() ? "User" : user.getName().trim();
		StringBuilder body = new StringBuilder();
		body.append("Hello ").append(displayName).append(",\n\n");
		body.append("Your booking has been confirmed.\n");
		body.append("Booking Number: ").append(booking.getBookingNumber()).append("\n");
		body.append("Event ID: ").append(booking.getEventId()).append("\n");
		body.append("Quantity: ").append(booking.getQuantity()).append("\n");
		body.append("Amount: ").append(booking.getAmount()).append("\n");
		body.append("Status: ").append(booking.getStatus()).append("\n\n");
		body.append("Registered Email: ").append(user.getEmail()).append("\n");
		body.append("Thank you for using Ticketing System.");
		return body.toString();
	}

}
