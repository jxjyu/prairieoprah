package ca.xjyu.pls.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Defines a credential with specific usage constraints and temporal validity.
 * <p>
 * A passcode is tied to a specific department and may have a limited number
 * of uses or a fixed expiration window.
 *
 * @author Jeff Yu
 * @version 0.3
 */
public class Passcode {
    private String password, department;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime creation;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime expiry;

    private ArrayList<String> emails;
    public int uses;

    /**
     * Default constructor required for Jackson deserialisation.
     */
    public Passcode() {}

    /**
     * Creates a new Passcode with defined limits.
     *
     * @param password   The literal string required for authentication.
     * @param minutes    The duration from the moment of creation until the passcode expires.
     * @param maxUses    The maximum number of times this passcode can be successfully used.
     * @param department The department or group this passcode belongs to.
     */
    public Passcode(String password, int minutes, int maxUses, String department) {
        this.password = password;
        this.uses = maxUses;
        this.emails = new ArrayList<>();
        this.department = department;
        creation = LocalDateTime.now();
        expiry = LocalDateTime.now().plusMinutes(minutes);
    }

    /**
     * Validates whether a given entry is eligible to use this passcode.
     *
     * @param e The entry to validate.
     * @return {@code true} if the passcode matches, is not expired, and the user hasn't used it yet.
     */
    public boolean good(Entry e) {
        return e.getPassword().equals(this.password) && !this.isExpired() && !hasEntry(e);
    }

    /**
     * Records the usage of this passcode by a specific entry and decrements the usage counter.
     *
     * @param e The entry consuming a use of this passcode.
     */
    public void use(Entry e) {
        if (uses > 0) {
            uses--;
        }
        emails.add(e.getEmail());
    }

    /**
     * Checks if a specific email address has already utilised this passcode.
     *
     * @param e The entry containing the email to check.
     * @return {@code true} if the email is already in the usage list.
     */
    public boolean hasEntry(Entry e) {
        return emails.contains(e.getEmail());
    }

    /**
     * Determines if the passcode is no longer valid due to time or usage exhaustion.
     *
     * @return {@code true} if the current time is past expiry or no uses remain.
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiry) || !(uses > 0 || uses == -1);
    }

    /**
     * Checks if any users have successfully used this passcode.
     *
     * @return {@code true} if the usage list is not empty.
     */
    public boolean hasEntries() {
        return !emails.isEmpty();
    }

    public LocalDateTime getCreationTime() { return this.creation; }
    public String getCreationTimeString() {
        return this.creation.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
    }

    public LocalDateTime getExpiryTime() { return this.expiry; }
    public String getExpiryTimeString() {
        return this.expiry.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
    }

    public String getDepartment() { return this.department; }
    public String getPassword() { return this.password; }
    public int getUses() { return this.emails.size(); }
    public int getRemainingUses() { return this.uses; }

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
        return toCompare.getPassword().equals(this.password);
    }
}