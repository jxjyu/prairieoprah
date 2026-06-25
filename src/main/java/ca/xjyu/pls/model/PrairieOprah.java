package ca.xjyu.pls.model;

import ca.xjyu.pls.exceptions.InvalidEntryException;
import ca.xjyu.pls.exceptions.InvalidPasscodeException;
import ca.xjyu.pls.repository.EntryRepository;
import ca.xjyu.pls.repository.PasscodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The primary service responsible for managing the lifecycle of entries and passcodes.
 *
 * @author Jeff Yu
 * @version 1.0
 */
@Service
public class PrairieOprah {

    private final PasscodeRepository passcodeRepository;
    private final EntryRepository entryRepository;

    public PrairieOprah(PasscodeRepository passcodeRepository, EntryRepository entryRepository) {
        this.passcodeRepository = passcodeRepository;
        this.entryRepository = entryRepository;
    }

    /**
     * Attempts to add a new user entry.
     *
     * @param name        Name of the user.
     * @param email       Email of the user.
     * @param passcodeStr The passcode string provided.
     * @throws InvalidEntryException if the entry does not match any valid passcode,
     * is expired, or if usage limits are reached.
     */
    @Transactional
    public void addEntry(String name, String email, String passcodeStr) {
        Passcode passcode = passcodeRepository.findByPassword(passcodeStr)
                .orElseThrow(() -> new InvalidEntryException(""));

        if (passcode.isExpired()) {
            throw new InvalidEntryException("");
        }

        // A specific email may only use a given passcode once
        if (entryRepository.existsByEmailAndPasscode(email, passcode)) {
            throw new InvalidEntryException("");
        }

        // Enforce usage limits (-1 indicates unlimited uses)
        if (passcode.getMaxUses() != -1) {
            long currentUses = entryRepository.countByPasscode(passcode);
            if (currentUses >= passcode.getMaxUses()) {
                throw new InvalidEntryException("");
            }
        }

        Entry entry = new Entry();
        entry.setUserName(name);
        entry.setEmail(email);
        entry.setPasscode(passcode);
        entry.setEntryTime(LocalDateTime.now());

        entryRepository.save(entry);
    }

    /**
     * Selects a random entry from the pool of successful entries.
     *
     * @return A random {@link Entry}, or {@code null} if no entries exist.
     */
    public Entry oprah() {
        List<Entry> allEntries = entryRepository.findAll();
        if (allEntries.isEmpty()) {
            return null;
        }
        return allEntries.get((int) (Math.random() * allEntries.size()));
    }

    /**
     * Returns the list of currently active passcodes.
     *
     * @return A list of non-expired {@link Passcode} objects.
     */
    public List<Passcode> getCurrentPasscodes() {
        return passcodeRepository.findByExpiryTimeAfter(LocalDateTime.now());
    }

    /**
     * Returns the list of expired passcodes.
     *
     * @return A list of expired {@link Passcode} objects.
     */
    public List<Passcode> getExpiredPasscodes() {
        return passcodeRepository.findByExpiryTimeBefore(LocalDateTime.now());
    }

    /**
     * Registers a new passcode in the system.
     *
     * @param passcode   The password string.
     * @param minutes    Validity duration in minutes.
     * @param uses       Maximum allowed uses.
     * @param department Associated department.
     * @throws InvalidPasscodeException if the passcode string is already in the database.
     */
    @Transactional
    public void addPasscode(String passcode, int minutes, int uses, String department) {
        if (passcodeRepository.findByPassword(passcode).isPresent()) {
            throw new InvalidPasscodeException("Your passcode is already in use or matches an expired one!");
        }

        Passcode current = new Passcode();
        current.setPassword(passcode);
        current.setCreationTime(LocalDateTime.now());
        current.setExpiryTime(LocalDateTime.now().plusMinutes(minutes));
        current.setMaxUses(uses);
        current.setDepartment(department);

        passcodeRepository.save(current);
    }

    /**
     * Identifies which department a specific entry belongs to based on the passcode used.
     *
     * @param e The entry to look up.
     * @return The department name, or {@code null} if the entry has no associated passcode.
     */
    public String mapDepartment(Entry e) {
        if (e != null && e.getPasscode() != null) {
            return e.getPasscode().getDepartment();
        }
        return null;
    }

    /**
     * Retrieves all entries in the system.
     *
     * @return A list of all {@link Entry} objects.
     */
    public List<Entry> getEntries() {
        return entryRepository.findAll();
    }
}