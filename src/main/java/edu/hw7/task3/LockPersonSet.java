package edu.hw7.task3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.jetbrains.annotations.Nullable;

public class LockPersonSet implements PersonDatabase {
    private final Map<Integer, Person> dict = Collections.synchronizedMap(new HashMap<>());
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void add(Person person) {
        if (person == null) {
            return;
        }
        var writeLock = lock.writeLock();
        writeLock.lock();
        try {
            if (findByAddress(person.address()) == null
                && findByName(person.name()) == null
                && findByPhone(person.phoneNumber()) == null) {
                dict.put(person.id(), person);
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void delete(int id) {
        var writeLock = lock.writeLock();
        writeLock.lock();
        try {
            dict.remove(id);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public @Nullable Person findByName(String name) {
        var readLock = lock.readLock();
        readLock.lock();
        try {
            return dict.values().stream().filter(person -> person.name().equals(name)).findAny().orElse(null);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public @Nullable Person findByAddress(String address) {
        var readLock = lock.readLock();
        readLock.lock();
        try {
            return dict.values().stream()
                .filter(person -> person.address().equals(address)).findAny().orElse(null);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public @Nullable Person findByPhone(String phone) {
        var readLock = lock.readLock();
        readLock.lock();
        try {
            return dict.values().stream()
                .filter(person -> person.phoneNumber().equals(phone)).findAny().orElse(null);
        } finally {
            readLock.unlock();
        }
    }

    public @Nullable Person findById(int id) {
        var readLock = lock.readLock();
        readLock.lock();
        try {
            return dict.get(id);
        } finally {
            readLock.unlock();
        }
    }
}
