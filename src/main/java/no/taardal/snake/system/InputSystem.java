package no.taardal.snake.system;

import no.taardal.snake.keyboard.Keyboard;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;
import no.taardal.snake.type.EventType;

import java.awt.event.KeyEvent;

public class InputSystem {

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

    public void update() {
        if (keyboard.isAnyKeyPressed()) {
            if (keyboard.isPressed(KeyEvent.VK_UP)) {
                eventManager.sendEvent(EventType.UP_PRESSED);
            }
            if (keyboard.isPressed(KeyEvent.VK_LEFT)) {
                eventManager.sendEvent(EventType.LEFT_PRESSED);
            }
            if (keyboard.isPressed(KeyEvent.VK_RIGHT)) {
                eventManager.sendEvent(EventType.RIGHT_PRESSED);
            }
            if (keyboard.isPressed(KeyEvent.VK_DOWN)) {
                eventManager.sendEvent(EventType.DOWN_PRESSED);
            }
        }
    }

}
