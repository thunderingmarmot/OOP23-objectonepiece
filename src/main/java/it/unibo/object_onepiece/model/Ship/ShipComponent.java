package it.unibo.object_onepiece.model.Ship;

public interface ShipComponent {
    public void setHealth(int health);

    public Ship getShip();
    public int getHealth();
    public int getMaxHealth();
}
