package edu.maze;

import java.util.HashMap;
import java.util.random.RandomGenerator;

public class EllerGenerator implements Generator {

    final double probabilityOfWallCreation;

    public EllerGenerator(double probability) throws IllegalArgumentException {
        if (probability > 1 || probability < 0) {
            throw new IllegalArgumentException("Вероятность создания стенки должна лежать в диапазоне [0,1]!");
        }
        this.probabilityOfWallCreation = probability;
    }

    @Override
    public Maze generate(int height, int width) throws IllegalArgumentException {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Ширина и высота лабиринта должны быть положительными!");
        }
        RandomBoolGenerator generator = new RandomBoolGenerator(this.probabilityOfWallCreation);

        var cells = new Cell[height][width];
        var values = new int[width];
        var sizeOfSet = new HashMap<Integer, Integer>();
        int max = 0;

        for (int i = 0; i < height; i++) {

            // Инициализация номеров множеств на новой строчке
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell(i, j, j == width - 1, i == height - 1);
                if (i == 0) {
                    values[j] = j;
                    sizeOfSet.put(j, 1);
                } else if (cells[i - 1][j].getDownWall()) {
                    sizeOfSet.replace(values[j], sizeOfSet.get(values[j]) - 1);
                    values[j] = max + 1;
                    max++;
                    sizeOfSet.put(values[j], 1);
                }
            }

            // Создание правых стенок
            max = values[0];
            for (int j = 0; j < width - 1; j++) {
                var hasWall = generator.randomBool();
                if (hasWall || values[j + 1] == values[j]) {
                    cells[i][j].setRightWall(true);
                } else {
                    uniteSets(width, values, sizeOfSet, j);
                }
                max = Math.max(max, values[j + 1]);
            }

            // Случай последней строки
            if (i == height - 1) {
                for (int j = 0; j < width - 1; j++) {
                    if (values[j] != values[j + 1]) {
                        cells[i][j].setRightWall(false);
                        uniteSets(width, values, sizeOfSet, j);
                    }
                }
                break;
            }

            // Создание нижних стенок
            createDownWalls(width, generator, cells, values, sizeOfSet, i);
        }
        return new Maze(height, width, cells);
    }

    private static void createDownWalls(
        int width, RandomBoolGenerator generator, Cell[][] cells,
        int[] values, HashMap<Integer, Integer> sizeOfSet, int i
    ) {
        int cntDownWallsInSet = 0;
        for (int j = 0; j < width; j++) {
            if (j > 0 && values[j] != values[j - 1]) {
                cntDownWallsInSet = 0;
            }
            var hasWall = generator.randomBool();
            if (hasWall && sizeOfSet.get(values[j]) - cntDownWallsInSet >= 2) {
                cells[i][j].setDownWall(true);
                cntDownWallsInSet++;
            }
        }
    }

    private void uniteSets(int width, int[] values, HashMap<Integer, Integer> sizeOfSet, int j) {
        var oldValue = values[j + 1];
        int cnt = 0;
        for (int k = 0; k < width; k++) {
            if (values[k] == oldValue) {
                cnt++;
                values[k] = values[j];
            }
        }
        sizeOfSet.replace(values[j], sizeOfSet.get(values[j]) + cnt);
        sizeOfSet.replace(oldValue, sizeOfSet.get(oldValue) - cnt);
    }

    private record RandomBoolGenerator(double probability) {
        final private static RandomGenerator RANDOM_BOOL = RandomGenerator.getDefault();

        boolean randomBool() {
            return RANDOM_BOOL.nextDouble() < probability;
        }

    }
}
