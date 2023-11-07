package edu.maze.solvers;

import edu.maze.Coordinate;
import edu.maze.Maze;
import java.util.List;

public interface Solver {
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
