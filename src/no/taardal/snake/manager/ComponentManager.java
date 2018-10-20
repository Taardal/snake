package no.taardal.snake.manager;

import no.taardal.snake.component.DirectionComponent;
import no.taardal.snake.component.IndexComponent;
import no.taardal.snake.component.PositionComponent;

import java.util.HashMap;
import java.util.Map;

public class ComponentManager {

    private Map<String, PositionComponent> positionComponents;
    private Map<String, DirectionComponent> directionComponents;
    private Map<String, IndexComponent> indexComponents;

    public ComponentManager() {
        positionComponents = new HashMap<>();
        directionComponents = new HashMap<>();
        indexComponents = new HashMap<>();
    }

    public PositionComponent getPositionComponent(String entityId) {
        return positionComponents.get(entityId);
    }

    public void add(String entityId, PositionComponent positionComponent) {
        positionComponents.put(entityId, positionComponent);
    }

    public DirectionComponent getDirectionComponent(String entityId) {
        return directionComponents.get(entityId);
    }

    public void add(String entityId, DirectionComponent directionComponent) {
        directionComponents.put(entityId, directionComponent);
    }

    public IndexComponent getIndexComponent(String entityId) {
        return indexComponents.get(entityId);
    }

    public void add(String entityId, IndexComponent indexComponent) {
        indexComponents.put(entityId, indexComponent);
    }

    public Map<String, PositionComponent> getPositionComponents() {
        return positionComponents;
    }

    public Map<String, DirectionComponent> getDirectionComponents() {
        return directionComponents;
    }

    public Map<String, IndexComponent> getIndexComponents() {
        return indexComponents;
    }
}
