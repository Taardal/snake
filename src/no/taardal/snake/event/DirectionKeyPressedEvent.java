package no.taardal.snake.event;

import no.taardal.snake.direction.Direction;
import no.taardal.snake.type.EventType;

public class DirectionKeyPressedEvent implements Event {

    private Direction direction;

    public DirectionKeyPressedEvent(Direction direction) {
        this.direction = direction;
    }

    @Override
    public EventType getType() {
        return EventType.DIRECTION_KEY_PRESSED;
    }

    @Override
    public String getEntityId() {
        return null;
    }

    public Direction getDirection() {
        return direction;
    }

}
