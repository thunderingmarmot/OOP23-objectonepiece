package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * This class defines the common methods of every entity present on the section.
 */
public abstract class Entity {

    private final Section section;

    private Position position;
    private CardinalDirection direction;

    /**
     * This record contains the arguments of the Entity created Event.
     * 
     * @param  name           the name of the Entity
     * @param  spawnPosition  the spawn position of the Entity
     * @param  spawnDirection the spawn direction of the Entity
     */
    public record EntityCreatedArgs(String name, Position spawnPosition, CardinalDirection spawnDirection) { }
    private final Event<EntityCreatedArgs> onEntityCreated = new Event<>();

    /**
     * This record contains the arguments of the Entity updated Event.
     * 
     * @param  name         the name of the Entity
     * @param  oldPosition  the old position of the Entity
     * @param  newPosition  the new position of the Entity
     * @param  newDirection the new direction of the Entity
     */
    public record EntityUpdatedArgs(String name, Position oldPosition, Position newPosition, CardinalDirection newDirection) { }
    private final Event<EntityUpdatedArgs> onEntityUpdated = new Event<>();

    /**
     * This record contains the arguments of the Entity removed Event.
     * 
     * @param  lastPosition the last position of the Entity
     */
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

    protected abstract Entity duplicate();

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
     * Removes entity from the section at its own position.
     */
    protected void remove() {
        this.getSection().removeEntityAt(this.position);
    }

    /**
     * Getter for the entity created Event.
     * 
     * @return the Event on entity created.
     */
    public Event<EntityCreatedArgs> getEntityCreatedEvent() {
        return onEntityCreated;
    }

    /**
     * Getter for the entity updated Event.
     * 
     * @return the Event on entity updated.
     */
    public Event<EntityUpdatedArgs> getEntityUpdatedEvent() {
        return onEntityUpdated;
    }

    /**
     * Getter for the entity removed Event.
     * 
     * @return the Event on entity removed.
     */
    public Event<EntityRemovedArgs> getEntityRemovedEvent() {
        return onEntityRemoved;
    }
}
