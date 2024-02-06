package it.unibo.object_onepiece.model.ship;

public interface Sail extends ShipComponent {
    boolean isInSpeedRange(int steps);

    int getMaxSpeed();
    int getMinSpeed();
    int getRotationPower();

    static Sail sloop() {
        final int maxSpeed = 1;
        final int minSpeed = 1;
        final int rotationPower = 0;
        final int health = 100;

        return new SailImpl(maxSpeed, minSpeed, rotationPower, health);
    }

    static Sail spinnaker() {
        final int maxSpeed = 3;
        final int minSpeed = 1;
        final int rotationPower = 3;
        final int health = 80;

        return new SailImpl(maxSpeed, minSpeed, rotationPower, health);
    }

    static Sail schooner() {
        final int maxSpeed = 5;
        final int minSpeed = 4;
        final int rotationPower = 1;
        final int health = 200;

        return new SailImpl(maxSpeed, minSpeed, rotationPower, health);
    }
}
