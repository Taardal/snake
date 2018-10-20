package no.taardal.snake.observer;

import no.taardal.snake.event.Event;

public interface Observer {

    void onEvent(Event event);
}
