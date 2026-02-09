package model;

import java.time.LocalDateTime;

public class Passcode {
    private String password;
    private LocalDateTime creation, expiry;

    public Passcode(int minutes) {
        password = "test";
        creation = LocalDateTime.now();
        expiry = LocalDateTime.now().plusMinutes(minutes);
    }

    public boolean good(String password) {
        if (password.equals(this.password) && LocalDateTime.now().isBefore(expiry)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isExpired() {
        if (LocalDateTime.now().isBefore(expiry)) {
            return false;
        } else {
            return true;
        }
    }

    public LocalDateTime getCreationTime() {
        return this.creation;
    }
    
    public LocalDateTime getExpiryTime() {
        return this.expiry;
    }
    
    private String getPassword() {
        return this.password;
    }

    @Override
    public int hashCode() {
        return password.hashCode() * 37 + creation.hashCode() * 37 + expiry.hashCode() * 37;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Passcode)) {
            return false;
        }
        Passcode toCompare = (Passcode)other;
        if (toCompare.getPassword().equals(this.password)) {
            return true;
        } else {
            return false;
        }
    }
}
