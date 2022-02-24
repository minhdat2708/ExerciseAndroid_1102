package com.example.baitapvandung_1102;

import java.util.Comparator;

public class NameComparator implements Comparator<Contacts> {
    @Override
    public int compare(Contacts contacts, Contacts t1) {
        return contacts.getName().compareTo(t1.getName());
    }
}
