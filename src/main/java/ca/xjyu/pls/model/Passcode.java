package ca.xjyu.pls.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Passcode {
    private String password, department;
    private LocalDateTime creation, expiry;
    private ArrayList<String> emails;
    public int uses;

    public Passcode(String password, int minutes, int maxUses, String department) {
        this.password = password;
        this.uses = maxUses;
        this.emails = new ArrayList<>();
        this.department = department;
        creation = LocalDateTime.now();
        expiry = LocalDateTime.now().plusMinutes(minutes);
    }

    public boolean good(Entry e) {
        return e.getPassword().equals(this.password) && !this.isExpired() &&
                !hasEntry(e);
    }

    public void use(Entry e) {
        if (uses > 0) {
            uses--;
        }
        emails.add(e.getEmail());
    }

    public boolean hasEntry(Entry e) {
        return emails.contains(e.getEmail());
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiry) || !(uses > 0 || uses == -1);
    }

    public boolean hasEntries() {
        return !emails.isEmpty();
    }

    public LocalDateTime getCreationTime() {
        return this.creation;
    }

    public String getCreationTimeString() {
        return this.creation.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
    }
    
    public LocalDateTime getExpiryTime() {
        return this.expiry;
    }

    public String getExpiryTimeString() {
        return this.expiry.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
    }

    public String getDepartment() {
        return this.department;
    }
    
    public String getPassword() {
        return this.password;
    }

    public int getUses() {
        return this.emails.size();
    }

    public int getRemainingUses() {
        return this.uses;
    }


    @Override
    public int hashCode() {
        return password.hashCode() * 37 + creation.hashCode() * 37 + expiry.hashCode() * 37
                + department.hashCode() * 37 + emails.hashCode() * 37;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Passcode)) {
            return false;
        }
        Passcode toCompare = (Passcode) other;
        if (toCompare.getPassword().equals(this.password)) {
            return true;
        } else {
            return false;
        }
    }
}
