package it.unibo.object_onepiece.model;

/**
 * This class represents a sail, which is a type of ShipComponent.
 * It defines a method for determining if the Ship can make a certain
 * number of steps according to its sail stats.
 * It also defines methods to retrieving maximum speed, minimum speed
 * and rotation power of the sail, and builder methods for the available sails.
 * 
 * @see ShipComponent
 */
public final class Sail extends ShipComponent {
    private final int maxSpeed;
    private final int minSpeed;
    private final boolean rotationPower;

    /**
     * Constructor for class Sail.
     * 
     * @param  health   the health of the sail
     * @param  max      the maximum speed of the sail
     * @param  min      the minimum speed of the sail
     * @param  rotation the rotation power of the sail
     */
    protected Sail(final int health, final int max, final int min, final boolean rotation) {
        super(health);
        this.maxSpeed = max;
        this.minSpeed = min;
        this.rotationPower = rotation;
    }

    protected Sail(final Sail origin) {
        super(origin);
        this.maxSpeed = origin.getMaxSpeed();
        this.minSpeed = origin.getMinSpeed();
        this.rotationPower = origin.haveRotationPower();
    }

    @Override
    protected Sail copy() {
        return new Sail(this);
    }

     /**
     * Check if the ship can make a certain number of steps
     * checking if steps in beetween maxSpeed and minSpeed.
     * 
     * @param  steps the number of steps that the Ship should do
     * @return       boolean with the result of the check.
     */
    protected boolean isInSpeedRange(final int steps) {
        return steps <= this.maxSpeed && steps >= this.minSpeed;
    }

    /**
     * Getter for the max speed of the ship sail.
     * 
     * @return sail max speed.
     */
    protected int getMaxSpeed() {
        return this.maxSpeed;
    }

    /**
     * Getter for the min speed of the ship sail.
     * 
     * @return sail min speed.
     */
    protected int getMinSpeed() {
        return this.minSpeed;
    }

    /**
     * Getter for the rotation power of the ship sail.
     * 
     * @return sail rotation power.
     */
    protected boolean haveRotationPower() {
        return this.rotationPower;
    }

    /**
     * Builder for the sloop sail, the standard type of sail.
     * 
     * @return sail with his standard stats.
     */
    protected static Sail sloop() {
        final int health = 100;
        final int maxSpeed = 1;
        final int minSpeed = 1;
        final boolean rotationPower = false;

        return new Sail(health, maxSpeed, minSpeed, rotationPower);
    }

    /**
     * Builder for the spinnaker sail, a very fast and agile sail 
     * but more fragile than the standard one.
     * 
     * @return sail with his standard stats.
     */
    protected static Sail spinnaker() {
        final int health = 80;
        final int maxSpeed = 3;
        final int minSpeed = 1;
        final boolean rotationPower = true;

        return new Sail(health, maxSpeed, minSpeed, rotationPower);
    }

    /**
     * Builder for the schooner sail, the best sail for speed and health.
     * 
     * @return sail with his standard stats.
     */
    protected static Sail schooner() {
        final int health = 200;
        final int maxSpeed = 5;
        final int minSpeed = 4;
        final boolean rotationPower = true;

        return new Sail(health, maxSpeed, minSpeed, rotationPower);
    }
}
