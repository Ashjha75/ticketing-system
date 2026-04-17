package com.ashish.ticketing.modules.inventory.service;

import com.ashish.ticketing.modules.event.entity.Event;
import com.ashish.ticketing.modules.event.repository.EventRepository;
import com.ashish.ticketing.modules.inventory.exception.InventoryException;
import org.springframework.stereotype.Service;

@Service
public class InventoryLockService {

	private final EventRepository eventRepository;

	public InventoryLockService(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	public Event lockEvent(Long eventId) {
		if (eventId == null) {
			throw new InventoryException("Event id is required");
		}

		return eventRepository.findById(eventId)
			.orElseThrow(() -> new InventoryException("Event not found"));
	}

}
