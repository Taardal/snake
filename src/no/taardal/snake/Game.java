package no.taardal.snake;

import no.taardal.snake.component.DirectionComponent;
import no.taardal.snake.component.PositionComponent;
import no.taardal.snake.component.RouteComponent;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.keyboard.Keyboard;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.manager.SystemManager;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.system.*;
import no.taardal.snake.type.EntityType;
import no.taardal.snake.type.EventType;

import java.awt.event.KeyEvent;
import java.lang.System;

public class Game implements Observer {

    int TEST = 431;

    public static final int MAP_SIZE = 5;

    private EventManager eventManager;
    private EntityManager entityManager;
    private ComponentManager componentManager;
    private SystemManager systemManager;
    private Keyboard keyboard;
    private int score;

    private Game() {
        eventManager = new EventManager();
        entityManager = new EntityManager();
        componentManager = new ComponentManager();

        keyboard = new Keyboard();

        InputSystem inputSystem = new InputSystem(entityManager, componentManager, eventManager, keyboard);
        DirectionSystem directionSystem = new DirectionSystem(entityManager, componentManager, eventManager);
        MovementSystem movementSystem = new MovementSystem(entityManager, componentManager, eventManager);
        CollisionSystem collisionSystem = new CollisionSystem(entityManager, componentManager, eventManager);
        SpawnSystem spawnSystem = new SpawnSystem(entityManager, componentManager, eventManager);

        systemManager = new SystemManager(inputSystem, directionSystem, movementSystem, collisionSystem, spawnSystem);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.init();
        game.run();
    }

    @Override
    public void onEvent(EventType eventType, Entity entity) {
        Log.log("Received event " + eventType + ", " + entity);
        if (eventType == EventType.APPLE_EATEN) {
            score += 10;
        }
        if (eventType == EventType.GAME_ENDED) {
            Log.log("GAME OVER! Score: " + score);
        }
    }

    private void init() {
        eventManager.addObserver(systemManager.getDirectionSystem(), EventType.UP_PRESSED);
        eventManager.addObserver(systemManager.getDirectionSystem(), EventType.LEFT_PRESSED);
        eventManager.addObserver(systemManager.getDirectionSystem(), EventType.RIGHT_PRESSED);
        eventManager.addObserver(systemManager.getDirectionSystem(), EventType.DOWN_PRESSED);
        eventManager.addObserver(systemManager.getSpawnSystem(), EventType.GAME_STARTED);
        eventManager.addObserver(systemManager.getSpawnSystem(), EventType.APPLE_EATEN);
        eventManager.addObserver(this, EventType.APPLE_EATEN);
        eventManager.addObserver(this, EventType.GAME_ENDED);
        eventManager.sendEvent(EventType.GAME_STARTED);
    }

    private void run() {
        logMap();
        for (int i = 0; i < 6; i++) {
            if (i == 1) {
                int vkRight = KeyEvent.VK_RIGHT;
                keyboard.press(vkRight);
            }
            if (i == 3) {
                keyboard.press(KeyEvent.VK_DOWN);
            }

            updateSystems();
            logMap();

            if (keyboard.isAnyKeyPressed()) {
                keyboard.release();
            }
        }
    }

    private void updateSystems() {
        systemManager.getInputSystem().update();
        systemManager.getDirectionSystem().update();
        systemManager.getMovementSystem().update();
        systemManager.getCollisionSystem().update();
        systemManager.getSpawnSystem().update();
    }

    private void logMap() {
        int[][] map = new int[MAP_SIZE][MAP_SIZE];
        entityManager.get(EntityType.APPLE).forEach(entity -> {
            PositionComponent positionComponent = componentManager.getPositionComponent(entity.getId());
            map[positionComponent.getY()][positionComponent.getX()] = 2;
        });
        entityManager.get(EntityType.BODY_PART).forEach(entity -> {
            PositionComponent positionComponent = componentManager.getPositionComponent(entity.getId());
            DirectionComponent directionComponent = componentManager.getDirectionComponent(entity.getId());
            RouteComponent routeComponent = componentManager.getRouteComponent(entity.getId());
            map[positionComponent.getY()][positionComponent.getX()] = 1;
        });
        for (int[] row : map) {
            for (int x : row) {
                System.out.print(x + "\t");
            }
            System.out.println();
        }
        Log.logBreak();
    }

}
