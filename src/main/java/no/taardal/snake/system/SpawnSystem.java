package no.taardal.snake.system;

import no.taardal.snake.Game;
import no.taardal.snake.component.DirectionComponent;
import no.taardal.snake.component.PositionComponent;
import no.taardal.snake.component.RouteComponent;
import no.taardal.snake.component.SpriteComponent;
import no.taardal.snake.direction.Direction;
import no.taardal.snake.direction.DirectionChange;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.shape.Circle;
import no.taardal.snake.type.EntityType;
import no.taardal.snake.type.EventType;
import no.taardal.snake.vector.Vector2i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

public class SpawnSystem implements Observer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpawnSystem.class);
    private static final int MAX_POSSIBLE_SPAWN_LOCATIONS = Game.MAP_SIZE * Game.MAP_SIZE;

    private final EntityManager entityManager;
    private final ComponentManager componentManager;

    private Random random;

    public SpawnSystem(EntityManager entityManager, ComponentManager componentManager, EventManager eventManager) {
        this.entityManager = entityManager;
        this.componentManager = componentManager;
        random = new Random();
        eventManager.addObserver(this, EventType.GAME_STARTED);
        eventManager.addObserver(this, EventType.APPLE_EATEN);
    }

    @Override
    public void onEvent(EventType eventType, Entity entity) {
        LOGGER.info("Received event " + eventType + ", " + entity);
        if (eventType == EventType.GAME_STARTED) {
            for (int i = 0; i < 3; i++) {
                Vector2i spawnPosition = new Vector2i(0, 10 + i);
                spawnBodyPart(spawnPosition, Direction.UP, new ArrayDeque<>());
            }
            spawnApple(new Vector2i(2, 2));
        }
        if (eventType == EventType.APPLE_EATEN) {
            spawnBodyPartAtEndOfBody();
            spawnNextApple(entity);
        }
    }

    private void spawnBodyPart(Vector2i spawnPosition, Direction direction, Deque<DirectionChange> directionChanges) {
        Entity entity = new Entity(getId(), EntityType.BODY_PART);
        entityManager.add(entity);
        componentManager.getBodyComponent().addBodyPart(entity);
        componentManager.add(entity.getId(), new DirectionComponent(direction));
        componentManager.add(entity.getId(), new PositionComponent(spawnPosition));
        componentManager.add(entity.getId(), new RouteComponent(new ArrayDeque<>(directionChanges)));
        componentManager.add(entity.getId(), new SpriteComponent(new Circle(Game.CELL_SIZE), Color.GREEN));
    }

    private String getId() {
        return UUID.randomUUID().toString();
    }

    private void spawnApple(Vector2i position) {
        Entity entity = new Entity(getId(), EntityType.APPLE);
        entityManager.add(entity);
        componentManager.add(entity.getId(), new PositionComponent(position));
        componentManager.add(entity.getId(), new SpriteComponent(new Circle(Game.CELL_SIZE), Color.RED));
    }

    private void spawnBodyPartAtEndOfBody() {
        Entity lastBodyPartEntity = componentManager.getBodyComponent().getLastBodyPart();
        Direction lastBodyPartDirection = componentManager.getDirectionComponent(lastBodyPartEntity.getId()).getDirection();
        Vector2i lastBodyPartPosition = componentManager.getPositionComponent(lastBodyPartEntity.getId()).getPosition();
        Deque<DirectionChange> directionChanges = componentManager.getRouteComponent(lastBodyPartEntity.getId()).getDirectionChanges();
        Vector2i bodyPartSpawnPosition = getSpawnPositionAtEndOfBody(lastBodyPartPosition, lastBodyPartDirection);
        spawnBodyPart(bodyPartSpawnPosition, lastBodyPartDirection, directionChanges);
    }

    private Vector2i getSpawnPositionAtEndOfBody(Vector2i lastBodyPartPosition, Direction lastBodyPartDirection) {
        int x = lastBodyPartPosition.getX();
        int y = lastBodyPartPosition.getY();
        if (lastBodyPartDirection == Direction.UP) {
            y++;
        }
        if (lastBodyPartDirection == Direction.DOWN) {
            y--;
        }
        if (lastBodyPartDirection == Direction.LEFT) {
            x++;
        }
        if (lastBodyPartDirection == Direction.RIGHT) {
            x--;
        }
        return new Vector2i(x, y);
    }

    private void spawnNextApple(Entity eatenAppleEntity) {
        Vector2i eatenApplePosition = componentManager.getPositionComponent(eatenAppleEntity.getId()).getPosition();
        IntStream.range(0, MAX_POSSIBLE_SPAWN_LOCATIONS).boxed()
                .map(i -> new Vector2i(random.nextInt(Game.MAP_SIZE), random.nextInt(Game.MAP_SIZE)))
                .filter(spawnPosition -> !spawnPosition.equals(eatenApplePosition) && !isBodyPartAtPosition(spawnPosition))
                .findFirst()
                .ifPresent(this::spawnApple);
    }

    private boolean isBodyPartAtPosition(Vector2i position) {
        return entityManager.get(EntityType.BODY_PART).stream().anyMatch(bodyPartEntity -> {
            PositionComponent positionComponent1 = componentManager.getPositionComponent(bodyPartEntity.getId());
            return positionComponent1.getPosition().equals(position);
        });
    }

}
