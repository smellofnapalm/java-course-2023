package edu.hw3.task8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class BackwardIterator<T> implements Iterator<T> {
    private final Iterator<T> iterator;

    BackwardIterator(Collection<T> collection) {
        List<T> toList = new ArrayList<>(collection.stream().toList());
        Collections.reverse(toList);
        this.iterator = toList.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        return iterator.next();
    }
}
