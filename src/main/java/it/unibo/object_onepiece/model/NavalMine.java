package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * This class represent a naval mine that is a collidable entity,
 * that deals damage when a ship collide with it.
 */
public final class NavalMine extends Collidable {
    private static final int DEFAULT_DAMAGE = 50;
    private final int damage;

    /**
     * Constructor for class NavalMine.
     * 
     * @param  section   the section of the naval mine
     * @param  position  the position of the naval mine
     * @param  direction the direction of the naval mine
     * @param  damage    the damage inflicted by the naval mine
     */
    private NavalMine(final Section section, 
                      final Position position, 
                      final CardinalDirection direction, 
                      final int damage) {
        super(section, position, direction);
        this.damage = damage;
    }

    /**
     * Constructor for the default naval mine.
     * 
     * @param  spawnSection   the section where the mine should spawn
     * @param  spawnPosition  the position where the mine should spawn
     * @param  spawnDirection the spawn direction of the mine
     */
    protected NavalMine(final Section spawnSection, 
                        final Position spawnPosition, 
                        final CardinalDirection spawnDirection) {
        super(spawnSection, spawnPosition, spawnDirection);
        this.damage = DEFAULT_DAMAGE;
    }

    protected NavalMine(final NavalMine origin) {
        super(origin);
        this.damage = origin.damage;
    }

    protected int getDamage() {
        return this.damage;
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
            ship.takeDamage(this.damage, ship.getKeel());
        }
        this.remove();
    }
}
