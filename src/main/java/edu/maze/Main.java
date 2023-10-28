package edu.maze;

import edu.maze.generators.EllerGenerator;
import edu.maze.renderers.ASCIIRenderer;
import edu.maze.renderers.Renderer;
import edu.maze.solvers.BFSSolver;
import edu.maze.solvers.DFSSolver;
import edu.maze.solvers.Solver;
import java.util.Locale;
import java.util.Scanner;

public final class Main {

    @SuppressWarnings("RegexpSinglelineJava")
    public static void main(String[] args) {
        System.out.println("""
            Вас приветствует программа по генерации и прохождению лабиринтов!
            Для генерации пока что доступен только алгоритм Эллера
            Введите в трех строчках высоту, ширину лабиринта, а так же вероятность генерации стены\s
            -- внутренний параметр алгоритма Эллера""");
        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);
        int height;
        int width;
        double probability;
        boolean firstTry = true;
        do {
            if (!firstTry) {
                System.out.println("Высота и ширина должны быть натуральными числа, а вероятность "
                    + "-- вещественным от 0 до 1, введите заново!");
            }
            height = sc.nextInt();
            width = sc.nextInt();
            probability = sc.nextDouble();
            firstTry = false;
        } while (height <= 0 || width <= 0 || probability > 1.0 || probability < 0.0);
        EllerGenerator gen = new EllerGenerator(probability);
        Maze maze = gen.generate(height, width);
        System.out.println("Ваш лабиринт сгенерирован! Выберите любой пункт меню");
        final String menu = """
            1. Вывести лабиринт:
            2. Нарисовать путь от точки (x1, y1) до точки (x2, y2):
            3. Сменить solver на DFSSolver
            4. Сменить solver на BFSSolver
            5. Выйти""";
        Solver solver = new BFSSolver();
        final Renderer renderer = new ASCIIRenderer();
        final int THREE = 3;
        final int FOUR = 4;
        boolean flag = true;
        do {
            System.out.println(menu);
            int input = sc.nextInt();
            switch (input) {
                case 1 -> System.out.println(renderer.render(maze));
                case 2 -> {
                    var c1 = new Coordinate(sc.nextInt(), sc.nextInt());
                    var c2 = new Coordinate(sc.nextInt(), sc.nextInt());
                    System.out.println(renderer.render(maze, solver.solve(maze, c1, c2)));
                }
                case THREE -> {
                    solver = new DFSSolver();
                }
                case FOUR -> {
                    solver = new BFSSolver();
                }
                default -> {
                    flag = false;
                }
            }
        } while (flag);

    }

    private Main() {
    }
}
