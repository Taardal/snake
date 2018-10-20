package no.taardal.snake.system;

import no.taardal.snake.direction.Direction;
import no.taardal.snake.event.DirectionKeyPressedEvent;
import no.taardal.snake.keyboard.Keyboard;
import no.taardal.snake.manager.EventManager;

import java.awt.event.KeyEvent;

public class InputSystem implements System {

    private final Keyboard keyboard;
    private final EventManager eventManager;

    public InputSystem(Keyboard keyboard, EventManager eventManager) {
        this.keyboard = keyboard;
        this.eventManager = eventManager;
    }

    @Override
    public void update() {
        Direction pressedDirection = getPressedDirection();
        if (pressedDirection != null) {
            eventManager.sendEvent(new DirectionKeyPressedEvent(pressedDirection));
        }
    }

    private Direction getPressedDirection() {
        if (keyboard.isPressed(KeyEvent.VK_UP)) {
            return Direction.UP;
        }
        if (keyboard.isPressed(KeyEvent.VK_DOWN)) {
            return Direction.LEFT;
        }
        if (keyboard.isPressed(KeyEvent.VK_RIGHT)) {
            return Direction.RIGHT;
        }
        if (keyboard.isPressed(KeyEvent.VK_LEFT)) {
            return Direction.DOWN;
        }
        return null;
    }

}
