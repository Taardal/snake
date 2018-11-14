package no.taardal.snake.system;

import no.taardal.snake.component.DirectionComponent;
import no.taardal.snake.component.PositionComponent;
import no.taardal.snake.component.RouteComponent;
import no.taardal.snake.component.SpriteComponent;
import no.taardal.snake.direction.Direction;
import no.taardal.snake.direction.DirectionChange;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
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

    private final EntityManager entityManager;
    private final ComponentManager componentManager;
    private final int mapSize;
    private final int cellSize;
    private final int maxPossibleSpawnLocations;
    private final Random random;

    public SpawnSystem(EntityManager entityManager, ComponentManager componentManager, int mapSize, int cellSize) {
        this.entityManager = entityManager;
        this.componentManager = componentManager;
        this.mapSize = mapSize;
        this.cellSize = cellSize;
        maxPossibleSpawnLocations = mapSize * cellSize;
        random = new Random();
    }

    @Override
    public void onEvent(EventType eventType, Entity entity) {
        LOGGER.info("Received event " + eventType + ", " + entity);
        if (eventType == EventType.GAME_STARTED) {
            for (int i = 0; i < 3; i++) {
                Vector2i spawnPosition = new Vector2i(0, 10 + i);
                ArrayDeque<DirectionChange> directionChanges = new ArrayDeque<>();
                spawnBodyPart(spawnPosition, Direction.UP, directionChanges);
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
        componentManager.addDirectionComponent(entity.getId(), new DirectionComponent(direction));
        componentManager.addPositionComponent(entity.getId(), new PositionComponent(spawnPosition));
        componentManager.addRouteComponent(entity.getId(), new RouteComponent(new ArrayDeque<>(directionChanges)));
        componentManager.addSpriteComponent(entity.getId(), new SpriteComponent(new Circle(cellSize), Color.GREEN));
    }

    private String getId() {
        return UUID.randomUUID().toString();
    }

    private void spawnApple(Vector2i position) {
        Entity entity = new Entity(getId(), EntityType.APPLE);
        entityManager.add(entity);
        componentManager.addPositionComponent(entity.getId(), new PositionComponent(position));
        componentManager.addSpriteComponent(entity.getId(), new SpriteComponent(new Circle(cellSize), Color.RED));
    }

    private void spawnBodyPartAtEndOfBody() {
        Entity lastBodyPart = componentManager.getBodyComponent().getLastBodyPart();
        Vector2i position = getPositionBehindBodyPart(lastBodyPart);
        spawnBodyPart(position, getDirection(lastBodyPart), getDirectionChanges(lastBodyPart));
    }

    private Vector2i getPositionBehindBodyPart(Entity entity) {
        Direction direction = getDirection(entity);
        Vector2i position = getPosition(entity);
        int x = position.getX();
        int y = position.getY();
        if (direction == Direction.UP) {
            y++;
        }
        if (direction == Direction.DOWN) {
            y--;
        }
        if (direction == Direction.LEFT) {
            x++;
        }
        if (direction == Direction.RIGHT) {
            x--;
        }
        return new Vector2i(x, y);
    }

    private Direction getDirection(Entity entity) {
        return componentManager.getDirectionComponent(entity.getId()).getDirection();
    }

    private Vector2i getPosition(Entity entity) {
        return componentManager.getPositionComponent(entity.getId()).getPosition();
    }

    private Deque<DirectionChange> getDirectionChanges(Entity entity) {
        return componentManager.getRouteComponent(entity.getId()).getDirectionChanges();
    }

    private void spawnNextApple(Entity eatenApple) {
        IntStream.range(0, maxPossibleSpawnLocations).boxed()
                .map(i -> new Vector2i(random.nextInt(mapSize), random.nextInt(mapSize)))
                .filter(spawnPosition -> !spawnPosition.equals(getPosition(eatenApple)) && !isBodyPartAtPosition(spawnPosition))
                .findFirst()
                .ifPresent(this::spawnApple);
    }

    private boolean isBodyPartAtPosition(Vector2i position) {
        return entityManager.get(EntityType.BODY_PART).stream().anyMatch(entity -> getPosition(entity).equals(position));
    }

}
