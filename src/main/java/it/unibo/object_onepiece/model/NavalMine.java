package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

/**
 * This class represent a naval mine that is a collidable entity,
 * that deals damage when a ship collide with it.
 */
public final class NavalMine extends Collidable {
    private final int damage;
    private final static int DEFAULT_DAMAGE = 50;

    /**
     * Constructor for class NavalMine.
     * 
     * @param  section  the section of the naval mine
     * @param  position the position of the naval mine
     * @param  damage   the damage inflicted by the naval mine
     */
    protected NavalMine(final Section section, final Position position, final int damage) {
        super(section, position);
        this.damage = damage;
    }

    /**
     * Getter for the default naval mine.
     * 
     * @param  spawnSection  the section where the mine should spawn
     * @param  spawnPosition the position where the mine should spawn
     * @return               the naval mine
     */
    protected static NavalMine getDefault(final Section spawnSection, final Position spawnPosition) {
        return new NavalMine(spawnSection, spawnPosition, DEFAULT_DAMAGE);
    }

    /**
     * Getter for the Rigidness of the naval mine.
     * 
     * @return the Rigidnes of the Collider.
     * @see    Collidable
     */
    @Override
    protected Rigidness getRigidness() {
        return Rigidness.SOFT;
    }

    /**
     * This method calls takeDamage on the collider's 
     * bow if the collider is as ship.
     * 
     * @param  collider the collider that has collide with the naval mine
     * @see    Collider
     * @see    Ship
     */
    @Override
    protected void onCollisionWith(final Collider collider) {
        if (collider instanceof Ship ship) {
            ship.takeDamage(this.damage, ship.getBow());
        }
        this.remove();
    }
}
