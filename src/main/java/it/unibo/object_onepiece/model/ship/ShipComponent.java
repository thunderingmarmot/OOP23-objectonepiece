package it.unibo.object_onepiece.model.ship;

public interface ShipComponent {
    void setHealth(int health);
    void setShip(Ship ship);

    Ship getShip();
    int getHealth();
    int getMaxHealth();
}
