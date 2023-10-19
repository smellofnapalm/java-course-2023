package edu.hw3.task5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ParseContacts {
    static class Contact {
        private final String name;
        private final String surname;
        private final String fullName;

        Contact(String fullName) {
            this.fullName = fullName;
            var arr = fullName.split(" ", 2);
            this.name = arr[0];
            if (arr.length == 2) {
                this.surname = arr[1];
            }
            else {
                this.surname = null;
            }
        }

        String getName() {
            return name;
        }

        public String getSurname() {
            return surname == null ? fullName : surname;
        }

        String getFullName() {
            return fullName;
        }

        @Override
        public String toString() {
            return fullName;
        }

        public boolean equals(Contact other) {
            return this.fullName.equals(other.getFullName());
        }
    }

    static final Comparator<Contact> comparator =
        (c1, c2) -> c1.getSurname().compareTo(c2.getSurname());

    public static Contact[] parseContacts(String[] arr, String order) {
        if (arr == null || arr.length == 0) {
            return new Contact[0];
        }
        var upperOrder = order.toUpperCase();
        Comparator<Contact> ASC = comparator;
        Comparator<Contact> DESC = comparator.reversed();
        if (!upperOrder.equals("ASC") && !upperOrder.equals("DESC")) {
            throw new IllegalArgumentException("Второй аргумент должен быть ASC или DESC!");
        }
        Comparator<Contact> cmp;
        if (order.equals("ASC")) {
            cmp = ASC;
        }
        else {
            cmp = DESC;
        }
        ArrayList<Contact> contacts = new ArrayList<>(arr.length);
        for (var s : arr) {
            contacts.add(new Contact(s));
        }
        contacts.sort(cmp);
        return contacts.toArray(new Contact[0]);
    }
}
