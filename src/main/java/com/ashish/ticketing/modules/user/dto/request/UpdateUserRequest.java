package com.ashish.ticketing.modules.user.dto.request;

import jakarta.validation.constraints.Size;

public class UpdateUserRequest {

    @Size(max = 120)
    private String name;

    @Size(min = 8, max = 255)
    private String password;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
