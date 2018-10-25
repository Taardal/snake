package no.taardal.snake.component;

import no.taardal.snake.vector.Vector2i;

public class PositionComponent {

    private Vector2i position;

    public PositionComponent(Vector2i position) {
        this.position = position;
    }

    public Vector2i getPosition() {
        return position;
    }

    public void setPosition(Vector2i position) {
        this.position = position;
    }

    public int getX() {
        return position.getX();
    }

    public void setX(int x) {
        position.setX(x);
    }

    public int getY() {
        return position.getY();
    }

    public void setY(int y) {
        position.setY(y);
    }

    @Override
    public String toString() {
        return "PositionComponent{" +
                "position=" + position +
                '}';
    }
}
