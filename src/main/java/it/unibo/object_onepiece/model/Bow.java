package it.unibo.object_onepiece.model;

/**
 * This class represents a bow, which is a type of ShipComponent.
 * It defines a method to retrieving crash damage of the bow.
 * It also defines builder methods and builder methods for the available bows.
 */
public final class Bow extends ShipComponent {
    private final int crashDamage;

    /**
     * Constructor for class Bow.
     * 
     * @param  crashDamage the crash damage of the bow
     * @param  ship        the ship where the bow is mounted
     * @param  health      the health of the bow
     */
    protected Bow(final int crashDamage, final ShipImpl ship, final int health) {
        super(ship, health);
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
     * @param  ship the ship that should be assigned to the bow
     * @return      bow with his standard stats.
     */
    protected static Bow standard(final ShipImpl ship) {
        final int crashDamage = 20;
        final int health = 100;

        return new Bow(crashDamage, ship, health);
    }

    /**
     * Builder for the heavy bow, a really strong bow
     * for crash damage and health.
     * 
     * @param  ship the ship that should be assigned to the bow
     * @return      bow with his standard stats.
     */
    protected static Bow heavy(final ShipImpl ship) {
        final int crashDamage = 40;
        final int health = 200;

        return new Bow(crashDamage, ship, health);
    }

    /**
     * Builder for the light bow, a really weak bow
     * for crash damage and health.
     * 
     * @param  ship the ship that should be assigned to the bow
     * @return      bow with his standard stats.
     */
    protected static Bow light(final ShipImpl ship) {
        final int crashDamage = 5;
        final int health = 75;

        return new Bow(crashDamage, ship, health);
    }
}
