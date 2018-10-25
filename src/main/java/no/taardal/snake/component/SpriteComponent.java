package no.taardal.snake.component;

import no.taardal.snake.shape.Circle;

import java.awt.*;

public class SpriteComponent {

    private final Circle circle;
    private final Color color;

    public SpriteComponent(Circle circle, Color color) {
        this.circle = circle;
        this.color = color;
    }

    public Circle getCircle() {
        return circle;
    }

    public Color getColor() {
        return color;
    }
}
