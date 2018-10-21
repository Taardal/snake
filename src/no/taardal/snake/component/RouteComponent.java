package no.taardal.snake.component;

import no.taardal.snake.direction.DirectionChange;

import java.util.ArrayList;
import java.util.List;

public class RouteComponent {

    private List<DirectionChange> directionChanges;

    public RouteComponent() {
        this.directionChanges = new ArrayList<>();
    }

    public List<DirectionChange> get() {
        return directionChanges;
    }

    public void add(DirectionChange directionChange) {
        directionChanges.add(directionChange);
    }
}
