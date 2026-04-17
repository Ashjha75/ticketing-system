package com.ashish.ticketing.modules.notification.dto.response;

import java.time.Instant;

public class NotificationResponse {

    private Long id;
    private String recipient;
    private String subject;
    private String message;
    private String status;
    private Instant sentAt;

    public NotificationResponse() {
    }

    public NotificationResponse(Long id, String recipient, String subject, String message, String status, Instant sentAt) {
        this.id = id;
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.status = status;
        this.sentAt = sentAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public void setSentAt(Instant sentAt) {
        this.sentAt = sentAt;
    }
}
