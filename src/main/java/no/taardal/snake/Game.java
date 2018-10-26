package no.taardal.snake;

import no.taardal.snake.camera.Camera;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.gamecanvas.GameCanvas;
import no.taardal.snake.gameloop.GameLoop;
import no.taardal.snake.type.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame implements GameLoop.Listener, GameState.Listener, Keyboard.Listener {

    public static final int MAP_SIZE = 20;
    public static final int CELL_SIZE = 5;

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
    private static final String TITLE = "Snake";
    private static final int GAME_SIZE = CELL_SIZE * MAP_SIZE;

    private GameLoop gameLoop;
    private GameCanvas gameCanvas;
    private GameState gameState;
    private Camera camera;

    private Game() {
        gameLoop = new GameLoop(this);
        gameCanvas = new GameCanvas(GAME_SIZE);
        gameState = new GameState(this);
        camera = new Camera(GAME_SIZE);
        addKeyListener(keyboard);
        add(gameCanvas);
        setTitle(TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();
    }

    public static void main(String[] args) {
        new Game().start();
    }

    @Override
    public void onKeyPressed(int keyCode) {
        gameState.onKeyPressed(keyCode);
    }

    @Override
    public void onUpdate() {
        gameState.update();
    }

    @Override
    public void onDraw() {
        camera.clear();
        gameState.drawToCamera(camera);
        gameCanvas.drawToCanvas(camera);
    }

    @Override
    public void onGameEnded(int score) {
        stop(score);
    }

    private synchronized void start() {
        new Thread(gameLoop, "GAME_LOOP").start();
        gameState.start();
    }

    private synchronized void stop(int score) {
        gameLoop.setRunning(false);
        String gameOverMessage = "Game over. Your score: " + score;
        JOptionPane.showMessageDialog(this, gameOverMessage);
    }

}
