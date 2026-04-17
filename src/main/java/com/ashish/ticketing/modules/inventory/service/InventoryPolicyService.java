package com.ashish.ticketing.modules.inventory.service;

import com.ashish.ticketing.modules.event.entity.Event;
import com.ashish.ticketing.modules.event.enums.EventStatus;
import com.ashish.ticketing.modules.inventory.exception.InventoryException;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class InventoryPolicyService {

	public void validateReservation(Event event, int quantity) {
		if (event == null) {
			throw new InventoryException("Event is required for reservation");
		}

		if (quantity <= 0) {
			throw new InventoryException("Quantity must be greater than zero");
		}

		if (event.getStatus() != EventStatus.PUBLISHED) {
			throw new InventoryException("Event is not available for booking");
		}

		Instant now = Instant.now();
		if (event.getBookingStartTime() == null || event.getBookingEndTime() == null
			|| now.isBefore(event.getBookingStartTime())
			|| now.isAfter(event.getBookingEndTime())) {
			throw new InventoryException("Booking window is closed");
		}

		if (event.getAvailableTickets() == null || event.getAvailableTickets() < quantity) {
			throw new InventoryException("Insufficient tickets available");
		}
	}

	public void validateRelease(Event event, int quantity) {
		if (event == null) {
			throw new InventoryException("Event is required for release");
		}

		if (quantity <= 0) {
			throw new InventoryException("Quantity must be greater than zero");
		}

		if (event.getAvailableTickets() == null || event.getTotalTickets() == null) {
			throw new InventoryException("Inventory values are not initialized");
		}

		if ((long) event.getAvailableTickets() + quantity > event.getTotalTickets()) {
			throw new InventoryException("Release exceeds total ticket inventory");
		}
	}

}
