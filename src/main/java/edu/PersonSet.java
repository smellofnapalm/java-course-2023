package edu;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class PersonSet implements PersonDatabase {
    final Map<Integer, Person> dict = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void add(Person person) {
        if (person == null) {
            return;
        }
        synchronized (this) {
            if (findByAddress(person.address()) == null
                && findByName(person.name()) == null
                && findByPhone(person.phoneNumber()) == null) {
                dict.put(person.id(), person);
            }
        }
    }

    @Override
    public void delete(int id) {
        synchronized (this) {
            dict.remove(id);
        }
    }

    @Override
    public @Nullable Person findByName(String name) {
        return dict.values().stream().filter(person -> person.name().equals(name)).findAny().orElse(null);
    }

    @Override
    public @Nullable Person findByAddress(String address) {
        return dict.values().stream().filter(person -> person.address().equals(address)).findAny().orElse(null);
    }

    @Override
    public @Nullable Person findByPhone(String phone) {
        return dict.values().stream().filter(person -> person.phoneNumber().equals(phone)).findAny().orElse(null);
    }

    public @Nullable Person findById(int id) {
        return dict.get(id);
    }
}
