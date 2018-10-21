package no.taardal.snake.system;

import no.taardal.snake.Log;
import no.taardal.snake.component.DirectionComponent;
import no.taardal.snake.component.PositionComponent;
import no.taardal.snake.component.RouteComponent;
import no.taardal.snake.direction.Direction;
import no.taardal.snake.direction.DirectionChange;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.type.EventType;
import no.taardal.snake.vector.Vector2i;

import java.util.List;

public class DirectionSystem implements System, Observer {

    private final EntityManager entityManager;
    private final ComponentManager componentManager;
    private final EventManager eventManager;

    public DirectionSystem(EntityManager entityManager, ComponentManager componentManager, EventManager eventManager) {
        this.entityManager = entityManager;
        this.componentManager = componentManager;
        this.eventManager = eventManager;
    }

    @Override
    public void onEvent(EventType eventType, Entity entity) {
        Log.log("Received event " + eventType + ", " + entity);
        Direction nextDirection = getNextDirection(eventType);
        if (nextDirection != null) {
            List<Entity> bodyParts = componentManager.getBodyComponent().getBodyParts();
            Vector2i headPosition = componentManager.getPositionComponent(bodyParts.get(0).getId()).getPosition();
            Vector2i directionChangePosition = new Vector2i(headPosition.getX(), headPosition.getY());
            bodyParts.forEach(bodyPart -> {
                DirectionChange directionChange = new DirectionChange(nextDirection, directionChangePosition);
                componentManager.getRouteComponent(bodyPart.getId()).addDirectionChange(directionChange);
            });
        }
    }

    @Override
    public void update() {
        componentManager.getBodyComponent().getBodyParts().forEach(entity -> {
            DirectionComponent directionComponent = componentManager.getDirectionComponent(entity.getId());
            PositionComponent positionComponent = componentManager.getPositionComponent(entity.getId());
            RouteComponent routeComponent = componentManager.getRouteComponent(entity.getId());
            routeComponent.getDirectionChanges().stream()
                    .filter(directionChange -> shouldChangeDirection(directionChange, directionComponent, positionComponent))
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

    private boolean shouldChangeDirection(DirectionChange directionChange, DirectionComponent directionComponent, PositionComponent positionComponent) {
        boolean positionsEquals = directionChange.getPosition().equals(positionComponent.getPosition());
        boolean validDirectionChange = isValidDirectionChange(directionChange.getDirection(), directionComponent.getDirection());
        return positionsEquals && validDirectionChange;
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
