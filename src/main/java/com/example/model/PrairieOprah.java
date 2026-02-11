package com.example.model;

import com.example.exceptions.InvalidEntryException;
import com.example.exceptions.InvalidPasscodeException;
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

    public boolean checkEntry(Entry e) {
        for (int i = passcodes.size() - 1; i >= 0; i--) {
            Passcode current = passcodes.get(i);
            if (current.good(e)) {
                current.use(e);
                entries.add(e);
                return true;
            }
            if (current.isExpired()) {
                if (current.hasEntries()) {
                    expired.add(current);
                    passcodes.remove(current);
                }
            }
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
        passcodes.add(current);
    }
}
