package no.taardal.snake.manager;

import no.taardal.snake.system.*;

public class SystemManager {

    private final InputSystem inputSystem;
    private final DirectionSystem directionSystem;
    private final MovementSystem movementSystem;
    private final CollisionSystem collisionSystem;
    private final SpawnSystem spawnSystem;
    private final DrawingSystem drawingSystem;

    public SystemManager(InputSystem inputSystem, DirectionSystem directionSystem, MovementSystem movementSystem, CollisionSystem collisionSystem, SpawnSystem spawnSystem, DrawingSystem drawingSystem) {
        this.inputSystem = inputSystem;
        this.directionSystem = directionSystem;
        this.movementSystem = movementSystem;
        this.collisionSystem = collisionSystem;
        this.spawnSystem = spawnSystem;
        this.drawingSystem = drawingSystem;
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

}
