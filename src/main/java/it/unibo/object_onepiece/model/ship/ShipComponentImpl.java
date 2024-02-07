package it.unibo.object_onepiece.model.ship;

/**
 * Abstract class that defines the implementation of the ShipComponent interface.
 */
public abstract class ShipComponentImpl implements ShipComponent {
    private Ship ship;
    private final int maxHealth;
    private int health;

    /**
     * Constructor for class ShipComponentImpl.
     * 
     * @param  health the initial and maximum health of the component
     */
    protected ShipComponentImpl(final int health) {
        this.maxHealth = health;
        this.health = health;
    }

    /**
     * Setter for the health of the ShipComponent.
     * 
     * @param  health the health to set
    */
    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Setter for the Ship of the ShipComponent.
     * 
     * @param  ship the Ship to set
    */
    @Override
    public void setShip(Ship ship) {
        this.ship = ship;
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
