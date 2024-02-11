package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * This class defines the common methods of every entity present on the section.
 */
public class Entity {

    private final Section section;
    private Position position;
    private CardinalDirection direction;

    protected record EntityCreatedArgs(String name, Position spawnPosition, CardinalDirection spawnDirection) { }
    protected record EntityUpdatedArgs(String name, Position oldPosition, Position newPosition, CardinalDirection newDirection) { }
    protected record EntityRemovedArgs(Position lastPosition) { };

    /**
     * Constructor for class Entity.
     * 
     * @param  s section where the entity should be located
     * @param  p position of the entity in the section
     * @param  d direction of the entity in the section
     */
    protected Entity(final Section s, final Position p, final CardinalDirection d) {
        this.section = s;
        this.position = p;
        this.direction = d;
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
        onEntityUpdated.invoke(this.getClass().getSimpleName(),
                               this.position,
                               newPosition,
                               this.direction);
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
        onEntityUpdated.invoke(this.getClass().getSimpleName(),
                               this.position,
                               this.position,
                               newDirection);
        this.direction = newDirection;
    }

    /**
     * This method invoke an Event onEntityRemoved
     * and remove the entity from the current section.
     */
    protected void remove() {
        onEntityRemoved.invoke(position);
        this.getSection().removeEntityAt(this.position);
    }
}
