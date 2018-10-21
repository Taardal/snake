package no.taardal.snake.event;

import no.taardal.snake.entity.Entity;
import no.taardal.snake.type.EventType;

public interface Event {

    EventType getType();

    Entity getEntity();

}
