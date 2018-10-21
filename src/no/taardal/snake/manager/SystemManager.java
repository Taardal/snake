package no.taardal.snake.manager;

import no.taardal.snake.system.*;

public class SystemManager {

    private final InputSystem inputSystem;
    private final DirectionSystem directionSystem;
    private final MovementSystem movementSystem;
    private final CollisionSystem collisionSystem;
    private final ScoreSystem scoreSystem;
    private final SpawnSystem spawnSystem;

    public SystemManager(InputSystem inputSystem, DirectionSystem directionSystem, MovementSystem movementSystem, CollisionSystem collisionSystem, ScoreSystem scoreSystem, SpawnSystem spawnSystem) {
        this.inputSystem = inputSystem;
        this.directionSystem = directionSystem;
        this.movementSystem = movementSystem;
        this.collisionSystem = collisionSystem;
        this.scoreSystem = scoreSystem;
        this.spawnSystem = spawnSystem;
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

    public ScoreSystem getScoreSystem() {
        return scoreSystem;
    }

    public SpawnSystem getSpawnSystem() {
        return spawnSystem;
    }

    @Override
    public String toString() {
        return "SystemManager{" +
                "inputSystem=" + inputSystem +
                ", movementSystem=" + movementSystem +
                '}';
    }
}
