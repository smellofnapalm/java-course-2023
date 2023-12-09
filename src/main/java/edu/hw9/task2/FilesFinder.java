package edu.hw9.task2;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public final class FilesFinder {
    private static final int FILES_IN_DIR = 1000;
    private final static String ERROR = "Данного пути не существует или это обычный файл!";

    public static List<Path> findLargeDirs(Path root) {
        List<Path> largeDirs = Collections.synchronizedList(new ArrayList<>());
        var ans = new FilesInDir(root, largeDirs).compute();
        return largeDirs;
    }

    public static List<Path> findFilesByFilter(Path root, long size, String extension) {
        List<Path> filteredFiles = Collections.synchronizedList(new ArrayList<>());
        new FilesByFilter(root, size, extension, filteredFiles).compute();
        return filteredFiles;
    }

    private FilesFinder() {
    }

    static class FilesInDir extends RecursiveTask<Integer> {
        final Path root;
        final List<Path> largeDirs;

        FilesInDir(Path root, List<Path> largeDirs) {
            this.root = root;
            this.largeDirs = largeDirs;
        }

        public Integer compute() {
            if (!Files.exists(root) || !Files.isDirectory(root)) {
                throw new IllegalArgumentException(ERROR);
            }
            int countFiles = Objects.requireNonNull(root.toFile().listFiles(File::isFile)).length;
            File[] dirs = root.toFile().listFiles(File::isDirectory);
            if (dirs == null) {
                return 0;
            }
            List<FilesInDir> subDirs = new ArrayList<>();
            for (var dir : dirs) {
                FilesInDir nextDir = new FilesInDir(dir.toPath(), largeDirs);
                subDirs.add(nextDir);
                nextDir.fork();
            }
            Integer res = countFiles;
            for (var dir : subDirs) {
                res += dir.join();
            }
            if (res > FILES_IN_DIR) {
                largeDirs.add(root);
            }
            return res;
        }
    }

    static class FilesByFilter extends RecursiveAction {
        final Path root;
        final long size;
        final String extension;
        final List<Path> filteredFiles;

        FilesByFilter(Path root, long size, String extension, List<Path> filteredFiles) {
            this.root = root;
            this.size = size;
            this.extension = extension;
            this.filteredFiles = filteredFiles;
        }

        @Override
        protected void compute() {
            if (!Files.exists(root) || !Files.isDirectory(root)) {
                throw new IllegalArgumentException(ERROR);
            }
            filteredFiles.addAll(Arrays.stream(Objects.requireNonNull(root.toFile().listFiles(f ->
                f.isFile()
                    && f.toPath().getFileName().toString().endsWith(extension)
                    && f.length() >= size))).map(File::toPath).toList());
            File[] dirs = root.toFile().listFiles(File::isDirectory);
            if (dirs == null) {
                return;
            }
            List<FilesByFilter> subDirs = new ArrayList<>();
            for (var dir : dirs) {
                FilesByFilter nextDir = new FilesByFilter(dir.toPath(), size, extension, filteredFiles);
                subDirs.add(nextDir);
                nextDir.fork();
            }
            subDirs.forEach(ForkJoinTask::join);
        }
    }
}
