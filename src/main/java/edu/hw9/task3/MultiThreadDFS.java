package edu.hw9.task3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MultiThreadDFS {
    private final Graph graph;
    private List<Boolean> used;
    private final ReentrantReadWriteLock usedLock = new ReentrantReadWriteLock();
    private List<Integer> parent;

    MultiThreadDFS(Graph graph) {
        this.graph = graph;
    }

    public List<Integer> findPath(int begin, int end) {
        if (begin >= graph.n || begin < 0 || end >= graph.n || end < 0) {
            throw new IllegalArgumentException("Некорректные вершины графа!");
        }

        this.used = Stream.generate(() -> false).limit(graph.n).collect(Collectors.toList());
        this.parent = Stream.generate(() -> -1).limit(graph.n).collect(Collectors.toList());
        List<Integer> path = new ArrayList<>();

        new DFS(begin, end).compute();
        if (parent.get(end) == -1) {
            throw new IllegalArgumentException("Между вершинами нет пути!");
        }
        int cur = end;
        while (cur != begin) {
            path.add(cur);
            cur = parent.get(cur);
        }
        path.add(begin);
        Collections.reverse(path);
        return path;
    }

    class DFS extends RecursiveAction {
        private final int vertex;
        private final int end;

        DFS(int vertex, int end) {
            this.vertex = vertex;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (vertex == end) {
                return;
            }
            List<DFS> dfsList = new ArrayList<>();
            for (int u : graph.adjacencyList.get(vertex)) {
                usedLock.readLock().lock();
                final boolean flag = used.get(u);
                usedLock.readLock().unlock();
                if (!flag) {
                    try {
                        usedLock.writeLock().lock();
                        used.set(u, true);
                        parent.set(u, vertex);
                    } finally {
                        usedLock.writeLock().unlock();
                    }
                    var newU = new DFS(u, end);
                    newU.fork();
                    dfsList.add(newU);
                }
            }
            dfsList.forEach(ForkJoinTask::join);
        }
    }
}
