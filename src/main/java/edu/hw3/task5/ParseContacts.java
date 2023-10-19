package edu.hw3.task5;

import java.util.ArrayList;
import java.util.Comparator;

public final class ParseContacts {
    private ParseContacts() {
    }

    public static Contact[] parseContacts(String[] arr, String order) {
        if (arr == null || arr.length == 0) {
            return new Contact[0];
        }
        var upperOrder = order.toUpperCase();
        final Comparator<Contact> ASC = COMPARATOR;
        final Comparator<Contact> DESC = COMPARATOR.reversed();
        final String ASC_STRING = "ASC";
        if (!upperOrder.equals(ASC_STRING) && !upperOrder.equals("DESC")) {
            throw new IllegalArgumentException("Второй аргумент должен быть ASC или DESC!");
        }
        Comparator<Contact> cmp;
        if (order.equals(ASC_STRING)) {
            cmp = ASC;
        } else {
            cmp = DESC;
        }
        ArrayList<Contact> contacts = new ArrayList<>(arr.length);
        for (var s : arr) {
            contacts.add(new Contact(s));
        }
        contacts.sort(cmp);
        return contacts.toArray(new Contact[0]);
    }

    static final Comparator<Contact> COMPARATOR =
        (c1, c2) -> c1.getSurname().compareTo(c2.getSurname());

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
            } else {
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

    }
}
