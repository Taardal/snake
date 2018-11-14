package no.taardal.snake.manager;

import no.taardal.snake.entity.Entity;
import no.taardal.snake.type.EntityType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityManager {

    private Map<String, Entity> entities;

    public EntityManager() {
        this.entities = new HashMap<>();
    }

    public Map<String, Entity> getEntities() {
        return entities;
    }

    public List<Entity> get(EntityType entityType) {
        return entities.values().stream().filter(entity -> entity.getType() == entityType).collect(Collectors.toList());
    }

    public void add(Entity entity) {
        entities.put(entity.getId(), entity);
    }

}
