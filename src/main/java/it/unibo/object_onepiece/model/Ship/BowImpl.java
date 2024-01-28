package it.unibo.object_onepiece.model.Ship;

public class BowImpl implements Bow {
    private final int crashDamage;
    private final Ship ship;

    public BowImpl(final int crashDamage, final Ship ship) {
        this.crashDamage = crashDamage;
        this.ship = ship;
    }

    @Override
    public int getCrashDamage() {
        return this.crashDamage;
    }

    @Override
    public Ship getShip() {
        return this.ship;
    }
}
