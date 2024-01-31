package it.unibo.object_onepiece.model.Ship;

public abstract class ShipComponentImpl implements ShipComponent {
    private Ship ship;
    private final int maxHealth;
    private int health;

    protected ShipComponentImpl(final int health) {
        this.maxHealth = health;
        this.health = health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void setShip(Ship ship) {
        this.ship = ship;
    }
    
    @Override
    public int getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public int getHealth() {
        return this.health;
    }


    @Override
    public Ship getShip() {
        return this.ship;
    }
}
