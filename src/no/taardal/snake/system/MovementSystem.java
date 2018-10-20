package no.taardal.snake.system;

import no.taardal.snake.Log;
import no.taardal.snake.component.DirectionComponent;
import no.taardal.snake.component.IndexComponent;
import no.taardal.snake.component.PositionComponent;
import no.taardal.snake.direction.Direction;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.event.DirectionKeyPressedEvent;
import no.taardal.snake.event.Event;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.type.EntityType;
import no.taardal.snake.type.EventType;
import no.taardal.snake.vector.Vector2i;

public class MovementSystem implements System, Observer {

    private final EntityManager entityManager;
    private final ComponentManager componentManager;
    private final EventManager eventManager;

    public MovementSystem(EntityManager entityManager, ComponentManager componentManager, EventManager eventManager) {
        this.entityManager = entityManager;
        this.componentManager = componentManager;
        this.eventManager = eventManager;
    }

    @Override
    public void onEvent(Event event) {
        if (event.getType() == EventType.DIRECTION_KEY_PRESSED) {
            Direction newDirection = ((DirectionKeyPressedEvent) event).getDirection();
            Entity headBodyPartEntity = getHeadBodyPartEntity();
            if (headBodyPartEntity != null) {
                DirectionComponent directionComponent = componentManager.getDirectionComponent(headBodyPartEntity.getId());
                if (isValidDirectionChange(newDirection, directionComponent.getDirection())) {
                    directionComponent.setDirection(newDirection);
                }
            }
        }
    }

    @Override
    public void update() {
        Direction nextBodyPartDirection = null;
        for (Entity bodyPartEntity : entityManager.get(EntityType.BODY_PART)) {

            DirectionComponent directionComponent = componentManager.getDirectionComponents().get(bodyPartEntity.getId());
            PositionComponent positionComponent = componentManager.getPositionComponents().get(bodyPartEntity.getId());
            IndexComponent indexComponent = componentManager.getIndexComponents().get(bodyPartEntity.getId());

            Direction direction = directionComponent.getDirection();
            Vector2i newPosition = getNewPosition(direction, positionComponent.getPosition());
            positionComponent.setPosition(newPosition);

            if (nextBodyPartDirection != null && isValidDirectionChange(nextBodyPartDirection, direction)) {
                directionComponent.setDirection(nextBodyPartDirection);
            }
            nextBodyPartDirection = direction;

            Log.log("Body part " + indexComponent.getIndex() + ": " + positionComponent.getX() + " X, " + positionComponent.getY() + " Y");
        }
        Log.logBreak();
    }

    private Entity getHeadBodyPartEntity() {
        return entityManager.get(EntityType.BODY_PART).stream()
                .filter(entity -> componentManager.getIndexComponents().get(entity.getId()).isHead())
                .findFirst()
                .orElse(null);
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

    private Vector2i getNewPosition(Direction direction, Vector2i position) {
        if (direction == Direction.UP) {
            return position.withY(position.getY() - 1);
        }
        if (direction == Direction.DOWN) {
            return position.withY(position.getY() + 1);
        }
        if (direction == Direction.LEFT) {
            return position.withX(position.getX() - 1);
        }
        if (direction == Direction.RIGHT) {
            return position.withX(position.getX() + 1);
        }
        return null;
    }
}
