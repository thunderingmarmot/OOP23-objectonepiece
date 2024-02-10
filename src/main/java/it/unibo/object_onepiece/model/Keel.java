package it.unibo.object_onepiece.model;

public class Keel extends ShipComponent {
    private final int damageEndurance;

    protected Keel(final int health, final int damageEndurance) {
        super(health);
        this.damageEndurance = damageEndurance;
    }

    protected boolean isKeelDamaged() {
        return this.getHealth() < (this.getMaxHealth() / this.damageEndurance);
    }

    /**
     * Getter for the damage endurance of the ship keel.
     * 
     * @return keel damage endurance.
     */
    protected int getDamageEndurance() {
        return this.damageEndurance;
    }

    /**
     * Builder for the standard keel.
     * 
     * @return      keel with his standard stats.
     */
    protected static Keel standard() {
        final int health = 100;
        final int damageEndurance = 2;

        return new Keel(health, damageEndurance);
    }

    /**
     * Builder for the heavy bow, a really strong bow
     * for crash damage and health.
     * 
     * @return      bow with his standard stats.
     */
    protected static Keel heavy() {
        final int health = 120;
        final int damageEndurance = 4;

        return new Keel(health, damageEndurance);
    }

    /**
     * Builder for the light bow, a really weak bow
     * for crash damage and health.
     * 
     * @return      bow with his standard stats.
     */
    protected static Keel light() {
        final int health = 80;
        final int damageEndurance = 1;

        return new Keel(health, damageEndurance);
    }
}
