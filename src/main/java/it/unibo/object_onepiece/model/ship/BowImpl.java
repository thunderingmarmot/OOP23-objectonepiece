package it.unibo.object_onepiece.model.ship;

/**
 * Class that defines the implementation of the Bow interface.
 */
public final class BowImpl extends ShipComponentImpl implements Bow {
    private final int crashDamage;

    /**
     * Constructor for class BowImpl.
     * 
     * @param  crashDamage the crash damage of the bow
     * @param  health      the health of the bow
     */
    protected BowImpl(final int crashDamage, final int health) {
        super(health);
        this.crashDamage = crashDamage;
    }

    /**
     * Getter for the crash damage of the ship bow.
     * 
     * @return bow crash damage.
     */
    @Override
    public int getCrashDamage() {
        return this.crashDamage;
    }
}
