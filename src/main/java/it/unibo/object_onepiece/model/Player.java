package it.unibo.object_onepiece.model;
import java.util.List;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.BiArgument;

/**
 * Implementation of the Player interface.
 * @see Player
 */
public final class Player extends Ship {

    private int experience;

    private final StatsUpdatedEvent onStatsUpdated = new StatsUpdatedEvent();

    /**
     * An Event alias that is used when Player stats are updated.
     * @see Event
     * @see Entity
     */
    public static final class StatsUpdatedEvent
    extends Event<BiArgument<List<Integer>>> {
        /**
         * A less verbose version of invoke that directly takes the Event arguments.
         * @param healthList list of all ShipComponents healths
         * @param maxHealthList list of all ShipComponents max healths
         */
        protected void invoke(final List<Integer> healthList,
                              final List<Integer> maxHealthList) {
            super.invoke(new BiArgument<>(healthList, maxHealthList));
        }
    }

    /**
     * Constructor for PlayerImpl.
     * It's protected to only allow creating this object inside this package.
     * Actual object creation is handled in the static method inside Player interface.
     * @param section the reference to the Section containing this Player 
     * @param position the position to place this Player at
     * @param direction the starting direction of the Player's ship
     * @param experience the starting experience value of the Player
     * @param weapon the starting weapon of the Player's ship
     * @param sail the starting sail of the Player's ship
     * @param bow the starting bow of the Player's ship
     * @see Player
     */
    protected Player(final Section section,
                     final Position position,
                     final CardinalDirection direction,
                     final int experience,
                     final Weapon weapon,
                     final Sail sail,
                     final Bow bow,
                     final Keel keel) {
        super(section, position, direction, weapon, sail, bow, keel);
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
                          Bow.standard(),
                          Keel.standard());
    }

    /**
     * Checks wether the Player current position is the same as the one passed as argument.
     * @param position the position to check against
     * @return a boolean that indicates wether the Player is in that position
     */
    public boolean isInSamePositionAs(final Position position) {
        return this.getPosition().equals(position);
    }

    /**
     * Moves the Player's ship towards a specified position.
     * @param destination the position to reach
     * @return a boolean that indicates wether the Player has moved
     * @see Ship
     */
    public boolean moveTo(final Position destination) {
        final CardinalDirection direction = this.getPosition().whereTo(destination);
        final int distance = this.getPosition().distanceFrom(destination);
        final MoveDetails moveResult = super.move(direction, distance);
        return MOVE_SUCCESS_CONDITIONS.contains(moveResult);
    }

    /**
     * Makes the Player shoot at a target position.
     * @param target the position to shoot at
     * @return a ShootReturnType as it's defined in Weapon
     * @see Weapon
     */
    public boolean shootAt(final Position target) {
        return super.shoot(target).hasShooted();
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
    }

    /**
     * Getter for the onStatsUpdated event.
     * @return 
     */
    public StatsUpdatedEvent getStatsUpdatedEvent() {
        return onStatsUpdated;
    }
}
