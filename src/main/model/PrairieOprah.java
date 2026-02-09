package model;

import java.util.ArrayList;

public class PrairieOprah {
    private ArrayList<Entry> entries;
    private ArrayList<Passcode> passcodes;
    private ArrayList<Passcode> expired;

    public PrairieOprah() {
        entries = new ArrayList<>();
        passcodes = new ArrayList<>();
        expired = new ArrayList<>();
    }
    
    public Entry oprah() {
        return entries.get((int)(Math.random() * entries.size()));
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

    public void createPasscode(int minutes) {
        passcodes.add(new Passcode(minutes));
    }
}
