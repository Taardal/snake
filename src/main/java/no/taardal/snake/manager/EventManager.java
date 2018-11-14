package no.taardal.snake.manager;

import no.taardal.snake.entity.Entity;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.type.EventType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    private Map<EventType, List<Observer>> observers;

    public EventManager() {
        observers = new HashMap<>();
    }

    public void addObserver(Observer observer, EventType eventType) {
        if (!observers.containsKey(eventType)) {
            observers.put(eventType, new ArrayList<>());
        }
        observers.get(eventType).add(observer);
    }

    public void sendEvent(EventType eventType) {
        sendEvent(eventType, null);
    }

    public void sendEvent(EventType eventType, Entity entity) {
        if (observers.containsKey(eventType)) {
            observers.get(eventType).forEach(observer -> observer.onEvent(eventType, entity));
        }
    }
}
