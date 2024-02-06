package it.unibo.object_onepiece.model.ship;

/**
 * This interface represents a sail, which is a type of ShipComponent.
 * It defines a method for determining if the Ship can make a certain
 * number of steps according to its sail stats.
 * It also defines methods to retrieving maximum speed, minimum speed
 * and rotation power of the sail.
 */
public interface Sail extends ShipComponent {
    /**
     * Check if the ship can make a certain number of steps 
     * according to its sail stats.
     * 
     * @param  steps the number of steps that the Ship should do
     * @return       boolean with the result of the check.
     */
    boolean isInSpeedRange(int steps);

    /**
     * Get method for the max speed of the ship sail.
     * 
     * @return sail max speed.
     */
    int getMaxSpeed();

    /**
     * Get method for the min speed of the ship sail.
     * 
     * @return sail min speed.
     */
    int getMinSpeed();

    /**
     * Get method for the rotation power of the ship sail.
     * 
     * @return sail rotation speed.
     */
    int getRotationPower();

    /**
     * Builder for the sloop sail, the standard type of sail.
     * 
     * @return sail with his standard stats.
     */
    static Sail sloop() {
        final int maxSpeed = 1;
        final int minSpeed = 1;
        final int rotationPower = 0;
        final int health = 100;

        return new SailImpl(maxSpeed, minSpeed, rotationPower, health);
    }

    /**
     * Builder for the spinnaker sail, a very fast and agile sail 
     * but more fragile than the standard one.
     * 
     * @return sail with his standard stats.
     */
    static Sail spinnaker() {
        final int maxSpeed = 3;
        final int minSpeed = 1;
        final int rotationPower = 3;
        final int health = 80;

        return new SailImpl(maxSpeed, minSpeed, rotationPower, health);
    }

    /**
     * Builder for the schooner sail, the best sail for speed and health.
     * 
     * @return sail with his standard stats.
     */
    static Sail schooner() {
        final int maxSpeed = 5;
        final int minSpeed = 4;
        final int rotationPower = 1;
        final int health = 200;

        return new SailImpl(maxSpeed, minSpeed, rotationPower, health);
    }
}
