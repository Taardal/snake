package no.taardal.snake.system;

import no.taardal.snake.component.DirectionComponent;
import no.taardal.snake.component.IndexComponent;
import no.taardal.snake.component.PositionComponent;
import no.taardal.snake.direction.Direction;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.event.AppleEatenEvent;
import no.taardal.snake.event.Event;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.type.EntityType;
import no.taardal.snake.type.EventType;
import no.taardal.snake.vector.Vector2i;

import java.util.Random;
import java.util.UUID;

public class SpawnSystem implements System, Observer {

    private EntityManager entityManager;
    private ComponentManager componentManager;
    private EventManager eventManager;
    private Random random;

    public SpawnSystem(EntityManager entityManager, ComponentManager componentManager, EventManager eventManager) {
        this.entityManager = entityManager;
        this.componentManager = componentManager;
        this.eventManager = eventManager;
        random = new Random();
    }

    @Override
    public void update() {

    }

    @Override
    public void onEvent(Event event) {
        if (event.getType() == EventType.GAME_STARTED) {
            spawnBodyParts();
            spawnApple();
        }
        if (event.getType() == EventType.APPLE_EATEN) {
            AppleEatenEvent appleEatenEvent = (AppleEatenEvent) event;

            Entity eatenApple = entityManager.get(appleEatenEvent.getEntityId());
            PositionComponent positionComponent = componentManager.getPositionComponent(appleEatenEvent.getEntityId());

            int x = random.nextInt(3);
            int y = random.nextInt(3);
            if (x != positionComponent.getX() || y != positionComponent.getY()) {
                Entity appleToBeSpawned = new Entity(getId(), EntityType.APPLE);
                entityManager.add(appleToBeSpawned);
                componentManager.add(appleToBeSpawned.getId(), new PositionComponent(new Vector2i(x, y)));
            } else {
                x = random.nextInt(3);
                y = random.nextInt(3);
            }
        }
    }

    private void spawnBodyParts() {
        for (int i = 0; i < 3; i++) {
            Entity bodyPartEntity = new Entity(getId(), EntityType.BODY_PART);
            entityManager.add(bodyPartEntity);
            componentManager.add(bodyPartEntity.getId(), new PositionComponent(new Vector2i(0, i)));
            componentManager.add(bodyPartEntity.getId(), new DirectionComponent(Direction.UP));
            componentManager.add(bodyPartEntity.getId(), new IndexComponent(i));
        }
    }

    private void spawnApple() {
        Entity appleEntity = new Entity(getId(), EntityType.APPLE);
        entityManager.add(appleEntity);
        componentManager.add(appleEntity.getId(), new PositionComponent(new Vector2i(2, 2)));
    }

    private String getId() {
        return UUID.randomUUID().toString();
    }
}
