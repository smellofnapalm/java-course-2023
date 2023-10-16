package edu.hw2.task2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RectangleTest {
    static Arguments[] rectangles() {
        return new Arguments[] {
            Arguments.of(new Rectangle()),
            Arguments.of(new Square())
        };
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    void rectangleArea(Rectangle rect) {
        var reassignedRect = rect;
        reassignedRect = reassignedRect.setWidth(20);
        reassignedRect = reassignedRect.setHeight(10);

        assertThat(reassignedRect.area()).isEqualTo(200.0);
    }
}
