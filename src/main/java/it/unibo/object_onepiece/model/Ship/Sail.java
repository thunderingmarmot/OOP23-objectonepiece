package it.unibo.object_onepiece.model.Ship;

public interface Sail extends ShipComponent {
    public boolean isInSpeedRange(int steps);

    public int getMaxSpeed();
    public int getMinSpeed();
    public int getRotationPower();

    public static Sail sloop() {
        return new SailImpl(1, 1, 0, 100);
    }

    public static Sail spinnaker() {
        return new SailImpl(3, 1, 3, 80);
    }

    public static Sail schooner() {
        return new SailImpl(5, 4, 1, 200);
    }
}
