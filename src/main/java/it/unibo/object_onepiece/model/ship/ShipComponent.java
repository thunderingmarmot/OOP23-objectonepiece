package it.unibo.object_onepiece.model.ship;

/**
 * This interface is implemented by all the ship components.
 * It defines the ship components common methods, like setting
 * health and Ship, and getting health and maximum health.
 */
public interface ShipComponent {
    /**
     * Set method for the health of the ShipComponent.
     * 
     * @param  health the health to set
    */
    void setHealth(int health);

    /**
     * Set method for the Ship of the ShipComponent.
     * 
     * @param  ship the Ship to set
    */
    void setShip(Ship ship);

    /**
     * Get method for the health of the ShipComponent.
     * 
     * @return the current health of the ShipComponent.
     */
    int getHealth();

    /**
     * Get method for the maximum health of the ShipComponent.
     * 
     * @return the maximum health of the ShipComponent.
     */
    int getMaxHealth();
}
