package no.taardal.snake.system;

import no.taardal.snake.event.Event;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.observer.Observer;

public class CollisionSystem implements System, Observer {

    private final EntityManager entityManager;
    private final ComponentManager componentManager;
    private final EventManager eventManager;

    public CollisionSystem(EntityManager entityManager, ComponentManager componentManager, EventManager eventManager) {
        this.entityManager = entityManager;
        this.componentManager = componentManager;
        this.eventManager = eventManager;
    }

    @Override
    public void update() {

    }

    @Override
    public void onEvent(Event event) {

    }
}
