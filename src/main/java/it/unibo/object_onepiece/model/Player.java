package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Defines the Player's ship.
 */
public interface Player {
    /**
     * Moves the Player's ship towards a specified position.
     * @param destination the position to reach
     * @return a boolean that indicates wether the Player has moved
     * @see Ship
     */
    boolean moveTo(Position destination);

    /**
     * Makes the Player shoot at a target position.
     * @param target the position to shoot at
     * @return a ShootReturnType as it's defined in Weapon
     * @see Weapon
     */
    boolean shootAt(Position target);

    /**
     * Fully heals the Player by spending experience.
     */
    void healWithExperience();
}
