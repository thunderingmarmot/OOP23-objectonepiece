package it.unibo.object_onepiece.model.Ship;

public class SailImpl implements Sail {
    private final int maxSpeed;
    private final int minSpeed;
    private final int rotationPower;
    private final Ship ship;
    private int health;

    public SailImpl(final int max, final int min, final int rotation, final Ship ship, final int health) {
        this.maxSpeed = max;
        this.minSpeed = min;
        this.rotationPower = rotation;
        this.ship = ship;
        this.health = health;
    }

    @Override
    public boolean isInSpeedRange(final int steps) {
        if((steps > this.maxSpeed) ||
        (steps < this.minSpeed)) {
            return false;
        }
        return true;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public Ship getShip() {
        return this.ship;
    }

    @Override
    public int getMaxSpeed() {
        return this.maxSpeed;
    }

    @Override
    public int getMinSpeed() {
        return this.minSpeed;
    }

    @Override
    public int getRotationPower() {
        return this.rotationPower;
    }

    @Override
    public int getHealth() {
        return this.health;
    }
}
