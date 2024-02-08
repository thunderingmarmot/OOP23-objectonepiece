package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Implementation of the Barrel interface.
 * @see Barrel
 */
public final class Barrel extends Collidable {

    private static final int DEFAULT_EXPERIENCE_GIVEN = 50;

    private final int experienceGiven;

    /**
     * Creates a default Barrel.
     * @param spawnSection the reference to the Section containing this Barrel
     * @param spawnPosition the position to place this Barrel at
     * @return the newly created Barrel object
     */
    protected static Barrel getDefault(final Section spawnSection, final Position spawnPosition) {
        return new Barrel(spawnSection, spawnPosition, DEFAULT_EXPERIENCE_GIVEN);
    }

    /**
     * Constructor for BarrelImpl.
     * It's protected to only allow creating this object inside this package.
     * Actual object creation is handled in the static method inside Barrel interface.
     * @param section the reference to the Section containing this Barrel 
     * @param position the position to place this Island at
     * @param experienceGiven the experience value this Barrel gives the Player when taken
     */
    protected Barrel(final Section section, final Position position, final int experienceGiven) {
        super(section, position);
        this.experienceGiven = experienceGiven;
    }

    /**
     * Defines the behaviour of getting taken by a Player.
     * @param player the Player that is taking this Barrel
     * @see Player
     */
    public void take(final Player player) {
        player.addExperience(experienceGiven);
    }

    /**
     * A collision with a Barrel means destroying it and picking it up.
     * @param collider the Collider that collided with the Barrel
     * @see Collidable
     */
    @Override
    public void onCollisionWith(final Collider collider) {
        if (collider instanceof Player player) {
            take(player);
        }
        this.remove();
    }

    @Override
    public Rigidness getRigidness() {
        return Rigidness.SOFT;
    }
}
