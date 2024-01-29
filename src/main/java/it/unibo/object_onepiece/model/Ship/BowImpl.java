package it.unibo.object_onepiece.model.Ship;

public class BowImpl extends ShipComponentImpl implements Bow {
    private final int crashDamage;

    public BowImpl(final int crashDamage, final Ship ship, final int health) {
        super(ship, health);
        this.crashDamage = crashDamage;
    }
    
    @Override
    public int getCrashDamage() {
        return this.crashDamage;
    }
}
