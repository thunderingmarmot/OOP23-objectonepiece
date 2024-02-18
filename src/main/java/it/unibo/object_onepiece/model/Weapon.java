package it.unibo.object_onepiece.model;

/**
 * This class represents a weapon, which is a type of ShipComponent.
 * It defines a method to shoot with the weapon and retrieving maximum damage, 
 * minimum damage and range.
 * It also defines factory methods for the available weapons.
 * 
 * @see ShipComponent
 */
public final class Weapon extends ShipComponent {
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
    private Weapon(final int health, final int max, final int min, final int range) {
        super(health);
        this.maxDamage = max;
        this.minDamage = min;
        this.attackRange = range;
    }

    /**
     * Constructor to copy from an existing Weapon.
     * 
     * @param  origin the weapon to copy from
     */
    private Weapon(final Weapon origin) {
        super(origin);
        this.maxDamage = origin.getMaxDamage();
        this.minDamage = origin.getMinDamage();
        this.attackRange = origin.getRange();
    }

    /**
     * This method makes a copy of a Weapon.
     * 
     * @return the copy of the weapon.
     */
    @Override
    protected Weapon copy() {
        return new Weapon(this);
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
     * Factory method for the cannon, the standard type of weapon.
     * 
     * @return weapon with his standard stats.
     */
    protected static Weapon cannon() {
        final int health = 100;
        final int maxDamage = 40;
        final int minDamage = 20;
        final int range = 3;

        return new Weapon(health, maxDamage, minDamage, range);
    }

    /**
     * Factory method for the railgun, the most powerful weapon for damage and range,
     * but more fragile.
     * 
     * @return weapon with his standard stats.
     */
    protected static Weapon railgun() {
        final int health = 70;
        final int maxDamage = 100;
        final int minDamage = 0;
        final int range = 5;

        return new Weapon(health, maxDamage, minDamage, range);
    }

    /**
     * Factory method for the heavycannon, the best weapon for damage and health,
     * but with very short range.
     * 
     * @return weapon with his standard stats.
     */
    protected static Weapon heavycannon() {
        final int health = 120;
        final int maxDamage = 60;
        final int minDamage = 20;
        final int range = 2;

        return new Weapon(health, maxDamage, minDamage, range);
    }
}
