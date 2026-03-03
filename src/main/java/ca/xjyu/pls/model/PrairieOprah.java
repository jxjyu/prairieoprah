package ca.xjyu.pls.model;

import ca.xjyu.pls.exceptions.InvalidEntryException;
import ca.xjyu.pls.exceptions.InvalidPasscodeException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The primary service responsible for managing the lifecycle of entries and passcodes.
 * <p>
 * This class handles the registration of passcodes, the processing of user entries,
 * and the persistence of the application state to a JSON file.
 *
 * @author Jeff Yu
 * @version 0.3
 */
@Service
public class PrairieOprah {
    private ArrayList<Entry> entries;
    private LinkedList<Passcode> passcodes;
    private ArrayList<Passcode> expired;

    private final ObjectMapper mapper;
    private final File dataFile = new File("oprahdata.json");
    private boolean safeToSave = false;

    /**
     * Initialises the service and configures the JSON object mapper.
     */
    public PrairieOprah() {
        entries = new ArrayList<>();
        passcodes = new LinkedList<>();
        expired = new ArrayList<>();

        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Loads the application state from the local JSON file.
     * <p>
     * If the file is missing, it assumes a fresh start. If the load fails,
     * saving is disabled to prevent overwriting existing data with an empty state.
     */
    @PostConstruct
    public void loadState() {
        if (dataFile.exists()) {
            try {
                AppState state = mapper.readValue(dataFile, AppState.class);
                this.entries = state.entries != null ? state.entries : new ArrayList<>();
                this.passcodes = state.passcodes != null ? state.passcodes : new LinkedList<>();
                this.expired = state.expired != null ? state.expired : new ArrayList<>();
                safeToSave = true;
            } catch (IOException e) {
                System.err.println("Failed to load application state: " + e.getMessage());
            }
        } else {
            safeToSave = true; // No file exists yet, so it is safe to create one
        }
    }

    /**
     * Persists the current state of entries and passcodes to the JSON file.
     * <p>
     * This method is triggered before the bean is destroyed by the Spring container.
     */
    @PreDestroy
    public void saveState() {
        if (!safeToSave) {
            return;
        }
        try {
            AppState state = new AppState(this.entries, this.passcodes, this.expired);
            mapper.writerWithDefaultPrettyPrinter().writeValue(dataFile, state);
        } catch (IOException e) {
            System.err.println("Failed to save application state: " + e.getMessage());
        }
    }

    /**
     * Attempts to add a new user entry.
     *
     * @param name     Name of the user.
     * @param email    Email of the user.
     * @param passcode The passcode string provided.
     * @throws InvalidEntryException if the entry does not match any valid passcode.
     */
    public void addEntry(String name, String email, String passcode) {
        Entry current = new Entry(name, email, passcode);
        if (!checkEntry(current)) {
            throw new InvalidEntryException("");
        }
    }

    /**
     * Selects a random entry from the pool of successful entries.
     *
     * @return A random {@link Entry}, or {@code null} if no entries exist.
     */
    public Entry oprah() {
        if (entries.isEmpty()) {
            return null;
        }
        return entries.get((int)(Math.random() * entries.size()));
    }

    /**
     * Returns the list of currently active passcodes after refreshing their status.
     *
     * @return A list of non-expired {@link Passcode} objects.
     */
    public List<Passcode> getCurrentPasscodes() {
        refreshCurrent();
        return passcodes;
    }
    /**
     * Returns the list of saved expired passcodes.
     * <p>
     * May not include all expired passcodes unless refreshed immediately prior.
     *
     * @return A list of expired {@link Passcode} objects.
     */
    public List<Passcode> getExpiredPasscodes() { return expired; }

    /**
     * Checks an entry against all active passcodes.
     * <p>
     * If a match is found, the entry is recorded, the passcode usage is updated,
     * and the state is saved.
     *
     * @param e The entry to verify.
     * @return {@code true} if the entry was successfully processed.
     */
    public boolean checkEntry(Entry e) {
        boolean stateChanged = false;
        for (int i = passcodes.size() - 1; i >= 0; i--) {
            Passcode current = passcodes.get(i);
            if (current.good(e)) {
                current.use(e);
                entries.add(e);
                checkRemove(current);
                saveState();
                return true;
            }
            if (checkRemove(current)) {
                stateChanged = true;
            }
        }
        if (stateChanged) saveState();
        return false;
    }

    /**
     * Registers a new passcode in the system.
     *
     * @param passcode   The password string.
     * @param minutes    Validity duration.
     * @param uses       Maximum allowed uses.
     * @param department Associated department.
     * @throws InvalidPasscodeException if the passcode is already active or matches an expired one.
     */
    public void addPasscode(String passcode, int minutes, int uses, String department) {
        Passcode current = new Passcode(passcode, minutes, uses, department);
        for (Passcode p : passcodes) {
            if (p.equals(current)) {
                throw new InvalidPasscodeException("Your passcode is already in use!");
            }
        }
        for (Passcode p : expired) {
            if (p.equals(current)) {
                throw new InvalidPasscodeException("Your password can't be the same as an expired passcode!");
            }
        }
        passcodes.addFirst(current);
        saveState(); // Auto-save on mutation
    }

    /**
     * Identifies which department a specific entry belongs to based on the passcode used.
     *
     * @param e The entry to look up.
     * @return The department name, or {@code null} if the entry is not found.
     */
    public String mapDepartment(Entry e) {
        if (!entries.contains(e)) {
            return null;
        }
        for (Passcode p : passcodes) {
            if (p.hasEntry(e)) {
                return p.getDepartment();
            }
        }
        for (Passcode p : expired) {
            if (p.hasEntry(e)) {
                return p.getDepartment();
            }
        }
        return null;
    }

    /**
     * Evaluates if a passcode should be moved to the expired list or removed entirely.
     *
     * @param current The passcode to evaluate.
     * @return {@code true} if the passcode was moved or removed.
     */
    public boolean checkRemove(Passcode current) {
        if (current.isExpired()) {
            if (current.hasEntries()) {
                expired.add(current);
                passcodes.remove(current);
            } else {
                passcodes.remove(current);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Iterates through active passcodes to prune those that have expired.
     */
    private void refreshCurrent() {
        for (int i = passcodes.size() - 1; i >= 0; i--) {
            checkRemove(passcodes.get(i));
        }
    }

    /**
     * Data Transfer Object used for serialising the application state to JSON.
     */
    public static class AppState {
        public ArrayList<Entry> entries;
        public LinkedList<Passcode> passcodes;
        public ArrayList<Passcode> expired;

        public AppState() {}

        public AppState(ArrayList<Entry> entries, LinkedList<Passcode> passcodes, ArrayList<Passcode> expired) {
            this.entries = entries;
            this.passcodes = passcodes;
            this.expired = expired;
        }
    }

    public List<Entry> getEntries() { return entries; }
}