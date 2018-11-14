package no.taardal.snake;

import no.taardal.snake.camera.Camera;
import no.taardal.snake.gamecanvas.GameCanvas;
import no.taardal.snake.gameloop.GameLoop;
import no.taardal.snake.keyboard.Keyboard;
import no.taardal.snake.system.GameSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class Game extends JFrame implements GameLoop.Listener, GameSystem.Listener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
    private static final String TITLE = "Snake";
    private static final int CELL_SIZE = 20;
    private static final int MAP_SIZE = 30;
    private static final int GAME_SIZE = CELL_SIZE * MAP_SIZE;

    private GameLoop gameLoop;
    private GameCanvas gameCanvas;
    private GameSystem gameSystem;
    private Keyboard keyboard;
    private Camera camera;

    private Game() {
        gameLoop = new GameLoop(this);
        gameCanvas = new GameCanvas(GAME_SIZE);
        gameSystem = new GameSystem(this, MAP_SIZE, CELL_SIZE);
        keyboard = new Keyboard();
        camera = new Camera(GAME_SIZE, CELL_SIZE);
        configureFrame();
    }

    public static void main(String[] args) {
        new Game().start();
    }

    @Override
    public void onHandleInput() {
        int pressedKey = keyboard.getPressedKey();
        if (pressedKey != Keyboard.NO_KEY_PRESSED) {
            gameSystem.onHandleInput(pressedKey);
        }
    }

    @Override
    public void onUpdate() {
        gameSystem.update();
    }

    @Override
    public void onDraw() {
        camera.clear();
        gameSystem.drawToCamera(camera);
        gameCanvas.drawToCanvas(camera);
    }

    @Override
    public void onGameOver(int score) {
        stop(score);
    }

    private void configureFrame() {
        addKeyListener(keyboard);
        add(gameCanvas);
        setTitle(TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();
    }

    private synchronized void start() {
        gameSystem.start();
        new Thread(gameLoop, "GAME_LOOP").start();
        LOGGER.info("Game started...");
    }

    private synchronized void stop(int score) {
        gameLoop.stop();
        String gameOverMessage = "Game over. Your score: " + score;
        JOptionPane.showMessageDialog(this, gameOverMessage);
        LOGGER.info("Game stopped!");
    }

}
