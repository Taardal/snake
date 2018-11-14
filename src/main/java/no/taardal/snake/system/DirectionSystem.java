package no.taardal.snake.system;

import no.taardal.snake.component.DirectionComponent;
import no.taardal.snake.component.RouteComponent;
import no.taardal.snake.direction.Direction;
import no.taardal.snake.direction.DirectionChange;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.type.EventType;
import no.taardal.snake.vector.Vector2i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DirectionSystem implements Observer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectionSystem.class);

    private final ComponentManager componentManager;

    DirectionSystem(ComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    @Override
    public void onEvent(EventType eventType, Entity entity) {
        LOGGER.info("Received event " + eventType + ", " + entity);
        Direction nextDirection = getNextDirection(eventType);
        if (nextDirection != null) {
            List<Entity> bodyParts = getBodyParts();
            Direction currentDirection = getDirection(bodyParts.get(0));
            if (isValidDirectionChange(nextDirection, currentDirection)) {
                addDirectionChange(nextDirection, bodyParts);
            }
        }
    }

    void update() {
        componentManager.getBodyComponent().getBodyParts().forEach(entity -> {
            DirectionComponent directionComponent = componentManager.getDirectionComponent(entity.getId());
            RouteComponent routeComponent = componentManager.getRouteComponent(entity.getId());
            routeComponent.getDirectionChanges().stream()
                    .filter(directionChange -> directionChange.getPosition().equals(getPosition(entity)))
                    .forEach(directionChange -> {
                        directionComponent.setDirection(directionChange.getDirection());
                        routeComponent.removeFirst();
                    });
        });
    }

    private Direction getNextDirection(EventType eventType) {
        if (eventType == EventType.UP_PRESSED) {
            return Direction.UP;
        }
        if (eventType == EventType.LEFT_PRESSED) {
            return Direction.LEFT;
        }
        if (eventType == EventType.RIGHT_PRESSED) {
            return Direction.RIGHT;
        }
        if (eventType == EventType.DOWN_PRESSED) {
            return Direction.DOWN;
        }
        return null;
    }

    private List<Entity> getBodyParts() {
        return componentManager.getBodyComponent().getBodyParts();
    }

    private Direction getDirection(Entity entity) {
        return componentManager.getDirectionComponent(entity.getId()).getDirection();
    }

    private void addDirectionChange(Direction nextDirection, List<Entity> bodyParts) {
        Vector2i position = getPosition(bodyParts.get(0));
        bodyParts.forEach(bodyPart -> addDirectionChange(bodyPart, new DirectionChange(nextDirection, position.copy())));
    }

    private Vector2i getPosition(Entity entity) {
        return componentManager.getPositionComponent(entity.getId()).getPosition();
    }

    private void addDirectionChange(Entity bodyPart, DirectionChange directionChange) {
        componentManager.getRouteComponent(bodyPart.getId()).addDirectionChange(directionChange);
    }

    private boolean isValidDirectionChange(Direction newDirection, Direction currentDirection) {
        return isHorizontal(currentDirection) && isVertical(newDirection) || isVertical(currentDirection) && isHorizontal(newDirection);
    }

    private boolean isHorizontal(Direction direction) {
        return direction == Direction.LEFT || direction == Direction.RIGHT;
    }

    private boolean isVertical(Direction direction) {
        return direction == Direction.UP || direction == Direction.DOWN;
    }
}
