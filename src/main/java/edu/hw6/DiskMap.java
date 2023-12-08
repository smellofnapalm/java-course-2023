package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiskMap implements Map<String, String> {
    final Map<String, String> dict = new HashMap<>();
    final Path path;

    DiskMap(String userFileName) {
        path = Paths.get(userFileName);
        if (!path.toFile().isFile()) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            load();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить данные из файла!", e);
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

    private void save() {
        try {
            Files.writeString(path, this.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void load() throws IOException {
        dict.clear();
        try {
            Files.readAllLines(path).stream()
                .map(str -> str.split(":"))
                .forEach(kv -> dict.put(kv[0], kv[1]));
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        try {
            Files.writeString(path, "%s:%s".formatted(key, value), StandardOpenOption.APPEND);
            return dict.put(key, value);
        } catch (IOException e) {
            throw new IllegalArgumentException("Невозможно обновить файл на диске!");
        }
    }

    @Override
    public String remove(Object key) {
        var toReturn = dict.remove(key);
        save();
        return toReturn;
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        for (var kv : m.entrySet()) {
            this.put(kv.getKey(), kv.getValue());
        }
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
