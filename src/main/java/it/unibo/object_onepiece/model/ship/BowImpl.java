package it.unibo.object_onepiece.model.ship;

public final class BowImpl extends ShipComponentImpl implements Bow {
    private final int crashDamage;

    protected BowImpl(final int crashDamage, final int health) {
        super(health);
        this.crashDamage = crashDamage;
    }

    @Override
    public int getCrashDamage() {
        return this.crashDamage;
    }
}
