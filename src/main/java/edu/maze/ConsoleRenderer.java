package edu.maze;

import java.util.ArrayList;
import java.util.List;

public class ConsoleRenderer implements Renderer {
    @Override
    public String render(Maze maze) {
        return render(maze, new ArrayList<>());
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        Cell start = null;
        Cell end = null;
        if (!path.isEmpty()) {
            start = maze.getCell(path.get(0));
            end = maze.getCell(path.get(path.size() - 1));
        }
        // Отрисовка верха лабиринта
        StringBuilder lines = new StringBuilder();
        final String boundary = "+---";
        final String newRow = "+\n|";
        for (int j = 0; j < maze.width; j++) {
            lines.append(boundary);
            if (j == maze.width - 1) {
                lines.append(newRow);
            }
        }

        // Отрисовка основной части лабиринта
        for (int i = 0; i < maze.height; i++) {
            if (i > 0) {
                for (int j = 0; j < maze.width; j++) {
                    var cell = maze.getCell(i - 1, j);
                    if (cell.getDownWall()) {
                        lines.append(boundary);
                    } else {
                        lines.append("+   ");
                    }
                }
                lines.append(newRow);
            }
            for (int j = 0; j < maze.width; j++) {
                var cell = maze.getCell(i, j);
                boolean isAtPath = path.contains(new Coordinate(cell.row, cell.col));
                if (cell == start) {
                    lines.append(" s  ");
                } else if (cell == end) {
                    lines.append(" e  ");
                } else if (isAtPath) {
                    lines.append(" *  ");
                } else {
                    lines.append("    ");
                }

                if (cell.getRightWall()) {
                    lines.deleteCharAt(lines.length() - 1);
                    lines.append("|");
                }
            }
            lines.append("\n");
        }

        // Отрисовка нижней стенки лабиринта
        for (int j = 0; j < maze.width; j++) {
            lines.append(boundary);
            if (j == maze.width - 1) {
                lines.append("+\n");
            }
        }
        return lines.toString();
    }
}
