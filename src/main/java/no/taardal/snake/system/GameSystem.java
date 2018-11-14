package no.taardal.snake.system;

import no.taardal.snake.camera.Camera;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.type.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameSystem implements Observer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameSystem.class);

    private Listener listener;
    private EntityManager entityManager;
    private ComponentManager componentManager;
    private EventManager eventManager;
    private InputSystem inputSystem;
    private DirectionSystem directionSystem;
    private MovementSystem movementSystem;
    private CollisionSystem collisionSystem;
    private SpawnSystem spawnSystem;
    private DrawingSystem drawingSystem;
    private ScoreSystem scoreSystem;

    public GameSystem(Listener listener, int mapSize, int cellSize) {
        this(mapSize, cellSize);
        this.listener = listener;
    }

    private GameSystem(int mapSize, int cellSize) {
        createManagers();
        createSystems(mapSize, cellSize);
        addObservers();
    }

    @Override
    public void onEvent(EventType eventType, Entity entity) {
        LOGGER.info("Received event " + eventType + ", " + entity);
        if (eventType == EventType.GAME_OVER) {
            listener.onGameOver(scoreSystem.getScore());
        }
    }

    public void start() {
        eventManager.sendEvent(EventType.GAME_STARTED);
    }

    public void onHandleInput(int keyCode) {
        inputSystem.onKeyPressed(keyCode);
    }

    public void update() {
        directionSystem.update();
        movementSystem.update();
        collisionSystem.update();
    }

    public void drawToCamera(Camera camera) {
        drawingSystem.draw(camera);
    }

    private void createManagers() {
        entityManager = new EntityManager();
        componentManager = new ComponentManager();
        eventManager = new EventManager();
    }

    private void createSystems(int mapSize, int cellSize) {
        spawnSystem = new SpawnSystem(entityManager, componentManager, mapSize, cellSize);
        inputSystem = new InputSystem(eventManager);
        movementSystem = new MovementSystem(componentManager, mapSize);
        directionSystem = new DirectionSystem(componentManager);
        collisionSystem = new CollisionSystem(entityManager, componentManager, eventManager);
        drawingSystem = new DrawingSystem(entityManager, componentManager);
        scoreSystem = new ScoreSystem(eventManager);
    }

    private void addObservers() {
        eventManager.addObserver(spawnSystem, EventType.GAME_STARTED);
        eventManager.addObserver(spawnSystem, EventType.APPLE_EATEN);
        eventManager.addObserver(directionSystem, EventType.UP_PRESSED);
        eventManager.addObserver(directionSystem, EventType.LEFT_PRESSED);
        eventManager.addObserver(directionSystem, EventType.RIGHT_PRESSED);
        eventManager.addObserver(directionSystem, EventType.DOWN_PRESSED);
        eventManager.addObserver(this, EventType.GAME_OVER);
    }

    public interface Listener {

        void onGameOver(int score);

    }
}
