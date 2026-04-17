package com.ashish.ticketing.modules.user.dto.response;

import java.time.Instant;

public class UserProfileResponse {

    private Long id;
    private String email;
    private String role;
    private String status;
    private Instant createdAt;

    public UserProfileResponse() {
    }

    public UserProfileResponse(Long id, String email, String role, String status, Instant createdAt) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
