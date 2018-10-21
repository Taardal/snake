package no.taardal.snake.system;

import no.taardal.snake.entity.Entity;
import no.taardal.snake.event.Event;
import no.taardal.snake.keyboard.Keyboard;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.type.EventType;

import java.awt.event.KeyEvent;

public class InputSystem implements System {

    private final EntityManager entityManager;
    private final ComponentManager componentManager;
    private final EventManager eventManager;
    private final Keyboard keyboard;

    public InputSystem(EntityManager entityManager, ComponentManager componentManager, EventManager eventManager, Keyboard keyboard) {
        this.entityManager = entityManager;
        this.componentManager = componentManager;
        this.eventManager = eventManager;
        this.keyboard = keyboard;
    }

    @Override
    public void update() {
        if (keyboard.isAnyKeyPressed()) {
            if (keyboard.isPressed(KeyEvent.VK_UP)) {
                eventManager.sendEvent(getEvent(EventType.UP_PRESSED));
            }
            if (keyboard.isPressed(KeyEvent.VK_LEFT)) {
                eventManager.sendEvent(getEvent(EventType.LEFT_PRESSED));
            }
            if (keyboard.isPressed(KeyEvent.VK_RIGHT)) {
                eventManager.sendEvent(getEvent(EventType.RIGHT_PRESSED));
            }
            if (keyboard.isPressed(KeyEvent.VK_DOWN)) {
                eventManager.sendEvent(getEvent(EventType.DOWN_PRESSED));
            }
        }
    }

    private Event getEvent(EventType eventType) {
        return new Event() {

            @Override
            public EventType getType() {
                return eventType;
            }

            @Override
            public Entity getEntity() {
                return null;
            }
        };
    }

}
