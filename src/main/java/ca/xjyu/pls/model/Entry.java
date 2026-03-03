package ca.xjyu.pls.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a user submission or check-in attempt within the system.
 * <p>
 * This class captures the identity of the user, their contact details,
 * the passcode they provided, and the specific timestamp of the entry.
 *
 * @author Jeff Yu
 * @version 0.3
 */
public class Entry {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime time;
    private String user, email, password;

    // Default constructor required for Jackson deserialisation
    public Entry() {}

    /**
     * Constructs a new Entry with a specific timestamp.
     *
     * @param user     The name or identifier of the user.
     * @param email    The email address associated with the entry.
     * @param passcode The password or token provided by the user.
     * @param time     The exact date and time the entry was created.
     */
    public Entry(String user, String email, String passcode, LocalDateTime time) {
        this.user = user;
        this.email = email;
        this.password = passcode;
        this.time = time;
    }

    /**
     * Constructs a new Entry, automatically assigning the current system time.
     *
     * @param user     The name or identifier of the user.
     * @param email    The email address associated with the entry.
     * @param passcode The password or token provided by the user.
     */
    public Entry(String user, String email, String passcode) {
        this(user, email, passcode, LocalDateTime.now());
    }

    public String getUser() { return this.user; }
    public String getEmail() { return this.email; }
    public String getPassword() { return this.password; }
    public LocalDateTime getDateTime() { return this.time; }

    /**
     * Returns the entry timestamp as a human-readable string.
     *
     * @return A formatted string in the pattern "dd MMM yyyy HH:mm:ss".
     */
    public String getDateTimeString() {
        return this.time.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
    }


    @Override
    public int hashCode() {
        return user.hashCode() * 37 + email.hashCode() * 37 + password.hashCode() * 37 + time.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Entry)) {
            return false;
        }
        Entry toCompare = (Entry) other;
        return toCompare.getUser().equals(this.user) &&
                toCompare.getDateTime().equals(this.time) &&
                toCompare.getEmail().equals(this.email) &&
                toCompare.getPassword().equals(this.password);
    }
}