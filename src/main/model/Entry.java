package model;

import java.time.LocalDateTime;

public class Entry {
    private LocalDateTime time;
    private String user, email;
    
    public Entry(String user, String email) {
        this.user = user;
        this.email = email;
        this.time = LocalDateTime.now();
    }

}
