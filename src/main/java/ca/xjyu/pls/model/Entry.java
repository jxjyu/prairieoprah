package ca.xjyu.pls.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Entry {
    private LocalDateTime time;
    private String user, email, password;

    public Entry(String user, String email, String passcode, LocalDateTime time) {
        this.user = user;
        this.email = email;
        this.password = passcode;
        this.time = time;
    }

    public Entry(String user, String email, String passcode) {
        this(user, email, passcode, LocalDateTime.now());
    }

    public String getUser() {
        return this.user;
    }
    
    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public LocalDateTime getDateTime() {
        return this.time;
    }

    public String getDateTimeString() {
        return this.time.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
    }

    @Override
    public int hashCode() {
        return user.hashCode() * 37 + email.hashCode() * 37 + password.hashCode() * 37 +
                time.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Entry)) {
            return false;
        }
        Entry toCompare = (Entry) other;
        if (!toCompare.getUser().equals(this.user)) {
            return false;
        } else if (!toCompare.getDateTime().equals(this.time)) {
            return false;
        } else if (!toCompare.getEmail().equals(this.email)) {
            return false;
        } else if (!toCompare.getPassword().equals(this.password)) {
            return false;
        } else {
            return true;
        }
    }

}
