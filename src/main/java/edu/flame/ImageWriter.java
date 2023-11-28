package edu.flame;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public class ImageWriter {
    final Path path;
    final String format;
    Cell[][] res;
    final int window;
    final double gamma;
    int maxCnt = 0;

    ImageWriter(Path path, String format, Cell[][] image, int window, double gamma) {
        this.path = path;
        this.format = format;
        this.res = image;
        this.window = window;
        this.gamma = gamma;
    }

    private Cell[][] averagePool() {
        int height = res.length;
        int width = res[0].length;
        var averagedMap = new Cell[height / window][width / window];
        int half = window / 2;
        int total = window * window;
        for (int i = half; i < height - half; i += window) {
            for (int j = half; j < width - half; j += window) {
                calcAverage(averagedMap, half, total, i, j);
            }
        }
        return averagedMap;
    }

    void writeImage() {
        res = averagePool();
        gammaCorrection();

        BufferedImage bufferedImage = new BufferedImage(res.length, res[0].length,
            BufferedImage.TYPE_INT_RGB
        );
        for (int x = 0; x < res.length; x++) {
            for (int y = 0; y < res[0].length; y++) {
                bufferedImage.setRGB(x, y, res[x][y].color.getRGB());
            }
        }
        try {
            File outputfile = new File(path.toUri());
            ImageIO.write(bufferedImage, format, outputfile);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить изображение!", e);
        }
    }

    private void gammaCorrection() {
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                int r = res[i][j].color.getRed();
                int g = res[i][j].color.getGreen();
                int b = res[i][j].color.getBlue();
                double coef = Math.pow(Math.log(res[i][j].count) / Math.log(maxCnt), 1.0 / gamma);
                r *= coef;
                g *= coef;
                b *= coef;
                res[i][j].color = new Color(r, g, b);
            }
        }
    }

    private void calcAverage(Cell[][] averagedMap, int half, int total, int i, int j) {
        int cnt = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i1 = i - half; i1 <= i + half; i1++) {
            for (int j1 = j - half; j1 <= j + half; j1++) {
                cnt += res[i1][j1].count;
                r += res[i1][j1].color.getRed();
                g += res[i1][j1].color.getGreen();
                b += res[i1][j1].color.getBlue();
            }
        }
        averagedMap[(i - half) / window][(j - half) / window] =
            new Cell(new Color(r / total, g / total, b / total));
        averagedMap[(i - half) / window][(j - half) / window].count = Math.ceilDiv(cnt, total);
        maxCnt = Math.max(maxCnt, Math.ceilDiv(cnt, total));
    }
}
