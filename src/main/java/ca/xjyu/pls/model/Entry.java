package ca.xjyu.pls.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a user submission or check-in attempt within the system.
 *
 * @author Jeff Yu
 * @version 1.0
 */
@Entity
@Table(name = "entries")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 'user' is a reserved keyword in many SQL dialects, so we use 'user_name' for the column
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "passcode_id", nullable = false)
    private Passcode passcode;

    @Column(name = "entry_time", nullable = false)
    private LocalDateTime entryTime;

    /**
     * Default constructor required for JPA instantiation.
     */
    public Entry() {}

    /**
     * Lifecycle callback to automatically set the creation timestamp
     * before the entity is persisted to the database.
     */
    @PrePersist
    protected void onCreate() {
        if (entryTime == null) {
            entryTime = LocalDateTime.now();
        }
    }

    // --- Standard JPA Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Passcode getPasscode() { return passcode; }
    public void setPasscode(Passcode passcode) { this.passcode = passcode; }

    public LocalDateTime getEntryTime() { return entryTime; }
    public void setEntryTime(LocalDateTime entryTime) { this.entryTime = entryTime; }


    // --- Legacy Getters for Frontend Backwards Compatibility ---

    public String getUser() { return this.userName; }

    public LocalDateTime getDateTime() { return this.entryTime; }

    /**
     * Reaches into the associated Passcode entity to retrieve the literal password string.
     */
    public String getPassword() {
        return this.passcode != null ? this.passcode.getPassword() : null;
    }

    public String getDateTimeString() {
        if (this.entryTime == null) return "";
        return this.entryTime.minusHours(8).format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
        // Retained original implementation
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (entryTime != null ? entryTime.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Entry)) return false;

        Entry that = (Entry) other;

        boolean userEquals = (this.userName == null && that.userName == null)
                || (this.userName != null && this.userName.equals(that.userName));
        boolean emailEquals = (this.email == null && that.email == null)
                || (this.email != null && this.email.equals(that.email));
        boolean timeEquals = (this.entryTime == null && that.entryTime == null)
                || (this.entryTime != null && this.entryTime.equals(that.entryTime));

        return userEquals && emailEquals && timeEquals;
    }
}