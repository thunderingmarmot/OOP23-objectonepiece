package it.unibo.object_onepiece.model.Ship;

public interface Sail extends ShipComponent {
    public boolean isInSpeedRange(int steps);

    public int getMaxSpeed();
    public int getMinSpeed();
    public int getRotationPower();
}
