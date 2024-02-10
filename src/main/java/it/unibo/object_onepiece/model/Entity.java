package it.unibo.object_onepiece.model;
import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.TriArguments;

/**
 * This class defines the common methods of every entity present on the section.
 */
public class Entity {

    private final Section section;
    private Position position;
    private CardinalDirection direction;

    private final EntityUpdatedEvent onEntityUpdated = new EntityUpdatedEvent();

    /**
     * An Event alias that is used when an Entity state is updated in any way.
     * @see Event
     * @see Entity
     */
    public static final class EntityUpdatedEvent
    extends Event<TriArguments<Position,
                               Optional<Position>,
                               Optional<CardinalDirection>>> {
        /**
         * A less verbose version of invoke that directly takes the Event arguments.
         * @param  oldPosition the position this Entity was before the update
         * @param  newPosition the position this Entity is after the update
         * @param  direction   the direction this Entity is after the update
         * @see    Event
         */
        protected void invoke(final Position oldPosition,
                              final Optional<Position> newPosition,
                              final Optional<CardinalDirection> direction) {
            super.invoke(new TriArguments<>(oldPosition, newPosition, direction));
        }
    }

    /**
     * Constructor for class Entity.
     * 
     * @param  s section where the entity should be located
     * @param  p position of the entity in the section
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
     * Getter for the onEntityUpdated Event.
     * 
     * @return an event of entity removed.
     * @see    Event
     */
    public EntityUpdatedEvent getEntityUpdatedEvent() {
        return this.onEntityUpdated;
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
        onEntityUpdated.invoke(this.position,
                               Optional.of(newPosition),
                               Optional.of(this.direction));
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
        onEntityUpdated.invoke(this.position,
                               Optional.of(this.position),
                               Optional.of(newDirection));
        this.direction = newDirection;
    }

    /**
     * This method invoke an Event onEntityRemoved
     * and remove the entity from the current section.
     */
    protected void remove() {
        onEntityUpdated.invoke(this.position,
                               Optional.empty(),
                               Optional.empty());
        this.getSection().removeEntityAt(this.position);
    }
}
