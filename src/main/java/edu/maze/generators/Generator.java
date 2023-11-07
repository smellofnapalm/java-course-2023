package edu.maze.generators;

import edu.maze.Maze;

public interface Generator {
    Maze generate(int height, int width);
}
