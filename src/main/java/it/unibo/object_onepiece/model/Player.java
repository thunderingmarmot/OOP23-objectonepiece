package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.Argument;

/**
 * Implementation of the Player interface.
 * @see Player
 */
public final class Player extends Ship {

    private int experience;

    private final Event<Argument<Integer>> onExperienceAdded = new Event<>();

    /**
     * Constructor for PlayerImpl.
     * It's protected to only allow creating this object inside this package.
     * Actual object creation is handled in the static method inside Player interface.
     * @param section the reference to the Section containing this Player 
     * @param position the position to place this Player at
     * @param direction the starting direction of the Player's ship
     * @param experience the starting experience value of the Player
     * @see Player
     */
    protected Player(final Section section,
                     final Position position,
                     final CardinalDirection direction,
                     final int experience,
                     final Weapon weapon,
                     final Sail sail,
                     final Bow bow) {
        super(section, position, direction, weapon, sail, bow);
        this.experience = experience;
    }

    /**
     * Creates a default Player.
     * @param spawnSection the reference to the Section containing this Player
     * @param spawnPosition the position to place this Player at
     * @return the newly created Player object
     */
    protected static Player getDefault(final Section spawnSection, final Position spawnPosition) {
        return new Player(spawnSection,
                          spawnPosition,
                          CardinalDirection.NORTH,
                          0,
                          Weapon.cannon(),
                          Sail.sloop(),
                          Bow.standard());
    }

    /**
     * Getter for the experience private field.
     * @return the currently owned experience value
     */
    protected int getExperience() {
        return experience;
    }

    /**
     * Adds experience to the Player's owned experience.
     * @param experience the experience value to add
     */
    protected void addExperience(final int experience) {
        this.experience += experience;
        onExperienceAdded.invoke(new Argument<>(this.experience));
    }

    /**
     * Getter for the onExperienceAdded event.
     * @return the Event object
     * @see Event
     */
    public Event<Argument<Integer>> getExperienceAddedEvent() {
        return onExperienceAdded;
    }
}
