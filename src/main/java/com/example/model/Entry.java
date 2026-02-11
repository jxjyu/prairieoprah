package com.example.model;

import java.time.LocalDateTime;

public class Entry {
    private LocalDateTime time;
    private String user, email, passcode;
    private String department;

    public Entry(String user, String email, String passcode) {
        this.user = user;
        this.email = email;
        this.passcode = passcode;
        this.time = LocalDateTime.now();
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUser() {
        return this.user;
    }
    
    public String getEmail() {
        return this.email;
    }

    public String getPasscode() {
        return this.passcode;
    }

    public String getDepartment() {
        return this.department;
    }

    public LocalDateTime getDateTime() {
        return this.time;
    }

}
