package no.taardal.snake.entity;

import no.taardal.snake.type.EntityType;

public class Entity {

    private String id;
    private EntityType type;

    public Entity(String id, EntityType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id='" + id + '\'' +
                ", type=" + type +
                '}';
    }
}
