package no.taardal.snake.system;

import no.taardal.snake.entity.Entity;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.type.EntityType;
import no.taardal.snake.type.EventType;
import no.taardal.snake.vector.Vector2i;

import java.util.List;

public class CollisionSystem {

    private final EntityManager entityManager;
    private final ComponentManager componentManager;
    private final EventManager eventManager;

    public CollisionSystem(EntityManager entityManager, ComponentManager componentManager, EventManager eventManager) {
        this.entityManager = entityManager;
        this.componentManager = componentManager;
        this.eventManager = eventManager;
    }

    public void update() {
        List<Entity> bodyParts = componentManager.getBodyComponent().getBodyParts();
        sendAppleCollisionEvents(bodyParts);
        sendBodyCollisionEvents(bodyParts);
    }

    private void sendBodyCollisionEvents(List<Entity> bodyParts) {
        Entity firstBodyPart = bodyParts.get(0);
        bodyParts.stream()
                .filter(entity -> !entity.equals(firstBodyPart) && getPosition(firstBodyPart).equals(getPosition(entity)))
                .findFirst()
                .ifPresent(entity -> eventManager.sendEvent(EventType.GAME_ENDED));
    }

    private void sendAppleCollisionEvents(List<Entity> bodyParts) {
        entityManager.get(EntityType.APPLE).stream()
                .filter(entity -> getPosition(bodyParts.get(0)).equals(getPosition(entity)))
                .findFirst()
                .ifPresent(entity -> {
                    eventManager.sendEvent(EventType.APPLE_EATEN, entity);
                    entityManager.getEntities().remove(entity.getId());
                    componentManager.getPositionComponents().remove(entity.getId());
                });
    }

    private Vector2i getPosition(Entity entity) {
        return componentManager.getPositionComponent(entity.getId()).getPosition();
    }

}
