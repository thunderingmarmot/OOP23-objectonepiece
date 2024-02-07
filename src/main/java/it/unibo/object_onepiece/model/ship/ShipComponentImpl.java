package it.unibo.object_onepiece.model.ship;

/**
 * Abstract class that defines the implementation of the ShipComponent interface.
 */
public abstract class ShipComponentImpl implements ShipComponent {
    private final Ship ship;
    private final int maxHealth;
    private int health;

    /**
     * Constructor for class ShipComponentImpl.
     * 
     * @param  ship   the ship assigned to the component
     * @param  health the initial and maximum health of the component
     */
    protected ShipComponentImpl(final Ship ship, final int health) {
        this.ship = ship;
        this.maxHealth = health;
        this.health = health;
    }

    /**
     * Setter for the health of the ShipComponent.
     * 
     * @param  health the health to set
    */
    @Override
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Getter for the health of the ShipComponent.
     * 
     * @return the current health of the ShipComponent.
     */
    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * Getter for the maximum health of the ShipComponent.
     * 
     * @return the maximum health of the ShipComponent.
     */
    @Override
    public int getMaxHealth() {
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
