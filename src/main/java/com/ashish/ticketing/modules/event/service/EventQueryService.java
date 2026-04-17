package com.ashish.ticketing.modules.event.service;

import com.ashish.ticketing.common.constants.AppConstants;
import com.ashish.ticketing.common.exception.NotFoundException;
import com.ashish.ticketing.modules.event.dto.request.EventSearchRequest;
import com.ashish.ticketing.modules.event.entity.Event;
import com.ashish.ticketing.modules.event.enums.EventStatus;
import com.ashish.ticketing.modules.event.repository.EventRepository;
import com.ashish.ticketing.modules.event.repository.EventSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
public class EventQueryService {

	private final EventRepository eventRepository;

	public EventQueryService(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	public Event getEventById(Long id) {
		return eventRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("Event not found"));
	}

	public Event getPublishedEventById(Long id) {
		return eventRepository.findByIdAndStatus(id, EventStatus.PUBLISHED)
			.orElseThrow(() -> new NotFoundException("Published event not found"));
	}

	public Page<Event> searchEvents(EventSearchRequest request) {
		Pageable pageable = buildPageable(request);
		return eventRepository.findAll(EventSpecification.build(request), pageable);
	}

	private Pageable buildPageable(EventSearchRequest request) {
		int page = request != null && request.getPage() != null && request.getPage() >= 0
			? request.getPage()
			: 0;

		int requestedSize = request != null && request.getSize() != null ? request.getSize() : AppConstants.DEFAULT_PAGE_SIZE;
		int size = Math.min(Math.max(requestedSize, 1), AppConstants.MAX_PAGE_SIZE);

		Sort sort = Sort.by(Sort.Direction.DESC, "startTime");
		if (request != null && StringUtils.hasText(request.getSort())) {
			String[] parts = request.getSort().split(",");
			String field = parts[0].trim();
			Sort.Direction direction = parts.length > 1 && "asc".equalsIgnoreCase(parts[1].trim())
				? Sort.Direction.ASC
				: Sort.Direction.DESC;
			sort = Sort.by(direction, field);
		}

		return PageRequest.of(page, size, sort);
	}
}

