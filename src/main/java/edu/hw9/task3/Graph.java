package edu.hw9.task3;

import java.util.ArrayDeque;
import java.util.List;
import java.util.stream.Stream;

public class Graph {
    final List<ArrayDeque<Integer>> adjacencyList;
    final int n;

    Graph(int n, List<Edge> edges) {
        adjacencyList = Stream.generate(() -> new ArrayDeque<Integer>()).limit(n).toList();
        for (var edge : edges) {
            adjacencyList.get(edge.u).add(edge.v);
            adjacencyList.get(edge.v).add(edge.u);
        }
        this.n = n;
    }

    static record Edge(int u, int v) {
    }
}
