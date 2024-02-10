package it.unibo.object_onepiece.model;

/**
 * This class represents a weapon, which is a type of ShipComponent.
 * It defines a method to shoot with the weapon and retrieving maximum damage, 
 * minimum damage and range.
 * It also defines builder methods and builder methods for the available weapons.
 * 
 * @see ShipComponent
 */
public final class Weapon extends ShipComponent {
    /**
     * Enum to define various type of result returned by shoot.
     */
    protected enum ShootDetails {
        /** 
         * The weapon has shooted succesfully. 
         */
        SHOOTED_SUCCESSFULLY,
        /**
         * The position where you want to shoot is out of range. 
         */
        OUT_OF_SHOOTING_RANGE,
        /** 
         * The weapon is broken. 
         */
        WEAPON_BROKEN
    }

    /**
     * Record returned by shoot.
     * 
     * @param  hasShooted result of the shooting
     * @param  details    details about the shooting result
     */
    protected record ShootReturnType(boolean hasShooted, ShootDetails details) { }

    private final int maxDamage;
    private final int minDamage;
    private final int attackRange;

    /**
     * Constructor for class Weapon.
     * 
     * @param  health the health of the weapon
     * @param  max    the maximum damage of the weapon
     * @param  min    the minimum damage of the weapon
     * @param  range  the range of the weapon
     */
    protected Weapon(final int health, final int max, final int min, final int range) {
        super(health);
        this.maxDamage = max;
        this.minDamage = min;
        this.attackRange = range;
    }

    /**
     * Getter for the maximum damage of the ship weapon.
     * 
     * @return weapon maximum damage.
     */
    protected int getMaxDamage() {
        return this.maxDamage;
    }

    /**
     * Getter for the minimum damage of the ship weapon.
     * 
     * @return weapon minimum damage.
     */
    protected int getMinDamage() {
        return this.minDamage;
    }

    /**
     * Getter for the range of the ship weapon.
     * 
     * @return weapon range.
     */
    protected int getRange() {
        return this.attackRange;
    }

    /**
     * Builder for the cannon, the standard type of weapon.
     * 
     * @return      weapon with his standard stats.
     */
    protected static Weapon cannon() {
        final int health = 100;
        final int maxDamage = 20;
        final int minDamage = 10;
        final int range = 3;

        return new Weapon(health, maxDamage, minDamage, range);
    }

    /**
     * Builder for the railgun, the most powerful weapon for damage and range,
     * but more fragile.
     * 
     * @return      weapon with his standard stats.
     */
    protected static Weapon railgun() {
        final int health = 70;
        final int maxDamage = 50;
        final int minDamage = 0;
        final int range = 5;

        return new Weapon(health, maxDamage, minDamage, range);
    }

    /**
     * Builder for the heavycannon, the best weapon for damage and health,
     * but with very short range.
     * 
     * @return      weapon with his standard stats.
     */
    protected static Weapon heavycannon() {
        final int health = 120;
        final int maxDamage = 80;
        final int minDamage = 40;
        final int range = 1;

        return new Weapon(health, maxDamage, minDamage, range);
    }
}
