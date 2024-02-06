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
        SHOOTED_SUCCESSFULLY,
        OUT_OF_SHOOTING_RANGE,
        WEAPON_BROKEN
    };

    /**
     * Record returned by shoot
     * 
     * @param  hasShooted result of the shooting
     * @param  details    details about the shooting result
     */
    record ShootReturnType(boolean hasShooted, ShootDetails details) { }; 

    /**
     * This method make the Ship shoots with his weapon
     * 
     * @param  position the position of the cell that you want to hit
     * @return          a ShootReturnType that contains the result of the shooting and the details.
     */
    ShootReturnType shoot(Position position);

    int getMaxDamage();
    int getMinDamage();
    int getRange();

    static Weapon cannon() {
        final int maxDamage = 20;
        final int minDamage = 10;
        final int range = 3;
        final int health = 100;

        return new WeaponImpl(maxDamage, minDamage, range, health);
    }

    static Weapon railgun() {
        final int maxDamage = 50;
        final int minDamage = 0;
        final int range = 5;
        final int health = 70;

        return new WeaponImpl(maxDamage, minDamage, range, health);
    }

    static Weapon heavycannon() {
        final int maxDamage = 80;
        final int minDamage = 40;
        final int range = 1;
        final int health = 120;

        return new WeaponImpl(maxDamage, minDamage, range, health);
    }
}
