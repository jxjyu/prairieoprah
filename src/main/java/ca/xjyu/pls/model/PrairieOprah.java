package ca.xjyu.pls.model;

import ca.xjyu.pls.exceptions.InvalidEntryException;
import ca.xjyu.pls.exceptions.InvalidPasscodeException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class PrairieOprah {
    private ArrayList<Entry> entries;
    private LinkedList<Passcode> passcodes;
    private ArrayList<Passcode> expired;

    public PrairieOprah() {
        entries = new ArrayList<>();
        passcodes = new LinkedList<>();
        expired = new ArrayList<>();
    }

    /**
     * Helper method for the UI to easily add a name.
     */
    public void addEntry(String name, String email, String passcode) {
        Entry current = new Entry(name, email, passcode);
        if (!checkEntry(current)) {
            throw new InvalidEntryException("");
        }
    }

    /**
     * Picks a random winner (Oprah style: "You get a car!")
     */
    public Entry oprah() {
        if (entries.isEmpty()) {
            return null;
        }
        return entries.get((int)(Math.random() * entries.size()));
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public List<Passcode> getCurrentPasscodes() {
        return passcodes;
    }

    public List<Passcode> getExpiredPasscodes() {
        return expired;
    }

    public boolean checkEntry(Entry e) {
        for (int i = passcodes.size() - 1; i >= 0; i--) {
            Passcode current = passcodes.get(i);
            if (current.good(e)) {
                current.use(e);
                entries.add(e);
                checkRemove(current);
                return true;
            }
            checkRemove(current);
        }
        return false;
    }

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
    }

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
}
