package it.unibo.object_onepiece.model;

public class SailImpl implements Sail {
    private final int maxSpeed;
    private final int minSpeed;
    private final int rotationPower;
    private final Ship ship;

    public SailImpl(final int max, final int min, final int rotation, final Ship ship) {
        this.maxSpeed = max;
        this.minSpeed = min;
        this.rotationPower = rotation;
        this.ship = ship;
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
}
