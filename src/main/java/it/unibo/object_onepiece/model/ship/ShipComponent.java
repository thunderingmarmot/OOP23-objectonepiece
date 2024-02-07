package it.unibo.object_onepiece.model.ship;

/**
 * This interface is implemented by all the ship components.
 * It defines the ship components common methods, like setting
 * health and Ship, and getting health and maximum health.
 */
public interface ShipComponent {
    /**
     * Setter for the health of the ShipComponent.
     * 
     * @param  health the health to set
    */
    void setHealth(int health);

    /**
     * Getter for the health of the ShipComponent.
     * 
     * @return the current health of the ShipComponent.
     */
    int getHealth();

    /**
     * Getter for the maximum health of the ShipComponent.
     * 
     * @return the maximum health of the ShipComponent.
     */
    int getMaxHealth();
}
