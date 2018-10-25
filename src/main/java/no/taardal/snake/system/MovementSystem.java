package no.taardal.snake.system;

import no.taardal.snake.Game;
import no.taardal.snake.component.DirectionComponent;
import no.taardal.snake.component.PositionComponent;
import no.taardal.snake.direction.Direction;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.vector.Vector2i;

public class MovementSystem {

    private final EntityManager entityManager;
    private final ComponentManager componentManager;
    private final EventManager eventManager;

    public MovementSystem(EntityManager entityManager, ComponentManager componentManager, EventManager eventManager) {
        this.entityManager = entityManager;
        this.componentManager = componentManager;
        this.eventManager = eventManager;
    }

    public void update() {
        componentManager.getBodyComponent().getBodyParts().forEach(bodyPartEntity -> {
            DirectionComponent directionComponent = componentManager.getDirectionComponents().get(bodyPartEntity.getId());
            PositionComponent positionComponent = componentManager.getPositionComponents().get(bodyPartEntity.getId());
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
                x = x + Game.MAP_SIZE;
            }
            position.setX(x);
        }
        if (direction == Direction.RIGHT) {
            int x = position.getX() + 1;
            if (x > Game.MAP_SIZE) {
                x = x - Game.MAP_SIZE;
            }
            position.setX(x);
        }
    }

    private void setNextY(Direction direction, Vector2i position) {
        if (direction == Direction.UP) {
            int y = position.getY() - 1;
            if (y < 0) {
                y = y + Game.MAP_SIZE;
            }
            position.setY(y);
        }
        if (direction == Direction.DOWN) {
            int y = position.getY() + 1;
            if (y > Game.MAP_SIZE) {
                y = y - Game.MAP_SIZE;
            }
            position.setY(y);
        }
    }

}
