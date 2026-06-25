package ca.xjyu.pls.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.hibernate.annotations.Formula;

/**
 * Defines a credential with specific usage constraints and temporal validity.
 *
 * @author Jeff Yu
 * @version 1.0
 */
@Entity
@Table(name = "passcodes")
public class Passcode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(name = "department")
    private String department;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;

    @Column(name = "max_uses")
    private int maxUses;

    /**
     * Default constructor required for JPA instantiation.
     */
    public Passcode() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public LocalDateTime getCreationTime() { return creationTime; }
    public void setCreationTime(LocalDateTime creationTime) { this.creationTime = creationTime; }

    public LocalDateTime getExpiryTime() { return expiryTime; }
    public void setExpiryTime(LocalDateTime expiryTime) { this.expiryTime = expiryTime; }

    public int getMaxUses() { return maxUses; }
    public void setMaxUses(int maxUses) { this.maxUses = maxUses; }

    /**
     * Determines if the passcode is no longer valid due to time exhaustion.
     * <p>
     * Note: Usage exhaustion is now evaluated at the database level by the service layer.
     *
     * @return {@code true} if the current time is past expiry.
     */
    public boolean isExpired() {
        if (expiryTime == null) return false;
        return LocalDateTime.now().isAfter(expiryTime);
    }

    public String getCreationTimeString() {
        if (creationTime == null) return "";
        return this.creationTime.minusHours(8).format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
    }

    public String getExpiryTimeString() {
        if (expiryTime == null) return "";
        return this.expiryTime.minusHours(8).format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
    }

    @Override
    public int hashCode() {
        return password != null ? password.hashCode() : 0;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Passcode)) {
            return false;
        }
        Passcode toCompare = (Passcode) other;
        return this.password != null && this.password.equals(toCompare.getPassword());
    }

    /**
     * A read-only field calculated by Hibernate upon fetching.
     * It dynamically counts the number of related records in the 'entries' table.
     */
    @Formula("(SELECT COUNT(*) FROM entries e WHERE e.passcode_id = id)")
    private int currentUses;

    /**
     * Returns the total number of times this passcode has been successfully used.
     * Replaces the old emails.size() logic.
     */
    public int getUses() {
        return currentUses;
    }

    /**
     * Calculates the remaining valid uses.
     */
    public int getRemainingUses() {
        if (maxUses == -1) {
            return -1; // -1 indicates unlimited uses
        }
        return Math.max(0, maxUses - currentUses);
    }

    /**
     * Checks if any users have successfully used this passcode.
     */
    public boolean hasEntries() {
        return currentUses > 0;
    }
}