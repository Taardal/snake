package no.taardal.snake.factory;

import no.taardal.snake.entity.Entity;
import no.taardal.snake.event.Event;
import no.taardal.snake.type.EventType;

public class EventFactory {

    public Event get(EventType eventType, Entity entity) {
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
