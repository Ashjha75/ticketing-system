package com.ashish.ticketing.modules.audit.service;

import com.ashish.ticketing.modules.audit.entity.AuditLog;
import com.ashish.ticketing.modules.audit.repository.AuditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

  private static final Logger logger = LoggerFactory.getLogger(AuditService.class);
	private static final String STATUS_SUCCESS = "SUCCESS";
	private static final String STATUS_FAILED = "FAILED";
	private static final String SYSTEM_ACTOR = "SYSTEM";
	private static final int MAX_DETAILS_LENGTH = 1000;

  private final AuditRepository auditRepository;

  public AuditService(AuditRepository auditRepository) {
	this.auditRepository = auditRepository;
  }

  @Async
	public void logSuccess(String action, String entityType, Long entityId, Long userId, String details) {
		saveSafely(action, entityType, entityId, userId, STATUS_SUCCESS, details);
	}

  @Async
	public void logFailure(String action, String entityType, Long entityId, Long userId, String details) {
		saveSafely(action, entityType, entityId, userId, STATUS_FAILED, details);
	}

  @Async
	public void logSystemAction(String action, String entityType, Long entityId, String details) {
		saveSafely(action, entityType, entityId, null, STATUS_SUCCESS, details);
	}

	private void saveSafely(String action, String entityType, Long entityId, Long userId, String status, String details) {
		try {
			AuditLog log = new AuditLog();
			log.setAction(defaultValue(action, "UNKNOWN_ACTION"));
			log.setEntityType(defaultValue(entityType, "UNKNOWN_ENTITY"));
			log.setEntityId(entityId);
			log.setPerformedBy(userId == null ? SYSTEM_ACTOR : String.valueOf(userId));
			log.setStatus(defaultValue(status, STATUS_SUCCESS));
			log.setDetails(sanitizeDetails(details));
			auditRepository.save(log);
		} catch (Exception ex) {
			logger.error("Audit logging failed for action={} entityType={} entityId={}", action, entityType, entityId, ex);
		}
	}

	private String sanitizeDetails(String details) {
		if (details == null || details.isBlank()) {
			return "N/A";
		}

		String safeDetails = details
			.replaceAll("(?i)password\\s*[:=]\\s*[^,\\s]+", "password=***")
			.replaceAll("(?i)token\\s*[:=]\\s*[^,\\s]+", "token=***");

		if (safeDetails.length() > MAX_DETAILS_LENGTH) {
			return safeDetails.substring(0, MAX_DETAILS_LENGTH);
		}
		return safeDetails;
	}

	private String defaultValue(String value, String fallback) {
		return value == null || value.isBlank() ? fallback : value;
	}

}
