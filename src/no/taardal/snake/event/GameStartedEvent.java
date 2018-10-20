package no.taardal.snake.event;

import no.taardal.snake.type.EventType;

public class GameStartedEvent implements Event {

    @Override
    public EventType getType() {
        return EventType.GAME_STARTED;
    }

    @Override
    public String getEntityId() {
        return null;
    }

}
