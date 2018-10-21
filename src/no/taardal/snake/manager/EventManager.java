package no.taardal.snake.manager;

import no.taardal.snake.entity.Entity;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.subject.Subject;
import no.taardal.snake.type.EventType;

import java.util.HashMap;
import java.util.Map;

public class EventManager {

    private Map<EventType, Subject> subjects;

    public EventManager() {
        subjects = new HashMap<>();
    }

    public void addObserver(Observer observer, EventType eventType) {
        if (!subjects.containsKey(eventType)) {
            subjects.put(eventType, new Subject());
        }
        subjects.get(eventType).add(observer);
    }

    public void sendEvent(EventType eventType) {
        sendEvent(eventType, null);
    }

    public void sendEvent(EventType eventType, Entity entity) {
        if (subjects.containsKey(eventType)) {
            subjects.get(eventType).sendEvent(eventType, entity);
        }
    }
}
