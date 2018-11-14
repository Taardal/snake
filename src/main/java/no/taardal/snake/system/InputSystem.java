package no.taardal.snake.system;

import no.taardal.snake.manager.EventManager;
import no.taardal.snake.type.EventType;

import java.awt.event.KeyEvent;

public class InputSystem {

    private final EventManager eventManager;

    public InputSystem(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void onKeyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            eventManager.sendEvent(EventType.UP_PRESSED);
        }
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            eventManager.sendEvent(EventType.LEFT_PRESSED);
        }
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            eventManager.sendEvent(EventType.RIGHT_PRESSED);
        }
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            eventManager.sendEvent(EventType.DOWN_PRESSED);
        }
    }
}
