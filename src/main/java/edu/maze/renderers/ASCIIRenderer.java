package edu.maze.renderers;

import edu.maze.Cell;
import edu.maze.Coordinate;
import edu.maze.Maze;
import java.util.ArrayList;
import java.util.List;

public class ASCIIRenderer implements Renderer {
    static final String BOUNDARY = "+---";
    static final String NEW_ROW = "+\n";
    static final String START_CELL = " s  ";
    static final String END_CELL = " e  ";
    static final String PATH_CELL = " *  ";
    static final String EMPTY_CELL = "    ";
    static final String PASSAGE = "+   ";

    @Override
    public String render(Maze maze) {
        return render(maze, new ArrayList<>());
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder lines = new StringBuilder();
        Cell start = null;
        Cell end = null;
        if (!path.isEmpty()) {
            start = maze.getCell(path.get(0));
            end = maze.getCell(path.get(path.size() - 1));
        }
        // Отрисовка верха лабиринта
        renderTopOrBottom(maze, lines, true);

        // Отрисовка основной части лабиринта
        renderMainPart(maze, path, lines, start, end);

        // Отрисовка нижней стенки лабиринта
        renderTopOrBottom(maze, lines, false);

        return lines.toString();
    }

    private static void renderTopOrBottom(Maze maze, StringBuilder lines, boolean isRenderingTop) {
        for (int j = 0; j < maze.width; j++) {
            lines.append(BOUNDARY);
            if (j == maze.width - 1) {
                lines.append(NEW_ROW);
                if (isRenderingTop) {
                    lines.append("|");
                }
            }
        }
    }

    private static void renderMainPart(Maze maze, List<Coordinate> path, StringBuilder lines, Cell start, Cell end) {
        for (int i = 0; i < maze.height; i++) {
            if (i > 0) {
                for (int j = 0; j < maze.width; j++) {
                    var cell = maze.getCell(i - 1, j);
                    if (cell.getDownWall()) {
                        lines.append(BOUNDARY);
                    } else {
                        lines.append(PASSAGE);
                    }
                }
                lines.append(NEW_ROW);
                lines.append("|");
            }
            renderInnerCells(maze, path, lines, start, end, i);
        }
    }

    private static void renderInnerCells(
        Maze maze, List<Coordinate> path, StringBuilder lines,
        Cell start, Cell end, int i
    ) {
        for (int j = 0; j < maze.width; j++) {
            var cell = maze.getCell(i, j);
            boolean isAtPath = path.contains(new Coordinate(cell.row, cell.col));
            lines.append(innerFill(cell, start, end, isAtPath));
            if (cell.getRightWall()) {
                lines.setCharAt(lines.length() - 1, '|');
            }
        }
        lines.append("\n");
    }

    private static String innerFill(Cell cell, Cell start, Cell end, boolean isAtPath) {
        if (cell == start) {
            return START_CELL;
        } else if (cell == end) {
            return END_CELL;
        } else if (isAtPath) {
            return PATH_CELL;
        }
        return EMPTY_CELL;
    }
}
