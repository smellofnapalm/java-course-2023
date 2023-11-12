package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiskMap implements Map<String, String> {
    final Map<String, String> dict = new HashMap<>();
    Path path = Paths.get(Instant.now().getNano() + ".txt");

    DiskMap() throws IOException {
        Files.createFile(path);
    }

    DiskMap(String userFileName) {
        path = Paths.get(userFileName);
        if (!path.toFile().isFile()) {
            throw new IllegalArgumentException("Путь к файлу указан некорректно!");
        }
    }

    void delete() throws IOException {
        Files.delete(path);
    }

    public String toString() {
        return dict.entrySet().stream()
            .map(kv -> "%s:%s\n".formatted(kv.getKey(), kv.getValue()))
            .collect(Collectors.joining());
    }

    void save() throws IOException {
        Files.writeString(path, this.toString());
    }

    void load() throws IOException {
        dict.clear();
        Files.readAllLines(path).stream()
            .map(str -> str.split(":"))
            .forEach(kv -> dict.put(kv[0], kv[1]));
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
