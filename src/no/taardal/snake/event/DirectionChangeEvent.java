package no.taardal.snake.event;

import no.taardal.snake.direction.Direction;
import no.taardal.snake.type.EventType;

public class DirectionChangeEvent implements Event {

    private Direction direction;
    private String entityId;

    public DirectionChangeEvent(Direction direction, String entityId) {
        this.direction = direction;
        this.entityId = entityId;
    }

    @Override
    public EventType getType() {
        return EventType.DIRECTION_CHANGE;
    }

    @Override
    public String getEntityId() {
        return entityId;
    }

    public Direction getDirection() {
        return direction;
    }
}
