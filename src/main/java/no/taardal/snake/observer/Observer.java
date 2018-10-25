package no.taardal.snake.observer;

import no.taardal.snake.entity.Entity;
import no.taardal.snake.type.EventType;

public interface Observer {

    void onEvent(EventType eventType, Entity entity);
}
