package edu.maze.renderers;

import edu.maze.Coordinate;
import edu.maze.Maze;
import java.util.List;

public interface Renderer {
    String render(Maze maze);

    String render(Maze maze, List<Coordinate> path);
}
