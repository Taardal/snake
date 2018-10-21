package no.taardal.snake.system;

import no.taardal.snake.component.DirectionComponent;
import no.taardal.snake.component.IndexComponent;
import no.taardal.snake.component.PositionComponent;
import no.taardal.snake.direction.Direction;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.event.Event;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.type.EntityType;
import no.taardal.snake.vector.Vector2i;

import java.util.List;
import java.util.stream.Collectors;

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
    public void update() {
        for (Entity bodyPartEntity : getBodyPartsSortedByIndex()) {
            DirectionComponent directionComponent = componentManager.getDirectionComponents().get(bodyPartEntity.getId());
            PositionComponent positionComponent = componentManager.getPositionComponents().get(bodyPartEntity.getId());
            Vector2i nextPosition = getNextPosition(directionComponent.getDirection(), positionComponent.getPosition());
            if (nextPosition != null) {
                positionComponent.setPosition(nextPosition);
            }
        }
    }

    @Override
    public void onEvent(Event event) {

    }

    private List<Entity> getBodyPartsSortedByIndex() {
        return entityManager.get(EntityType.BODY_PART).stream().sorted(this::compareBodyPartIndex).collect(Collectors.toList());
    }

    private int compareBodyPartIndex(Entity entity1, Entity entity2) {
        IndexComponent indexComponent1 = componentManager.getIndexComponent(entity1.getId());
        IndexComponent indexComponent2 = componentManager.getIndexComponent(entity2.getId());
        return indexComponent1.compareTo(indexComponent2);
    }

    private Vector2i getNextPosition(Direction direction, Vector2i position) {
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
