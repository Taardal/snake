package no.taardal.snake.system;

import no.taardal.snake.entity.Entity;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.type.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameSystem implements Observer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameSystem.class);

    @Override
    public void onEvent(EventType eventType, Entity entity) {
        if (eventType == EventType.GAME_ENDED) {
            LOGGER.info("GAME ENDED");
        }
    }

}
