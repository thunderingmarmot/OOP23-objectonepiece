package it.unibo.object_onepiece.model;
import java.util.List;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.Weapon.ShootReturnType;

/**
 * Implementation of the Player interface.
 * @see Player
 */
public final class Player extends Ship {

    private int experience;

    private static final List<MoveDetails> MOVE_SUCCESS_CONDITIONS = List.of(
        MoveDetails.MOVED_SUCCESSFULLY,
        MoveDetails.MOVED_BUT_COLLIDED,
        MoveDetails.ROTATED
    );

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
     * Checks wether the Player current position is the same as the one passed as argument.
     * @param position the position to check against
     * @return a boolean that indicates wether the Player is in that position
     */
    public boolean isInSamePositionAs(final Position position) {
        return this.getPosition() == position;
    }

    /**
     * Moves the Player's ship towards a specified position.
     * @param destination the position to reach
     * @return a boolean that indicates wether the Player has moved
     * @see Ship
     */
    public boolean move(final Position destination) {
        CardinalDirection direction = this.getPosition().whereTo(destination);
        int distance = this.getPosition().distanceFrom(destination);
        MoveDetails moveResult = super.move(direction, distance);
        return MOVE_SUCCESS_CONDITIONS.contains(moveResult);
    }

    /**
     * Makes the Player shoot at a target position.
     * @param target the position to shoot at
     * @return a ShootReturnType as it's defined in Weapon
     * @see Weapon
     */
    @Override
    public ShootReturnType shoot(final Position target) {
        return super.shoot(target);
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
}
