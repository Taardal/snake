package no.taardal.snake;

import no.taardal.snake.camera.Camera;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.gamecanvas.GameCanvas;
import no.taardal.snake.gameloop.GameLoop;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.manager.SystemManager;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.type.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class Game extends JFrame implements GameLoop.Listener, Observer {

    public static final int MAP_SIZE = 20;
    public static final int CELL_SIZE = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
    private static final String TITLE = "Snake";
    private static final int GAME_SIZE = CELL_SIZE * MAP_SIZE;

    private GameLoop gameLoop;
    private GameCanvas gameCanvas;
    private Camera camera;
    private SystemManager systemManager;

    private int score;

    private Game() {
        gameLoop = new GameLoop(this);
        gameCanvas = new GameCanvas(GAME_SIZE);
        camera = new Camera(GAME_SIZE);

        EventManager eventManager = new EventManager();
        systemManager = new SystemManager(new EntityManager(), new ComponentManager(), eventManager);

        eventManager.addObserver(this, EventType.APPLE_EATEN);
        eventManager.addObserver(this, EventType.GAME_ENDED);
        eventManager.sendEvent(EventType.GAME_STARTED);

        addKeyListener(systemManager.getInputSystem());
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
    public void onUpdate() {
        systemManager.update();
    }

    @Override
    public void onDraw() {
        camera.clear();
        systemManager.draw(camera);
        gameCanvas.draw(camera);
    }

    @Override
    public void onEvent(EventType eventType, Entity entity) {
        LOGGER.info("Received event " + eventType + ", " + entity);
        if (eventType == EventType.APPLE_EATEN) {
            score += 10;
        }
        if (eventType == EventType.GAME_ENDED) {
            LOGGER.info("GAME OVER! Score: " + score);
            gameLoop.setRunning(false);
        }
    }

    private synchronized void start() {
        new Thread(gameLoop, "GAME_LOOP").start();
    }

}
