package com.ashish.ticketing.modules.user.dto.request;

public class UpdateUserRequest {

    private String firstName;
    private String lastName;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
