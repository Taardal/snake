package no.taardal.snake.system;

import no.taardal.snake.camera.Camera;
import no.taardal.snake.component.PositionComponent;
import no.taardal.snake.component.SpriteComponent;
import no.taardal.snake.manager.ComponentManager;
import no.taardal.snake.manager.EntityManager;
import no.taardal.snake.manager.EventManager;

public class DrawingSystem {

    private final EntityManager entityManager;
    private final ComponentManager componentManager;
    private final EventManager eventManager;

    public DrawingSystem(EntityManager entityManager, ComponentManager componentManager, EventManager eventManager) {
        this.entityManager = entityManager;
        this.componentManager = componentManager;
        this.eventManager = eventManager;
    }

    public void draw(Camera camera) {
        entityManager.getEntities().values().forEach(entity -> {
            SpriteComponent spriteComponent = componentManager.getSpriteComponent(entity.getId());
            PositionComponent positionComponent = componentManager.getPositionComponent(entity.getId());
            camera.draw(spriteComponent.getCircle(), spriteComponent.getColor(), positionComponent.getPosition());
        });
    }

}
