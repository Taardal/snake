package no.taardal.snake;

import no.taardal.snake.component.PositionComponent;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.event.Event;
import no.taardal.snake.keyboard.Keyboard;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.manager.SystemManager;
import no.taardal.snake.system.*;
import no.taardal.snake.type.EntityType;
import no.taardal.snake.type.EventType;

import java.awt.event.KeyEvent;
import java.lang.System;

public class Game {

    public static final int MAP_SIZE = 5;

    private EventManager eventManager;
    private EntityManager entityManager;
    private ComponentManager componentManager;
    private SystemManager systemManager;
    private Keyboard keyboard;

    private Game() {
        eventManager = new EventManager();
        entityManager = new EntityManager();
        componentManager = new ComponentManager();

        keyboard = new Keyboard();

        InputSystem inputSystem = new InputSystem(entityManager, componentManager, eventManager, keyboard);
        DirectionSystem directionSystem = new DirectionSystem(entityManager, componentManager, eventManager);
        MovementSystem movementSystem = new MovementSystem(entityManager, componentManager, eventManager);
        CollisionSystem collisionSystem = new CollisionSystem(entityManager, componentManager, eventManager);
        ScoreSystem scoreSystem = new ScoreSystem(entityManager, componentManager, eventManager);
        SpawnSystem spawnSystem = new SpawnSystem(entityManager, componentManager, eventManager);

        systemManager = new SystemManager(inputSystem, directionSystem, movementSystem, collisionSystem, scoreSystem, spawnSystem);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.init();
        game.run();
    }

    private void init() {
        eventManager.addObserver(systemManager.getDirectionSystem(), EventType.UP_PRESSED);
        eventManager.addObserver(systemManager.getDirectionSystem(), EventType.LEFT_PRESSED);
        eventManager.addObserver(systemManager.getDirectionSystem(), EventType.RIGHT_PRESSED);
        eventManager.addObserver(systemManager.getDirectionSystem(), EventType.DOWN_PRESSED);
        eventManager.addObserver(systemManager.getMovementSystem(), EventType.COLLIDED_WITH_MAP);
        eventManager.addObserver(systemManager.getScoreSystem(), EventType.APPLE_EATEN);
        eventManager.addObserver(systemManager.getSpawnSystem(), EventType.GAME_STARTED);
        eventManager.addObserver(systemManager.getSpawnSystem(), EventType.APPLE_EATEN);

        eventManager.sendEvent(getEvent(EventType.GAME_STARTED));

        logInit();
    }

    private void run() {
        logMap();

        updateSystems();

        logMap();

        keyboard.press(KeyEvent.VK_RIGHT);
        updateSystems();
        keyboard.release();

        logMap();
/*
        updateSystems();

        logMap();

        keyboard.press(KeyEvent.VK_DOWN);
        updateSystems();
        keyboard.release();

        logMap();

        updateSystems();

        logMap();
*/
    }

    private void updateSystems() {
        systemManager.getInputSystem().update();
        systemManager.getMovementSystem().update();
        systemManager.getDirectionSystem().update();
        systemManager.getCollisionSystem().update();
        systemManager.getScoreSystem().update();
        systemManager.getSpawnSystem().update();
    }

    private void logInit() {
        Log.log("ENTITIES");
        Log.logLine();
        Log.log(entityManager.getEntities().values().toString());
        Log.logBreak();
        Log.log("COMPONENTS");
        Log.logLine();
        Log.log(componentManager.getDirectionComponents().values().toString());
        Log.log(componentManager.getPositionComponents().values().toString());
        Log.log(componentManager.getIndexComponents().values().toString());
        Log.logBreak();
        Log.log("SYSTEMS");
        Log.logLine();
        Log.log(systemManager.toString());
        Log.logBreak();
    }

    private void logMap() {
        int[][] map = new int[MAP_SIZE][MAP_SIZE];
        entityManager.get(EntityType.APPLE).forEach(entity -> {
            PositionComponent positionComponent = componentManager.getPositionComponent(entity.getId());
            map[positionComponent.getY()][positionComponent.getX()] = 2;
        });
        entityManager.get(EntityType.BODY_PART).forEach(entity -> {
            PositionComponent positionComponent = componentManager.getPositionComponent(entity.getId());
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

    private Event getEvent(EventType eventType) {
        return getEvent(eventType, null);
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
