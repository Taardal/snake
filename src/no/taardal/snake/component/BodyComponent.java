package no.taardal.snake.component;

import no.taardal.snake.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class BodyComponent {

    private List<Entity> bodyParts;

    public BodyComponent() {
        this.bodyParts = new ArrayList<>();
    }

    public List<Entity> getBodyParts() {
        return bodyParts;
    }

    public void addBodyPart(Entity bodyPart) {
        bodyParts.add(bodyPart);
    }

    public Entity getFirstBodyPart() {
        return bodyParts.get(0);
    }

    public Entity getLastBodyPart() {
        return bodyParts.get(bodyParts.size() - 1);
    }

    @Override
    public String toString() {
        return "BodyComponent{" +
                "bodyParts=" + bodyParts +
                '}';
    }
}
