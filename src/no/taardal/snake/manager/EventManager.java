package no.taardal.snake.manager;

import no.taardal.snake.event.Event;
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

    public void sendEvent(Event event) {
        if (subjects.containsKey(event.getType())) {
            subjects.get(event.getType()).sendEvent(event);
        }
    }
}
