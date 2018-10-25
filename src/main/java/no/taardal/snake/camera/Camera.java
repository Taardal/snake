package no.taardal.snake.camera;

import no.taardal.snake.Game;
import no.taardal.snake.shape.Circle;
import no.taardal.snake.vector.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Camera {

    private BufferedImage bufferedImage;
    private Graphics2D graphics2D;

    public Camera(int gameSize) {
        bufferedImage = new BufferedImage(gameSize, gameSize, BufferedImage.TYPE_INT_RGB);
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
        int x = position.getX() * Game.CELL_SIZE;
        int y = position.getY() * Game.CELL_SIZE;
        graphics2D.setColor(color);
        graphics2D.fillOval(x, y, circle.getSize(), circle.getSize());
    }
}
