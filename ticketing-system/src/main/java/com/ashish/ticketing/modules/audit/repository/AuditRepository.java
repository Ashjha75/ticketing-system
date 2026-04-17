package com.ashish.ticketing.modules.audit.repository;

import com.ashish.ticketing.modules.audit.entity.AuditLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<AuditLog, Long> {

	List<AuditLog> findByEntityType(String entityType);

	List<AuditLog> findByPerformedBy(String performedBy);

	List<AuditLog> findByAction(String action);
}
