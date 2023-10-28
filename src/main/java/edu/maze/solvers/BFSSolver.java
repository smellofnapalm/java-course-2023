package edu.maze.solvers;

import edu.maze.Cell;
import edu.maze.Coordinate;
import edu.maze.Maze;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BFSSolver implements Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        if (maze == null || !maze.coordinateIsValid(start) || !maze.coordinateIsValid(end)) {
            throw new IllegalArgumentException("Координаты start или end не принадлежат лабиринту (или лабиринт null)");
        }

        Queue<Cell> q = new ArrayDeque<>();
        int[][] dist = new int[maze.height][maze.width];
        Map<Cell, Cell> parent = new HashMap<>();
        int[][] shifts = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

        bfs(maze, start, end, q, dist, parent, shifts);

        if ((start.col() != end.col() || start.row() != end.row()) && dist[end.row()][end.col()] == 0) {
            throw new IllegalArgumentException("Не существует пути от start до end!");
        }

        Cell cur = maze.getCell(end);
        List<Coordinate> path = new ArrayList<>();
        path.add(end);
        while (cur != maze.getCell(start)) {
            cur = parent.get(cur);
            path.add(new Coordinate(cur.row, cur.col));
        }
        Collections.reverse(path);

        return path;
    }

    private static void bfs(
        Maze maze, Coordinate start, Coordinate end,
        Queue<Cell> q, int[][] dist, Map<Cell, Cell> parent, int[][] shifts
    ) {
        q.add(maze.getCell(start));
        while (!q.isEmpty()) {
            Cell u = q.poll();
            if (u.col == end.col() && u.row == end.row()) {
                break;
            }
            int i = u.row;
            int j = u.col;
            boolean[] shiftFlags = {
                i < maze.height - 1 && dist[i + 1][j] == 0 && !u.getDownWall(),
                j < maze.width - 1 && dist[i][j + 1] == 0 && !u.getRightWall(),
                i > 0 && dist[i - 1][j] == 0 && !maze.getCell(i - 1, j).getDownWall(),
                j > 0 && dist[i][j - 1] == 0 && !maze.getCell(i, j - 1).getRightWall()
            };
            for (int t = 0; t < shiftFlags.length; t++) {
                if (!shiftFlags[t]) {
                    continue;
                }
                int iNew = i + shifts[t][0];
                int jNew = j + shifts[t][1];
                q.add(maze.getCell(iNew, jNew));
                dist[iNew][jNew] = dist[i][j] + 1;
                parent.put(maze.getCell(iNew, jNew), u);
            }
        }
    }
}
