package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.Argument;
import it.unibo.object_onepiece.model.events.EventArgs.BiArgument;

/**
 * This class that defines an entity present on the section.
 */
public class Entity {
    private final Section section;
    private Position position;

    public final Event<BiArgument<Position>> onPositionChanged = new Event<>();
    public final Event<Argument<Position>> onEntityRemoved = new Event<>();

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
     */
    protected Section getSection() {
       return this.section;
    }

    /**
     * Getter for the current position of the entity.
     * 
     * @return the position of the entity in the section.
     */
    protected Position getPosition() {
        return this.position;
    }

    /**
     * Setter for the position of the entity.
     * This method invoke an Event onPositionChanged
     * and changes the current position of the Entity
     * to the new position.
     * 
     * @param  newPosition the new position of the entity
     */
    protected void setPosition(final Position newPosition) {
        onPositionChanged.invoke(new BiArgument<>(this.position, newPosition));
        this.position = newPosition;
    }

    /**
     * This method invoke an Event onEntityRemoved
     * and remove the entity from the current section.
     */
    protected void remove() {
        onEntityRemoved.invoke(new Argument<>(this.position));
        this.getSection().removeEntityAt(this.position);
    }
}
