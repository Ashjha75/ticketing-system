package com.ashish.ticketing.modules.event.repository;

import com.ashish.ticketing.modules.event.dto.request.EventSearchRequest;
import com.ashish.ticketing.modules.event.entity.Event;
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
            if (request != null) {
                if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
                    String like = "%" + request.getKeyword().toLowerCase() + "%";
                    predicates.add(builder.like(builder.lower(root.get("name")), like));
                }
                if (request.getCategory() != null) {
                    predicates.add(builder.equal(root.get("category"), request.getCategory()));
                }
                if (request.getStatus() != null) {
                    predicates.add(builder.equal(root.get("status"), request.getStatus()));
                }
                if (request.getStartTimeFrom() != null) {
                    predicates.add(builder.greaterThanOrEqualTo(root.get("startTime"), request.getStartTimeFrom()));
                }
                if (request.getStartTimeTo() != null) {
                    predicates.add(builder.lessThanOrEqualTo(root.get("startTime"), request.getStartTimeTo()));
                }
            }
            return predicates.isEmpty() ? builder.conjunction() : builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

