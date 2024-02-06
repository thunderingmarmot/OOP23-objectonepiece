package it.unibo.object_onepiece.model.ship;

/**
 * This interface represents a bow, which is a type of ShipComponent.
 * It defines a method to retrieving crash damage of the bow.
 * It also defines builder methods and builder methods for the available bows.
 */
public interface Bow extends ShipComponent {
    /**
     * Get method for the crash damage of the ship bow.
     * 
     * @return bow crash damage.
     */
    int getCrashDamage();

    /**
     * Builder for the standard bow.
     * 
     * @return bow with his standard stats.
     */
    static Bow standard() {
        final int crashDamage = 20;
        final int health = 100;

        return new BowImpl(crashDamage, health);
    }

    /**
     * Builder for the heavy bow, a really strong bow
     * for crash damage and health.
     * 
     * @return bow with his standard stats.
     */
    static Bow heavy() {
        final int crashDamage = 40;
        final int health = 200;

        return new BowImpl(crashDamage, health);
    }

    /**
     * Builder for the light bow, a really weak bow
     * for crash damage and health.
     * 
     * @return bow with his standard stats.
     */
    static Bow light() {
        final int crashDamage = 5;
        final int health = 75;

        return new BowImpl(crashDamage, health);
    }
}
