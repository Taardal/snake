package no.taardal.snake;

import no.taardal.snake.camera.Camera;
import no.taardal.snake.entity.Entity;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.manager.SystemManager;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.type.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameState implements Observer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameState.class);

    private EventManager eventManager;
    private SystemManager systemManager;

    public GameState() {
        eventManager = new EventManager();
        systemManager = new SystemManager(new EntityManager(), new ComponentManager(), eventManager);
        eventManager.addObserver(this, EventType.GAME_ENDED);
    }


    public void update() {


    }

    public void drawToCamera(Camera camera) {

    }
    @Override
    public void onEvent(EventType eventType, Entity entity) {
        LOGGER.info("Received event " + eventType + ", " + entity);
        if (eventType == EventType.GAME_ENDED) {
            stop();
        }
    }

    public void onKeyPressed(int keyCode) {

    }

    public void start() {

    }

    public String getScore() {
        return null;
    }
}
