package no.taardal.snake.subject;

import no.taardal.snake.entity.Entity;
import no.taardal.snake.observer.Observer;
import no.taardal.snake.type.EventType;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private List<Observer> observers;

    public Subject() {
        this.observers = new ArrayList<>();
    }

    public void add(Observer observer) {
        observers.add(observer);
    }

    public void remove(Observer observer) {
        observers.remove(observer);
    }

    public void sendEvent(EventType eventType, Entity entity) {
        observers.forEach(observer -> observer.onEvent(eventType, entity));
    }

}