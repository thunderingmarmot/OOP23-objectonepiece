package it.unibo.object_onepiece.model.ship;

public interface Sail extends ShipComponent {
    public boolean isInSpeedRange(int steps);

    public int getMaxSpeed();
    public int getMinSpeed();
    public int getRotationPower();

    public static Sail sloop() {
        final int maxSpeed = 1;
        final int minSpeed = 1;
        final int rotationPower = 0;
        final int health = 100;

        return new SailImpl(maxSpeed, minSpeed, rotationPower, health);
    }

    public static Sail spinnaker() {
        final int maxSpeed = 3;
        final int minSpeed = 1;
        final int rotationPower = 3;
        final int health = 80;

        return new SailImpl(maxSpeed, minSpeed, rotationPower, health);
    }

    public static Sail schooner() {
        final int maxSpeed = 5;
        final int minSpeed = 4;
        final int rotationPower = 1;
        final int health = 200;

        return new SailImpl(maxSpeed, minSpeed, rotationPower, health);
    }
}
