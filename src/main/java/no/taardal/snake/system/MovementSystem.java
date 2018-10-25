package no.taardal.snake.system;

import no.taardal.snake.Game;
import no.taardal.snake.component.DirectionComponent;
import no.taardal.snake.component.PositionComponent;
import no.taardal.snake.direction.Direction;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.vector.Vector2i;

public class MovementSystem {

    private static final long MILLIS_BETWEEN_MOVEMENT = 100;

    private final ComponentManager componentManager;

    private long lastTimeMillis;

    public MovementSystem(ComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    public void update() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastTimeMillis >= MILLIS_BETWEEN_MOVEMENT) {
            lastTimeMillis = currentTimeMillis;
            moveBodyParts();
        }
    }

    private void moveBodyParts() {
        componentManager.getBodyComponent().getBodyParts().forEach(entity -> {
            DirectionComponent directionComponent = componentManager.getDirectionComponents().get(entity.getId());
            PositionComponent positionComponent = componentManager.getPositionComponents().get(entity.getId());
            setNextPosition(directionComponent.getDirection(), positionComponent.getPosition());
        });
    }

    private void setNextPosition(Direction direction, Vector2i position) {
        setNextX(direction, position);
        setNextY(direction, position);
    }

    private void setNextX(Direction direction, Vector2i position) {
        if (direction == Direction.LEFT) {
            int x = position.getX() - 1;
            if (x < 0) {
                x = Game.MAP_SIZE - 1;
            }
            position.setX(x);
        }
        if (direction == Direction.RIGHT) {
            int x = position.getX() + 1;
            if (x >= Game.MAP_SIZE) {
                x = 0;
            }
            position.setX(x);
        }
    }

    private void setNextY(Direction direction, Vector2i position) {
        if (direction == Direction.UP) {
            int y = position.getY() - 1;
            if (y < 0) {
                y = Game.MAP_SIZE - 1;
            }
            position.setY(y);
        }
        if (direction == Direction.DOWN) {
            int y = position.getY() + 1;
            if (y >= Game.MAP_SIZE) {
                y = 0;
            }
            position.setY(y);
        }
    }

}
