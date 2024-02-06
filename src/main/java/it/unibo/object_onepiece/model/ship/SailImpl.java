package it.unibo.object_onepiece.model.ship;

/**
 * Class that defines the implementation of the Sail interface
 */
public final class SailImpl extends ShipComponentImpl implements Sail {
    private final int maxSpeed;
    private final int minSpeed;
    private final int rotationPower;

    /**
     * Constructor for class SailImpl
     * 
     * @param  max      the maximum speed of the sail
     * @param  min      the minimum speed of the sail
     * @param  rotation the rotation power of the sail
     * @param  health   the health of the sail
     */
    protected SailImpl(final int max, final int min, final int rotation, final int health) {
        super(health);
        this.maxSpeed = max;
        this.minSpeed = min;
        this.rotationPower = rotation;
    }

     /**
     * Check if the ship can make a certain number of steps
     * checking if steps in beetween maxSpeed and minSpeed.
     * 
     * @param  steps the number of steps that the Ship should do
     * @return       boolean with the result of the check.
     */
    @Override
    public boolean isInSpeedRange(final int steps) {
        if ((steps > this.maxSpeed)
        || (steps < this.minSpeed)) {
            return false;
        }
        return true;
    }

    /**
     * Get method for the max speed of the ship sail.
     * 
     * @return sail max speed.
     */
    @Override
    public int getMaxSpeed() {
        return this.maxSpeed;
    }

    /**
     * Get method for the min speed of the ship sail.
     * 
     * @return sail min speed.
     */
    @Override
    public int getMinSpeed() {
        return this.minSpeed;
    }

    /**
     * Get method for the rotation power of the ship sail.
     * 
     * @return sail rotation power.
     */
    @Override
    public int getRotationPower() {
        return this.rotationPower;
    }
}
