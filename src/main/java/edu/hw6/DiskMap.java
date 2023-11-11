package edu.hw6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiskMap implements Map<String, String> {
    final Map<String, String> dict = new HashMap<>();
    Path path = Paths.get(Instant.now().getNano() + ".txt");
    RandomAccessFile file;

    DiskMap() throws FileNotFoundException {
        file = new RandomAccessFile(path.toFile(), "rw");
    }

    DiskMap(String userFileName) throws FileNotFoundException {
        path = Paths.get(userFileName);
        file = new RandomAccessFile(path.toFile(), "rw");
    }

    void delete() throws IOException {
        file.close();
        Files.delete(path);
    }

    void save() throws IOException {
        file.seek(0);
        final char delim = ':';
        final char newLine = '\n';
        for (var entry : dict.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            file.writeChars(key);
            file.writeChar(delim);
            file.writeChars(value);
            file.writeChar(newLine);
        }
    }

    void load() throws IOException {
        file.close();
        file = new RandomAccessFile(path.toFile(), "rw");
        dict.clear();
        StringBuilder sb = new StringBuilder();
        while (file.getFilePointer() < file.length()) {
            char c = file.readChar();
            if (c == 0) {
                return;
            }
            if (c == '\n') {
                var keyAndValue = sb.toString().split(":");
                dict.put(keyAndValue[0], keyAndValue[1]);
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }

        }
    }

    @Override
    public int size() {
        return dict.size();
    }

    @Override
    public boolean isEmpty() {
        return dict.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return dict.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return dict.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return dict.get(key);
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        return dict.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return dict.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        dict.putAll(m);
    }

    @Override
    public void clear() {
        dict.clear();
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return dict.keySet();
    }

    @NotNull
    @Override
    public Collection<String> values() {
        return dict.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        return dict.entrySet();
    }
}
