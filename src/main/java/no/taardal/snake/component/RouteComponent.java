package no.taardal.snake.component;

import no.taardal.snake.direction.DirectionChange;

import java.util.ArrayDeque;
import java.util.Deque;

public class RouteComponent {

    private Deque<DirectionChange> directionChanges;

    public RouteComponent() {
        this.directionChanges = new ArrayDeque<>();
    }

    public Deque<DirectionChange> getDirectionChanges() {
        return directionChanges;
    }

    public void addDirectionChange(DirectionChange directionChange) {
        directionChanges.addLast(directionChange);
    }

    public void removeFirst() {
        directionChanges.removeFirst();
    }

    @Override
    public String toString() {
        return "RouteComponent{" +
                "directionChanges=" + directionChanges +
                '}';
    }
}
