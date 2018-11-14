package no.taardal.snake.system;

import no.taardal.snake.direction.Direction;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.vector.Vector2i;

public class MovementSystem {

    private final ComponentManager componentManager;
    private final int mapSize;

    public MovementSystem(ComponentManager componentManager, int mapSize) {
        this.componentManager = componentManager;
        this.mapSize = mapSize;
    }

    public void update() {
        componentManager.getBodyComponent().getBodyParts().forEach(this::setNextPosition);
    }

    private void setNextPosition(Entity entity) {
        Direction direction = getDirection(entity);
        Vector2i position = getPosition(entity);
        setNextX(direction, position);
        setNextY(direction, position);
    }

    private Direction getDirection(Entity entity) {
        return componentManager.getDirectionComponents().get(entity.getId()).getDirection();
    }

    private Vector2i getPosition(Entity entity) {
        return componentManager.getPositionComponents().get(entity.getId()).getPosition();
    }

    private void setNextX(Direction direction, Vector2i position) {
        if (direction == Direction.LEFT) {
            int x = position.getX() - 1;
            if (x < 0) {
                x = mapSize - 1;
            }
            position.setX(x);
        }
        if (direction == Direction.RIGHT) {
            int x = position.getX() + 1;
            if (x >= mapSize) {
                x = 0;
            }
            position.setX(x);
        }
    }

    private void setNextY(Direction direction, Vector2i position) {
        if (direction == Direction.UP) {
            int y = position.getY() - 1;
            if (y < 0) {
                y = mapSize - 1;
            }
            position.setY(y);
        }
        if (direction == Direction.DOWN) {
            int y = position.getY() + 1;
            if (y >= mapSize) {
                y = 0;
            }
            position.setY(y);
        }
    }

}
