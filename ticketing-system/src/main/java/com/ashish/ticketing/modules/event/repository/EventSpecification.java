package com.ashish.ticketing.modules.event.repository;

import com.ashish.ticketing.modules.event.dto.request.EventSearchRequest;
import com.ashish.ticketing.modules.event.entity.Event;
import com.ashish.ticketing.modules.event.enums.EventStatus;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public final class EventSpecification {
    private EventSpecification() {
    }

    public static Specification<Event> build(EventSearchRequest request) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("status"), EventStatus.PUBLISHED));

            if (request != null) {
                if (request.getCity() != null && !request.getCity().isBlank()) {
                    predicates.add(builder.equal(builder.lower(root.get("city")), request.getCity().toLowerCase()));
                }
                if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
                    String like = "%" + request.getKeyword().toLowerCase() + "%";
                    predicates.add(builder.like(builder.lower(root.get("title")), like));
                }
                if (request.getCategory() != null) {
                    predicates.add(builder.equal(root.get("category"), request.getCategory()));
                }
                if (request.getStartDate() != null) {
                    predicates.add(builder.greaterThanOrEqualTo(root.get("startTime"), request.getStartDate()));
                }
                if (request.getEndDate() != null) {
                    predicates.add(builder.lessThanOrEqualTo(root.get("startTime"), request.getEndDate()));
                }
            }
            return predicates.isEmpty() ? builder.conjunction() : builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

