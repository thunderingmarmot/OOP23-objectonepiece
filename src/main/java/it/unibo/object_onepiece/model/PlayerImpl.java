package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.Argument;
import it.unibo.object_onepiece.model.ship.ShipImpl;

/**
 * Implementation of the Player interface.
 * @see Player
 */
public class PlayerImpl extends ShipImpl implements Player {

    private int experience;

    private final Event<Argument<Integer>> onExperienceAdded = Event.get();

    /**
     * Constructor for PlayerImpl.
     * It's protected to only allow creating this object inside this package.
     * Actual object creation is handled in the static method inside Player interface.
     * @param section the reference to the Section containing this Player 
     * @param position the position to place this Player at
     * @param direction the starting direction of the Player's ship
     * @param weapon the starting weapon of the Player's ship
     * @param sail the starting sail of the Player's ship
     * @param bow the starting bow of the Player's ship
     * @param experience the starting experience value of the Player
     * @see Player
     */
    protected PlayerImpl(final Section section,
                         final Position position,
                         final CardinalDirection direction,
                         final int experience) {
        super(section, position, direction);
        this.experience = experience;
    }

    /**
     * Getter for the experience private field.
     * @return the currently owned experience value
     */
    @Override
    public int getExperience() {
        return experience;
    }

    /**
     * Adds experience to the Player's owned experience.
     * @param experience the experience value to add
     */
    @Override
    public void addExperience(final int experience) {
        this.experience += experience;
        onExperienceAdded.invoke(new Argument<>(this.experience));
    }
    
    /**
     * Getter for the onExperienceAdded Event.
     * @return an Event object that gets invoked when experience is added to the Player.
     * @see Event
     */
    @Override
    public Event<Argument<Integer>> getExperienceAddedEvent() {
        return onExperienceAdded;
    }
}
