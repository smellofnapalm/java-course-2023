package edu.maze.solvers;

import edu.maze.Cell;
import edu.maze.Coordinate;
import edu.maze.Maze;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DFSSolver implements Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        if (maze == null || !maze.coordinateIsValid(start) || !maze.coordinateIsValid(end)) {
            throw new IllegalArgumentException("Координаты start или end не принадлежат лабиринту (или лабиринт null)");
        }

        Coordinate initialParent = new Coordinate(-1, -1);
        Set<Coordinate> used = new HashSet<>();
        Map<Coordinate, Coordinate> parents = new HashMap<>();
        dfs(start, initialParent, end, used, parents, maze);
        if (!parents.containsKey(end)) {
            throw new IllegalArgumentException("Не существует пути от start до end!");
        }
        Coordinate cur = end;
        List<Coordinate> path = new ArrayList<>();
        path.add(end);
        while (cur != start) {
            cur = parents.get(cur);
            if (cur == initialParent) {
                break;
            }
            path.add(new Coordinate(cur.row(), cur.col()));
        }
        Collections.reverse(path);
        return path;
    }

    private void dfs(
        Coordinate v, Coordinate parent, Coordinate end,
        Set<Coordinate> used, Map<Coordinate, Coordinate> parents, Maze maze
    ) {
        used.add(v);
        parents.put(v, parent);
        int i = v.row();
        int j = v.col();
        if (v.equals(end)) {
            return;
        }
        List<Coordinate> coordinates = Arrays.stream(SHIFTS)
            .map(shift -> new Coordinate(i + shift[0], j + shift[1]))
            .toList();
        Cell cell = maze.getCell(v);
        boolean[] shiftFlags = {
            !cell.getDownWall(),
            !cell.getRightWall(),
            i > 0 && !maze.getCell(i - 1, j).getDownWall(),
            j > 0 && !maze.getCell(i, j - 1).getRightWall()
        };
        for (int t = 0; t < SHIFTS.length; t++) {
            var u = coordinates.get(t);
            if (maze.coordinateIsValid(u) && !used.contains(u) && shiftFlags[t]) {
                dfs(u, v, end, used, parents, maze);
            }
        }
    }

    static final int[][] SHIFTS = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

}
