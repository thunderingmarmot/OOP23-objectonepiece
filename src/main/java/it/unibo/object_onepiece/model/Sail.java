package it.unibo.object_onepiece.model;

public interface Sail extends ShipComponent {
    public Ship getShip();
    public int getMaxSpeed();
    public int getMinSpeed();
    public int getRotationPower();
}
