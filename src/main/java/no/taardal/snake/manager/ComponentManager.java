package no.taardal.snake.manager;

import no.taardal.snake.component.*;

import java.util.HashMap;
import java.util.Map;

public class ComponentManager {

    private Map<String, PositionComponent> positionComponents;
    private Map<String, DirectionComponent> directionComponents;
    private Map<String, RouteComponent> routeComponents;
    private Map<String, SpriteComponent> spriteComponents;
    private BodyComponent bodyComponent;

    public ComponentManager() {
        positionComponents = new HashMap<>();
        directionComponents = new HashMap<>();
        routeComponents = new HashMap<>();
        spriteComponents = new HashMap<>();
        bodyComponent = new BodyComponent();
    }

    public Map<String, PositionComponent> getPositionComponents() {
        return positionComponents;
    }

    public Map<String, DirectionComponent> getDirectionComponents() {
        return directionComponents;
    }

    public Map<String, RouteComponent> getRouteComponents() {
        return routeComponents;
    }

    public Map<String, SpriteComponent> getSpriteComponents() {
        return spriteComponents;
    }

    public BodyComponent getBodyComponent() {
        return bodyComponent;
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

    public RouteComponent getRouteComponent(String entityId) {
        return routeComponents.get(entityId);
    }

    public void add(String entityId, RouteComponent routeComponent) {
        routeComponents.put(entityId, routeComponent);
    }

    public SpriteComponent getSpriteComponent(String entityId) {
        return spriteComponents.get(entityId);
    }

    public void add(String entityId, SpriteComponent spriteComponent) {
        spriteComponents.put(entityId, spriteComponent);
    }
}
