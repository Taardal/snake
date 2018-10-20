package no.taardal.snake.component;

import no.taardal.snake.direction.Direction;

public class DirectionComponent {

    private Direction direction;

    public DirectionComponent(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "DirectionComponent{" +
                "direction=" + direction +
                '}';
    }

}
