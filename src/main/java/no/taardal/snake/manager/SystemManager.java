package no.taardal.snake.manager;

import no.taardal.snake.camera.Camera;
import no.taardal.snake.system.*;

public class SystemManager {

    private final InputSystem inputSystem;
    private final DirectionSystem directionSystem;
    private final MovementSystem movementSystem;
    private final CollisionSystem collisionSystem;
    private final SpawnSystem spawnSystem;
    private final DrawingSystem drawingSystem;

    public SystemManager(EntityManager entityManager, ComponentManager componentManager, EventManager eventManager) {
        spawnSystem = new SpawnSystem(entityManager, componentManager, eventManager);
        inputSystem = new InputSystem(eventManager);
        movementSystem = new MovementSystem(componentManager);
        directionSystem = new DirectionSystem(componentManager, eventManager);
        collisionSystem = new CollisionSystem(entityManager, componentManager, eventManager);
        drawingSystem = new DrawingSystem(entityManager, componentManager, eventManager);
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

    public void update() {
        directionSystem.update();
        movementSystem.update();
        collisionSystem.update();
    }

    public void draw(Camera camera) {
        drawingSystem.draw(camera);
    }
}
