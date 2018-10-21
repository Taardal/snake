package no.taardal.snake.system;

import no.taardal.snake.component.DirectionComponent;
import no.taardal.snake.component.IndexComponent;
import no.taardal.snake.direction.Direction;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.event.Event;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.type.EntityType;
import no.taardal.snake.type.EventType;

import java.util.List;
import java.util.stream.Collectors;

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
    public void update() {
        Direction nextBodyPartDirection = null;
        for (Entity bodyPartEntity : getBodyPartsSortedByIndex()) {
            IndexComponent indexComponent = componentManager.getIndexComponent(bodyPartEntity.getId());
            DirectionComponent directionComponent = componentManager.getDirectionComponents().get(bodyPartEntity.getId());

            if (nextBodyPartDirection != null && isValidDirectionChange(nextBodyPartDirection, directionComponent.getDirection())) {
                directionComponent.setDirection(nextBodyPartDirection);
            }
            nextBodyPartDirection = directionComponent.getDirection();
        }
    }

    @Override
    public void onEvent(Event event) {
        Direction nextDirection = getNextDirection(event);
        if (nextDirection != null) {
            Entity entity = event.getEntity() != null ? event.getEntity() : getFirstBodyPart();
            if (entity != null) {
                IndexComponent indexComponent = componentManager.getIndexComponent(entity.getId());
                DirectionComponent directionComponent = componentManager.getDirectionComponent(entity.getId());
                if (isValidDirectionChange(nextDirection, directionComponent.getDirection())) {
                    directionComponent.setDirection(nextDirection);
                }
            }
        }
    }

    private Direction getNextDirection(Event event) {
        if (event.getType() == EventType.UP_PRESSED) {
            return Direction.UP;
        }
        if (event.getType() == EventType.LEFT_PRESSED) {
            return Direction.LEFT;
        }
        if (event.getType() == EventType.RIGHT_PRESSED) {
            return Direction.RIGHT;
        }
        if (event.getType() == EventType.DOWN_PRESSED) {
            return Direction.DOWN;
        }
        return null;
    }

    private List<Entity> getBodyPartsSortedByIndex() {
        return entityManager.get(EntityType.BODY_PART).stream().sorted(this::compareBodyPartIndex).collect(Collectors.toList());
    }

    private int compareBodyPartIndex(Entity entity1, Entity entity2) {
        IndexComponent indexComponent1 = componentManager.getIndexComponent(entity1.getId());
        IndexComponent indexComponent2 = componentManager.getIndexComponent(entity2.getId());
        return indexComponent1.compareTo(indexComponent2);
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

    private Entity getFirstBodyPart() {
        return entityManager.get(EntityType.BODY_PART).stream().min(this::compareBodyPartIndex).orElse(null);
    }

}
