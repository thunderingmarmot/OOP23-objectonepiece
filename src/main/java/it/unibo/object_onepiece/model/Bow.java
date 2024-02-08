package it.unibo.object_onepiece.model;

/**
 * This class represents a bow, which is a type of ShipComponent.
 * It defines a method to retrieving crash damage of the bow.
 * It also defines builder methods and builder methods for the available bows.
 * 
 * @see ShipComponent
 */
public final class Bow extends ShipComponent {
    private final int crashDamage;

    /**
     * Constructor for class Bow.
     * 
     * @param  crashDamage the crash damage of the bow
     * @param  health      the health of the bow
     */
    protected Bow(final int crashDamage, final int health) {
        super(health);
        this.crashDamage = crashDamage;
    }

    /**
     * Getter for the crash damage of the ship bow.
     * 
     * @return bow crash damage.
     */
    protected int getCrashDamage() {
        return this.crashDamage;
    }

    /**
     * Builder for the standard bow.
     * 
     * @return      bow with his standard stats.
     */
    protected static Bow standard() {
        final int crashDamage = 20;
        final int health = 100;

        return new Bow(crashDamage, health);
    }

    /**
     * Builder for the heavy bow, a really strong bow
     * for crash damage and health.
     * 
     * @return      bow with his standard stats.
     */
    protected static Bow heavy() {
        final int crashDamage = 40;
        final int health = 200;

        return new Bow(crashDamage, health);
    }

    /**
     * Builder for the light bow, a really weak bow
     * for crash damage and health.
     * 
     * @return      bow with his standard stats.
     */
    protected static Bow light() {
        final int crashDamage = 5;
        final int health = 75;

        return new Bow(crashDamage, health);
    }
}
