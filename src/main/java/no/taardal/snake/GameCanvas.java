package no.taardal.snake;

import no.taardal.snake.camera.Camera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;

public class GameCanvas extends Canvas {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameCanvas.class);
    private static final int SCALE = 3;
    private static final int NUMBER_OF_BUFFERS = 3;

    public GameCanvas(int gameSize) {
        setPreferredSize(new Dimension(gameSize * SCALE, gameSize * SCALE));
    }

    public void draw(Camera camera) {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(NUMBER_OF_BUFFERS);
        } else {
            drawToBuffer(camera, bufferStrategy);
            bufferStrategy.show();
        }
    }

    private void drawToBuffer(Camera camera, BufferStrategy bufferStrategy) {
        int x = 0;
        int y = 0;
        ImageObserver observer = null;
        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(camera.getBufferedImage(), x, y, getWidth(), getHeight(), observer);
        graphics.dispose();
    }

}
