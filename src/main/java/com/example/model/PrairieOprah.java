package com.example.model;

import com.example.exceptions.InvalidEntryException;
import com.example.exceptions.InvalidPasscodeException;import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrairieOprah {
    private ArrayList<Entry> entries;
    private ArrayList<Passcode> passcodes;
    private ArrayList<Passcode> expired;

    public PrairieOprah() {
        entries = new ArrayList<>();
        passcodes = new ArrayList<>();
        expired = new ArrayList<>();
    }

    /**
     * Helper method for the UI to easily add a name.
     */
    public void addEntry(String name, String email, String passcode) {
        Entry current = new Entry(name, email, passcode);
        if (!checkEntry(current)) {
            throw new InvalidEntryException("");
        } else {
            entries.add(current);
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

    public boolean checkEntry(Entry e) {
        for (Passcode p : passcodes) {
            if (p.good(e.getPasscode())) {
                entries.add(e);
                return true;
            }
            if (p.isExpired()) {
                expired.add(p);
                passcodes.remove(p);
            }
        }
        return false;
    }

    public void addPasscode(String passcode, int minutes) {
        Passcode current = new Passcode(passcode, minutes);
        for (Passcode p : passcodes) {
            if (p.equals(current)) {
                throw new InvalidPasscodeException("");
            }
        }
        passcodes.add(current);
    }
}
