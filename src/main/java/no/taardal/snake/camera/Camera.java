package no.taardal.snake.camera;

import no.taardal.snake.shape.Circle;
import no.taardal.snake.vector.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Camera {

    private final BufferedImage bufferedImage;
    private final Graphics2D graphics2D;
    private final int cellSize;

    public Camera(int gameSize, int cellSize) {
        bufferedImage = new BufferedImage(gameSize, gameSize, BufferedImage.TYPE_INT_RGB);
        this.cellSize = cellSize;
        graphics2D = bufferedImage.createGraphics();
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void clear() {
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    public void draw(Circle circle, Color color, Vector2i position) {
        int x = position.getX() * cellSize;
        int y = position.getY() * cellSize;
        graphics2D.setColor(color);
        graphics2D.fillOval(x, y, circle.getSize(), circle.getSize());
    }
}
