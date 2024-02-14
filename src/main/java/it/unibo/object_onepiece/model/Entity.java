package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;

/**
 * This class defines the common methods of every entity present on the section.
 */
public class Entity {

    private final Section section;

    private Position position;
    private CardinalDirection direction;

    public record EntityCreatedArgs(String name, Position spawnPosition, CardinalDirection spawnDirection) { }
    private final Event<EntityCreatedArgs> onEntityCreated = new Event<>();

    public record EntityUpdatedArgs(String name, Position oldPosition, Position newPosition, CardinalDirection newDirection) { }
    private final Event<EntityUpdatedArgs> onEntityUpdated = new Event<>();

    public record EntityRemovedArgs(Position lastPosition) { }
    private final Event<EntityRemovedArgs> onEntityRemoved = new Event<>();

    /**
     * Constructor for class Entity.
     * 
     * @param  section   section where the entity should be located
     * @param  position  position of the entity in the section
     * @param  direction direction of the entity in the section
     */
    protected Entity(final Section section, final Position position, final CardinalDirection direction) {
        this.section = section;
        this.position = position;
        this.direction = direction;
    }

    /**
     * Getter for the current section of the entity.
     * 
     * @return the section where the entity is located.
     * @see    Section
     */
    protected Section getSection() {
       return this.section;
    }

    /**
     * Shortcut for the world.
     * 
     * @return the world.
     */
    protected WorldImpl getWorld() {
        return this.getSection().getWorld();
    }

    /**
     * Getter for the current position of the entity.
     * 
     * @return the position of the entity in the section.
     * @see    Utils
     */
    protected Position getPosition() {
        return this.position;
    }

    /**
     * Getter for the current direction of the Entity.
     * 
     * @return the current direction of the Entity.
     */
    protected CardinalDirection getDirection() {
        return this.direction;
    }

    /**
     * Setter for the position of the entity.
     * This method invoke an Event onEntityUpdated
     * and changes the current position of the Entity
     * to the new position.
     * 
     * @param  newPosition the new position of the entity
     * @see    Utils
     */
    protected void setPosition(final Position newPosition) {
        this.onEntityUpdated.invoke(new EntityUpdatedArgs(
            this.getClass().getSimpleName(), this.position, newPosition, this.direction));
        this.position = newPosition;
    }

    /**
     * Setter for the direction of the entity.
     * This method invoke an Event onEntityUpdated
     * and changes the current direction of the Entity
     * to the new direction.
     * 
     * @param  newDirection the new direction of the entity
     * @see    Utils
     */
    protected void setDirection(final CardinalDirection newDirection) {
        this.onEntityUpdated.invoke(new EntityUpdatedArgs(
            this.getClass().getSimpleName(), this.position, this.position, newDirection));
        this.direction = newDirection;
    }

    /**
     * This method invoke an Event onEntityRemoved
     * and remove the entity from the current section.
     */
    protected void remove() {
        this.onEntityRemoved.invoke(new EntityRemovedArgs(this.position));
        this.getSection().removeEntityAt(this.position);
    }

    public Event<EntityCreatedArgs> getEntityCreatedEvent() {
        return onEntityCreated;
    }

    public Event<EntityUpdatedArgs> getEntityUpdatedEvent() {
        return onEntityUpdated;
    }

    public Event<EntityRemovedArgs> getEntityRemovedEvent() {
        return onEntityRemoved;
    }
}
