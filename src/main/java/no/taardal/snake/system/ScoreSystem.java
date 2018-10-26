package no.taardal.snake.system;

import no.taardal.snake.entity.Entity;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.type.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScoreSystem implements Observer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScoreSystem.class);
    private static final int APPLE_SCORE = 500;

    private int score;

    public ScoreSystem(EventManager eventManager) {
        eventManager.addObserver(this, EventType.APPLE_EATEN);
    }

    @Override
    public void onEvent(EventType eventType, Entity entity) {
        LOGGER.info("Received event " + eventType + ", " + entity);
        if (eventType == EventType.APPLE_EATEN) {
            score += APPLE_SCORE;
        }
    }

    public int getScore() {
        return score;
    }
}
