package no.taardal.snake.event;

import no.taardal.snake.type.EventType;

public class KeyPressedEvent implements Event {

    private final int keyCode;

    public KeyPressedEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

    @Override
    public EventType getType() {
        return EventType.KEY_PRESSED;
    }

    @Override
    public String getEntityId() {
        return null;
    }
}
