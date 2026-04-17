package com.ashish.ticketing.modules.inventory.service;

import com.ashish.ticketing.modules.event.entity.Event;
import com.ashish.ticketing.modules.event.enums.EventStatus;
import com.ashish.ticketing.modules.event.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

	private final EventRepository eventRepository;
	private final InventoryLockService inventoryLockService;
	private final InventoryPolicyService inventoryPolicyService;

	public InventoryService(
			EventRepository eventRepository,
			InventoryLockService inventoryLockService,
			InventoryPolicyService inventoryPolicyService
	) {
		this.eventRepository = eventRepository;
		this.inventoryLockService = inventoryLockService;
		this.inventoryPolicyService = inventoryPolicyService;
	}

	@Transactional
	public void reserveTickets(Long eventId, int quantity) {
		Event event = inventoryLockService.lockEvent(eventId);
		inventoryPolicyService.validateReservation(event, quantity);

		event.setAvailableTickets(event.getAvailableTickets() - quantity);
		eventRepository.save(event);
	}

	@Transactional
	public void releaseTickets(Long eventId, int quantity) {
		Event event = inventoryLockService.lockEvent(eventId);
		inventoryPolicyService.validateRelease(event, quantity);

		event.setAvailableTickets(event.getAvailableTickets() + quantity);
		eventRepository.save(event);
	}

	@Transactional(readOnly = true)
	public boolean checkAvailability(Long eventId, int quantity) {
		if (eventId == null || quantity <= 0) {
			return false;
		}

		return eventRepository.findByIdAndStatus(eventId, EventStatus.PUBLISHED)
			.map(event -> event.getAvailableTickets() != null && event.getAvailableTickets() >= quantity)
			.orElse(false);
	}

}
