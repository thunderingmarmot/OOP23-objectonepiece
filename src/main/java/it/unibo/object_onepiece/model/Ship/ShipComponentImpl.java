package it.unibo.object_onepiece.model.Ship;

public abstract class ShipComponentImpl implements ShipComponent {
    private final Ship ship;
    private final int maxHealth;
    private int health;

    public ShipComponentImpl(final Ship ship, final int health) {
        this.ship = ship;
        this.maxHealth = health;
        this.health = health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
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