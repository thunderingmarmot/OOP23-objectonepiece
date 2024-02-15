package it.unibo.object_onepiece.model;

/**
 * This class is extended by all the ship components.
 * It defines the ship components common methods, like setting
 * and getting health and maximum health.
 */
public abstract class ShipComponent {
    private final int maxHealth;
    private int health;

    /**
     * Constructor for class ShipComponent.
     * 
     * @param  health the initial and maximum health of the component
     */
    protected ShipComponent(final int health) {
        this.maxHealth = health;
        this.health = health;
    }

    /**
     * Constructor to copy from an existing ShipComponent.
     * 
     * @param  origin the ship component to copy from
     */
    protected ShipComponent(final ShipComponent origin) {
        this.maxHealth = origin.getMaxHealth();
        this.health = origin.getHealth();
    }

    /**
     * Ovveride to define the behaviour of copy.
     * 
     * @return a ShipComponent or something that extends it.
     */
    protected abstract ShipComponent copy();

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
}
