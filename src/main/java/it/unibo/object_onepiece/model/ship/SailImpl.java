package it.unibo.object_onepiece.model.ship;

public final class SailImpl extends ShipComponentImpl implements Sail {
    private final int maxSpeed;
    private final int minSpeed;
    private final int rotationPower;

    protected SailImpl(final int max, final int min, final int rotation, final int health) {
        super(health);
        this.maxSpeed = max;
        this.minSpeed = min;
        this.rotationPower = rotation;
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
