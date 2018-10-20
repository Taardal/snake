package no.taardal.snake.event;

import no.taardal.snake.type.EventType;

public class AppleEatenEvent implements Event {

    private int x;
    private int y;

    public AppleEatenEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public EventType getType() {
        return EventType.APPLE_EATEN;
    }

    @Override
    public String getEntityId() {
        return null;
    }

}
