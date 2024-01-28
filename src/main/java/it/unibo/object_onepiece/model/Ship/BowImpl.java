package it.unibo.object_onepiece.model.Ship;

public class BowImpl implements Bow {
    private final int crashDamage;
    private final Ship ship;
    private int health;

    public BowImpl(final int crashDamage, final Ship ship, final int health) {
        this.crashDamage = crashDamage;
        this.ship = ship;
        this.health = health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getCrashDamage() {
        return this.crashDamage;
    }

    @Override
    public Ship getShip() {
        return this.ship;
    }

    @Override
    public int getHealth() {
        return this.health;
    }
}
