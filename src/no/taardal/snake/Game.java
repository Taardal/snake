package no.taardal.snake;

import no.taardal.snake.event.GameStartedEvent;
import no.taardal.snake.keyboard.Keyboard;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.manager.SystemManager;
import no.taardal.snake.system.InputSystem;
import no.taardal.snake.system.MovementSystem;
import no.taardal.snake.system.SpawnSystem;
import no.taardal.snake.type.EventType;

import java.awt.event.KeyEvent;

public class Game {

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
        InputSystem inputSystem = new InputSystem(keyboard, eventManager);
        MovementSystem movementSystem = new MovementSystem(entityManager, componentManager, eventManager);
        SpawnSystem spawnSystem = new SpawnSystem(entityManager, componentManager, eventManager);

        systemManager = new SystemManager(inputSystem, movementSystem, spawnSystem);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.init();
        game.run();
    }

    private void init() {
        eventManager.addObserver(systemManager.getMovementSystem(), EventType.DIRECTION_KEY_PRESSED);
        eventManager.addObserver(systemManager.getSpawnSystem(), EventType.GAME_STARTED);
        eventManager.addObserver(systemManager.getSpawnSystem(), EventType.APPLE_EATEN);

        eventManager.sendEvent(new GameStartedEvent());
        logInit();
    }

    private void run() {
        systemManager.getInputSystem().update();
        systemManager.getMovementSystem().update();
        keyboard.press(KeyEvent.VK_RIGHT);
        systemManager.getInputSystem().update();
        systemManager.getMovementSystem().update();
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

}
