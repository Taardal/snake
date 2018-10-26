package no.taardal.snake.manager;

import no.taardal.snake.camera.Camera;
import no.taardal.snake.system.*;

public class SystemManager {

    private InputSystem inputSystem;
    private DirectionSystem directionSystem;
    private MovementSystem movementSystem;
    private CollisionSystem collisionSystem;
    private SpawnSystem spawnSystem;
    private DrawingSystem drawingSystem;
    private ScoreSystem scoreSystem;

    public SystemManager(EntityManager entityManager, ComponentManager componentManager, EventManager eventManager) {
        spawnSystem = new SpawnSystem(entityManager, componentManager, eventManager);
        inputSystem = new InputSystem(eventManager);
        movementSystem = new MovementSystem(componentManager);
        directionSystem = new DirectionSystem(componentManager, eventManager);
        collisionSystem = new CollisionSystem(entityManager, componentManager, eventManager);
        drawingSystem = new DrawingSystem(entityManager, componentManager);
        scoreSystem = new ScoreSystem(eventManager);
    }

    public InputSystem getInputSystem() {
        return inputSystem;
    }

    public DirectionSystem getDirectionSystem() {
        return directionSystem;
    }

    public MovementSystem getMovementSystem() {
        return movementSystem;
    }

    public CollisionSystem getCollisionSystem() {
        return collisionSystem;
    }

    public SpawnSystem getSpawnSystem() {
        return spawnSystem;
    }

    public DrawingSystem getDrawingSystem() {
        return drawingSystem;
    }

    public ScoreSystem getScoreSystem() {
        return scoreSystem;
    }

    public void update() {
        directionSystem.update();
        movementSystem.update();
        collisionSystem.update();
    }

    public void draw(Camera camera) {
        drawingSystem.draw(camera);
    }
}
