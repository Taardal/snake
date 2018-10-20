package no.taardal.snake.manager;

import no.taardal.snake.system.InputSystem;
import no.taardal.snake.system.MovementSystem;
import no.taardal.snake.system.SpawnSystem;

public class SystemManager {

    private final InputSystem inputSystem;
    private final MovementSystem movementSystem;
    private final SpawnSystem spawnSystem;

    public SystemManager(InputSystem inputSystem, MovementSystem movementSystem, SpawnSystem spawnSystem) {
        this.inputSystem = inputSystem;
        this.movementSystem = movementSystem;
        this.spawnSystem = spawnSystem;
    }

    public MovementSystem getMovementSystem() {
        return movementSystem;
    }

    public InputSystem getInputSystem() {
        return inputSystem;
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
