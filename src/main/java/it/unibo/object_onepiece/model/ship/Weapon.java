package it.unibo.object_onepiece.model.ship;

import it.unibo.object_onepiece.model.Utils.Position;

/**
 * This interface represents a weapon, which is a type of ShipComponent.
 * It defines a method to shoot with the weapon and retrieving maximum damage, 
 * minimum damage and range.
 * It also defines builder methods and builder methods for the available weapons.
 */
public interface Weapon extends ShipComponent {
    /**
     * Enum to define various type of result returned by shoot.
     */
    enum ShootDetails {
        /** The weapon has shooted succesfully. */
        SHOOTED_SUCCESSFULLY,
        /** The position where you want to shoot is out of range. */
        OUT_OF_SHOOTING_RANGE,
        /** The weapon is broken. */
        WEAPON_BROKEN
    };

    /**
     * Record returned by shoot.
     * 
     * @param  hasShooted result of the shooting
     * @param  details    details about the shooting result
     */
    record ShootReturnType(boolean hasShooted, ShootDetails details) { }; 

    /**
     * This method make the Ship shoots with his weapon.
     * 
     * @param  position the cell position where the weapon should shoot
     * @return          a ShootReturnType that contains the result of the shooting and the details.
     */
    ShootReturnType shoot(Position position);

    /**
     * Getter for the maximum damage of the ship weapon.
     * 
     * @return weapon maximum damage.
     */
    int getMaxDamage();

    /**
     * Getter for the minimum damage of the ship weapon.
     * 
     * @return weapon minimum damage.
     */
    int getMinDamage();

    /**
     * Getter for the range of the ship weapon.
     * 
     * @return weapon range.
     */
    int getRange();

    /**
     * Builder for the cannon, the standard type of weapon.
     * 
     * @param  ship the ship that should be assigned to the weapon
     * @return      weapon with his standard stats.
     */
    static Weapon cannon(final Ship ship) {
        final int maxDamage = 20;
        final int minDamage = 10;
        final int range = 3;
        final int health = 100;

        return new WeaponImpl(maxDamage, minDamage, range, ship, health);
    }

    /**
     * Builder for the railgun, the most powerful weapon for damage and range,
     * but more fragile.
     * 
     * @param  ship the ship that should be assigned to the weapon
     * @return      weapon with his standard stats.
     */
    static Weapon railgun(final Ship ship) {
        final int maxDamage = 50;
        final int minDamage = 0;
        final int range = 5;
        final int health = 70;

        return new WeaponImpl(maxDamage, minDamage, range, ship, health);
    }

    /**
     * Builder for the heavycannon, the best weapon for damage and health,
     * but with very short range.
     * 
     * @param  ship the ship that should be assigned to the weapon
     * @return      weapon with his standard stats.
     */
    static Weapon heavycannon(final Ship ship) {
        final int maxDamage = 80;
        final int minDamage = 40;
        final int range = 1;
        final int health = 120;

        return new WeaponImpl(maxDamage, minDamage, range, ship, health);
    }
}
