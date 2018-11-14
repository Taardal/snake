package no.taardal.snake.keyboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayDeque;
import java.util.Deque;

public class Keyboard implements KeyListener {

    public static final int NO_KEY_PRESSED = -1;

    private static final Logger LOGGER = LoggerFactory.getLogger(Keyboard.class);

    private Deque<Integer> pressedKeys;

    public Keyboard() {
        pressedKeys = new ArrayDeque<>();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        LOGGER.info("Pressed key [{}]", e.getKeyChar());
        pressedKeys.addLast(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        LOGGER.info("Released key [{}]", e.getKeyChar());
        if (!pressedKeys.isEmpty()) {
            pressedKeys.remove(e.getKeyCode());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public int getPressedKey() {
        return !pressedKeys.isEmpty() ? pressedKeys.getFirst() : NO_KEY_PRESSED;
    }

}
