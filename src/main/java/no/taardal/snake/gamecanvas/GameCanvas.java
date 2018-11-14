package no.taardal.snake.gamecanvas;

import no.taardal.snake.camera.Camera;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;

public class GameCanvas extends Canvas {

    private static final int SCALE = 1;
    private static final int NUMBER_OF_BUFFERS = 3;

    public GameCanvas(int gameSize) {
        setPreferredSize(new Dimension(gameSize * SCALE, gameSize * SCALE));
    }

    public void drawToCanvas(Camera camera) {
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
