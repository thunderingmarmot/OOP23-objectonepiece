package it.unibo.object_onepiece.model;

/**
 * This class is extended by all the ship components.
 * It defines the ship components common methods, like setting
 * health and Ship, and getting health and maximum health.
 */
public class ShipComponent {
    private final Ship ship;
    private final int maxHealth;
    private int health;

    /**
     * Constructor for class ShipComponent.
     * 
     * @param  ship   the ship assigned to the component
     * @param  health the initial and maximum health of the component
     */
    protected ShipComponent(final Ship ship, final int health) {
        this.ship = ship;
        this.maxHealth = health;
        this.health = health;
    }

    /**
     * Setter for the health of the ShipComponent.
     * 
     * @param  health the health to set
    */
    protected void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Getter for the health of the ShipComponent.
     * 
     * @return the current health of the ShipComponent.
     */
    protected int getHealth() {
        return this.health;
    }

    /**
     * Getter for the maximum health of the ShipComponent.
     * 
     * @return the maximum health of the ShipComponent.
     */
    protected int getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * Getter for the Ship associated with the ShipComponent.
     * 
     * @return the Ship of the ShipComponent.
     */
    protected Ship getShip() {
        return this.ship;
    }
}
