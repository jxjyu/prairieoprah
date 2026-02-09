package com.example.model;

import java.time.LocalDateTime;

public class Entry {
    private LocalDateTime time;
    private String user, email, passcode;
    
    public Entry(String user, String email, String passcode) {
        this.user = user;
        this.email = email;
        this.passcode = passcode;
        this.time = LocalDateTime.now();
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

    public LocalDateTime getDateTime() {
        return this.time;
    }

}
