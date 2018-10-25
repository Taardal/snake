package no.taardal.snake;

import no.taardal.snake.camera.Camera;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.gameloop.GameLoop;
import no.taardal.snake.keyboard.Keyboard;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.manager.SystemManager;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.system.*;
import no.taardal.snake.type.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class Game extends JFrame implements Observer, GameLoop.Listener {

    public static final int MAP_SIZE = 20;
    public static final int CELL_SIZE = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
    private static final String TITLE = "Snake";
    private static final int GAME_SIZE = CELL_SIZE * MAP_SIZE;
    private static final long MILLIS_BETWEEN_MOVEMENT = 500;

    private GameLoop gameLoop;
    private GameCanvas gameCanvas;
    private Camera camera;
    private Keyboard keyboard;

    private EventManager eventManager;
    private EntityManager entityManager;
    private ComponentManager componentManager;
    private SystemManager systemManager;

    private int score;
    private long lastTimeMillis;

    private Game() {
        gameLoop = new GameLoop(this);
        gameCanvas = new GameCanvas(GAME_SIZE);
        camera = new Camera(GAME_SIZE);
        keyboard = new Keyboard();

        eventManager = new EventManager();
        entityManager = new EntityManager();
        componentManager = new ComponentManager();

        InputSystem inputSystem = new InputSystem(entityManager, componentManager, eventManager, keyboard);
        DirectionSystem directionSystem = new DirectionSystem(entityManager, componentManager, eventManager);
        MovementSystem movementSystem = new MovementSystem(entityManager, componentManager, eventManager);
        CollisionSystem collisionSystem = new CollisionSystem(entityManager, componentManager, eventManager);
        SpawnSystem spawnSystem = new SpawnSystem(entityManager, componentManager, eventManager);
        DrawingSystem drawingSystem = new DrawingSystem(entityManager, componentManager, eventManager);
        systemManager = new SystemManager(inputSystem, directionSystem, movementSystem, collisionSystem, spawnSystem, drawingSystem);

        eventManager.addObserver(systemManager.getDirectionSystem(), EventType.UP_PRESSED);
        eventManager.addObserver(systemManager.getDirectionSystem(), EventType.LEFT_PRESSED);
        eventManager.addObserver(systemManager.getDirectionSystem(), EventType.RIGHT_PRESSED);
        eventManager.addObserver(systemManager.getDirectionSystem(), EventType.DOWN_PRESSED);
        eventManager.addObserver(systemManager.getSpawnSystem(), EventType.GAME_STARTED);
        eventManager.addObserver(systemManager.getSpawnSystem(), EventType.APPLE_EATEN);
        eventManager.addObserver(this, EventType.APPLE_EATEN);
        eventManager.addObserver(this, EventType.GAME_ENDED);
        eventManager.sendEvent(EventType.GAME_STARTED);

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
        systemManager.getSpawnSystem().update();
        systemManager.getInputSystem().update();
        systemManager.getDirectionSystem().update();
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastTimeMillis >= MILLIS_BETWEEN_MOVEMENT) {
            lastTimeMillis = currentTimeMillis;
            systemManager.getMovementSystem().update();
            systemManager.getCollisionSystem().update();
        }
    }

    @Override
    public void onDraw() {
        camera.clear();
        systemManager.getDrawingSystem().draw(camera);
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
        }
    }

    private synchronized void start() {
        new Thread(gameLoop, "GAME_LOOP").start();
    }

}
