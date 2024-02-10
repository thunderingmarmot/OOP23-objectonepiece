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

    private final Event<TriArguments<Optional<Position>, Optional<Position>, Optional<CardinalDirection>>> onEntityUpdated = new Event<>();

    /**
     * Constructor for class Entity.
     * 
     * @param  s section where the entity should be located
     * @param  p position of the entity in the section
     */
    protected Entity(final Section s, final Position p) {
        this.section = s;
        this.position = p;
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
     * Getter for the onEntityUpdated Event.
     * 
     * @return an event of entity removed.
     * @see    Event
     */
    public Event<TriArguments<Optional<Position>, Optional<Position>, Optional<CardinalDirection>>> getEntityUpdatedEvent() {
        return this.onEntityUpdated;
    }

    /**
     * Setter for the position of the entity.
     * This method invoke an Event onPositionChanged
     * and changes the current position of the Entity
     * to the new position.
     * 
     * @param  newPosition the new position of the entity
     * @see    Utils
     */
    protected void setPosition(final Position newPosition) {
        onEntityUpdated.invoke(new TriArguments<>(Optional.of(this.position),
                                                  Optional.of(newPosition),
                                                  Optional.empty()));
        this.position = newPosition;
    }

    /**
     * This method invoke an Event onEntityRemoved
     * and remove the entity from the current section.
     */
    protected void remove() {
        onEntityUpdated.invoke(new TriArguments<>(Optional.of(this.position),
                                                  Optional.empty(),
                                                  Optional.empty()));
        this.getSection().removeEntityAt(this.position);
    }
}
