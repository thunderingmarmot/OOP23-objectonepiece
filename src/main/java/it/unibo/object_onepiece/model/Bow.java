package it.unibo.object_onepiece.model;

/**
 * This class represents a bow, which is a type of ShipComponent.
 * It defines a method to retrieving crash damage of the bow.
 * It also defines and factory methods for the available bows.
 * The bow is the frontal structure of the ship.
 * 
 * @see ShipComponent
 */
public final class Bow extends ShipComponent {
    private static final int BROKEN_CRASH_DAMAGE = 0;
    private final int crashDamage;
    private final int crashDamageMultiplier;

    /**
     * Constructor for class Bow.
     * 
     * @param  health                the health of the bow
     * @param  crashDamage           the crash damage of the bow
     * @param  crashDamageMultiplier the crash damage multiplier of the bow
     */
    private Bow(final int health, final int crashDamage, final int crashDamageMultiplier) {
        super(health);
        this.crashDamage = crashDamage;
        this.crashDamageMultiplier = crashDamageMultiplier;
    }

    /**
     * Constructor to copy from an existing Bow.
     * 
     * @param  origin the bow to copy from
     */
    private Bow(final Bow origin) {
        super(origin);
        this.crashDamage = origin.getCrashDamage();
        this.crashDamageMultiplier = origin.getCrashDamageMultiplier();
    }

    /**
     * This method makes a copy of a Bow.
     * 
     * @return the copy of the bow.
     */
    @Override
    protected Bow copy() {
        return new Bow(this);
    }

    /**
     * Getter for the crash damage of the ship bow.
     * 
     * @return bow crash damage.
     */
    protected int getCrashDamage() {
        if (this.getHealth() <= 0) {
            return BROKEN_CRASH_DAMAGE;
        }
        return this.crashDamage;
    }

    /**
     * Getter for the damage multiplier of the ship bow.
     * 
     * @return bow damage multiplier.
     */
    protected int getCrashDamageMultiplier() {
        return this.crashDamageMultiplier;
    }

    /**
     * Factory method for the standard bow.
     * 
     * @return bow with his standard stats.
     */
    protected static Bow standard() {
        final int health = 100;
        final int crashDamage = 20;
        final int crashDamageMultiplier = 2;

        return new Bow(health, crashDamage, crashDamageMultiplier);
    }

    /**
     * Factory method for the heavy bow, a really strong bow
     * for crash damage, crash damage multiplier and health.
     * 
     * @return bow with his standard stats.
     */
    protected static Bow heavy() {
        final int health = 200;
        final int crashDamage = 40;
        final int crashDamageMultiplier = 3;

        return new Bow(health, crashDamage, crashDamageMultiplier);
    }

    /**
     * Factory method for the light bow, a really weak bow
     * for crash damage, crash damage multiplier and health.
     * 
     * @return bow with his standard stats.
     */
    protected static Bow light() {
        final int health = 75;
        final int crashDamage = 5;
        final int crashDamageMultiplier = 1;

        return new Bow(health, crashDamage, crashDamageMultiplier);
    }
}
