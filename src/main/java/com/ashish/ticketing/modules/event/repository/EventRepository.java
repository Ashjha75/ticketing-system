package com.ashish.ticketing.modules.event.repository;

import com.ashish.ticketing.modules.event.entity.Event;
import com.ashish.ticketing.modules.event.enums.EventStatus;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

	Optional<Event> findByIdAndStatus(Long id, EventStatus status);

	List<Event> findByStatus(EventStatus status);

	long countByStatus(EventStatus status);

	@Query("select count(e) from Event e where e.status = :status and e.availableTickets = 0")
	long countSoldOutByStatus(@Param("status") EventStatus status);

	@Override
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Event> findById(Long id);
}

