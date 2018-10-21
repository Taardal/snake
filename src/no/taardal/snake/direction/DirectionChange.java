package no.taardal.snake.direction;

import no.taardal.snake.vector.Vector2i;

public class DirectionChange {

    private Direction direction;
    private Vector2i position;

    public DirectionChange(Direction direction, Vector2i position) {
        this.direction = direction;
        this.position = position;
    }

    public Direction getDirection() {
        return direction;
    }

    public Vector2i getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "DirectionChange{" +
                "direction=" + direction +
                ", position=" + position +
                '}';
    }
}
