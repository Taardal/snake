package no.taardal.snake.system;

import no.taardal.snake.Game;
import no.taardal.snake.Log;
import no.taardal.snake.component.DirectionComponent;
import no.taardal.snake.component.PositionComponent;
import no.taardal.snake.component.RouteComponent;
import no.taardal.snake.direction.Direction;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.type.EntityType;
import no.taardal.snake.type.EventType;
import no.taardal.snake.vector.Vector2i;

import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

public class SpawnSystem implements System, Observer {

    private static final int MAX_POSSIBLE_SPAWN_LOCATIONS = Game.MAP_SIZE * Game.MAP_SIZE;

    private EntityManager entityManager;
    private ComponentManager componentManager;
    private EventManager eventManager;
    private Random random;

    public SpawnSystem(EntityManager entityManager, ComponentManager componentManager, EventManager eventManager) {
        this();
        this.entityManager = entityManager;
        this.componentManager = componentManager;
        this.eventManager = eventManager;
    }

    private SpawnSystem() {
        random = new Random();
    }

    @Override
    public void update() {

    }

    @Override
    public void onEvent(EventType eventType, Entity entity) {
        Log.log("Received event " + eventType + ", " + entity);
        if (eventType == EventType.GAME_STARTED) {
            for (int i = 0; i < 3; i++) {
                Vector2i spawnPosition = new Vector2i(0, i + 1);
                spawnBodyPart(spawnPosition, Direction.UP);
            }
            spawnApple(new Vector2i(2, 2));
        }
        if (eventType == EventType.APPLE_EATEN) {
            spawnBodyPartAtEndOfBody();
            spawnNextApple(entity);
        }
    }

    private void spawnBodyPart(Vector2i spawnPosition, Direction direction) {
        Entity bodyPartEntity = new Entity(getId(), EntityType.BODY_PART);
        entityManager.add(bodyPartEntity);
        componentManager.add(bodyPartEntity.getId(), new DirectionComponent(direction));
        componentManager.add(bodyPartEntity.getId(), new PositionComponent(spawnPosition));
        componentManager.add(bodyPartEntity.getId(), new RouteComponent());
        componentManager.getBodyComponent().addBodyPart(bodyPartEntity);
    }

    private String getId() {
        return UUID.randomUUID().toString();
    }

    private void spawnApple(Vector2i position) {
        Entity entity = new Entity(getId(), EntityType.APPLE);
        entityManager.add(entity);
        componentManager.add(entity.getId(), new PositionComponent(position));
    }

    private void spawnBodyPartAtEndOfBody() {
        Entity lastBodyPartEntity = componentManager.getBodyComponent().getLastBodyPart();
        Direction lastBodyPartDirection = componentManager.getDirectionComponent(lastBodyPartEntity.getId()).getDirection();
        Vector2i lastBodyPartPosition = componentManager.getPositionComponent(lastBodyPartEntity.getId()).getPosition();
        Vector2i bodyPartSpawnPosition = getSpawnPositionAtEndOfBody(lastBodyPartPosition, lastBodyPartDirection);
        spawnBodyPart(bodyPartSpawnPosition, lastBodyPartDirection);
    }

    private Vector2i getSpawnPositionAtEndOfBody(Vector2i lastBodyPartPosition, Direction lastBodyPartDirection) {
        int x = lastBodyPartPosition.getX();
        int y = lastBodyPartPosition.getY();
        if (lastBodyPartDirection == Direction.UP) {
            y--;
        }
        if (lastBodyPartDirection == Direction.DOWN) {
            y++;
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
