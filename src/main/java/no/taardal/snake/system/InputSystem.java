package no.taardal.snake.system;

import no.taardal.snake.manager.EventManager;
import no.taardal.snake.type.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputSystem implements KeyListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputSystem.class);

    private final EventManager eventManager;

    public InputSystem(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        LOGGER.info("KEY RELEASED: " + keyEvent);
        int keyCode = keyEvent.getKeyCode();
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

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
