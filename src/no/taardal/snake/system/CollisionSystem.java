package no.taardal.snake.system;

import no.taardal.snake.entity.Entity;
import no.taardal.snake.event.Event;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.type.EntityType;
import no.taardal.snake.type.EventType;
import no.taardal.snake.vector.Vector2i;

import java.util.List;

public class CollisionSystem implements System {

    private final EntityManager entityManager;
    private final ComponentManager componentManager;
    private final EventManager eventManager;

    public CollisionSystem(EntityManager entityManager, ComponentManager componentManager, EventManager eventManager) {
        this.entityManager = entityManager;
        this.componentManager = componentManager;
        this.eventManager = eventManager;
    }

    @Override
    public void update() {
        List<Entity> bodyParts = componentManager.getBodyComponent().getBodyParts();
        Vector2i headPosition = getPosition(bodyParts.get(0));
        sendAppleCollisionEvents(headPosition);
        sendBodyCollisionEvents(headPosition, bodyParts);
    }

    private void sendBodyCollisionEvents(Vector2i headPosition, List<Entity> bodyParts) {
        bodyParts.stream()
                .filter(entity -> headPosition.equals(getPosition(entity)))
                .findFirst()
                .ifPresent(entity -> eventManager.sendEvent(getEvent(EventType.COLLIDED_WITH_BODY, null)));
    }

    private void sendAppleCollisionEvents(Vector2i headPosition) {
        entityManager.get(EntityType.APPLE).stream()
                .filter(entity -> headPosition.equals(getPosition(entity)))
                .findFirst()
                .ifPresent(entity -> {
                    eventManager.sendEvent(getEvent(EventType.APPLE_EATEN, entity));
                    entityManager.getEntities().remove(entity.getId());
                    componentManager.getPositionComponents().remove(entity.getId());
                });
    }

    private Vector2i getPosition(Entity entity) {
        return componentManager.getPositionComponent(entity.getId()).getPosition();
    }

    private Event getEvent(EventType eventType, Entity entity) {
        return new Event() {

            @Override
            public EventType getType() {
                return eventType;
            }

            @Override
            public Entity getEntity() {
                return entity;
            }
        };
    }

}
