package no.taardal.snake.system;

import no.taardal.snake.Game;
import no.taardal.snake.Log;
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
            for (int i = 0; i < 3; i++) {
                Vector2i spawnPosition = new Vector2i(0, i + 1);
                Entity entity = new Entity(getId(), EntityType.BODY_PART);
                entityManager.add(entity);
                componentManager.add(entity.getId(), new DirectionComponent(Direction.UP));
                componentManager.add(entity.getId(), new PositionComponent(spawnPosition));
                componentManager.add(entity.getId(), new IndexComponent(i));
            }
            spawnApple(new Vector2i(2, 2));
        }
        if (event.getType() == EventType.APPLE_EATEN) {
            Vector2i eatenApplePosition = componentManager.getPositionComponent(event.getEntity().getId()).getPosition();
            Vector2i appleSpawnPosition = new Vector2i(random.nextInt(Game.MAP_SIZE), random.nextInt(Game.MAP_SIZE));
            if (!appleSpawnPosition.equals(eatenApplePosition) && !isBodyPartOnPosition(appleSpawnPosition)) {
                spawnApple(appleSpawnPosition);
            } else {
                Log.log("Could not spawn apple at position " + appleSpawnPosition.toString());
            }
        }
    }

    private void spawnApple(Vector2i position) {
        Entity entity = new Entity(getId(), EntityType.APPLE);
        entityManager.add(entity);
        componentManager.add(entity.getId(), new PositionComponent(position));
    }

    private boolean isBodyPartOnPosition(Vector2i position) {
        return entityManager.get(EntityType.BODY_PART).stream().anyMatch(bodyPartEntity -> {
            PositionComponent positionComponent1 = componentManager.getPositionComponent(bodyPartEntity.getId());
            return positionComponent1.getPosition().equals(position);
        });
    }

    private String getId() {
        return UUID.randomUUID().toString();
    }
}
