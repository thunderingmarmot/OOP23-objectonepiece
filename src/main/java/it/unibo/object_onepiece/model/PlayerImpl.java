package it.unibo.object_onepiece.model;

public class PlayerImpl extends ShipImpl implements Player {

    private final int MAX_HEALTH = 100;

    private int experience;

    public PlayerImpl(Position position, Direction direction, int health, int experience) {
        super(position, direction, health);
        this.experience = experience;
    }

    @Override
    public int getExperience() {
        return experience;
    }

    @Override
    public void addExperience(int experience) {
        this.experience += experience;
    }

    @Override
    public void interact() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'interact'");
    }

    @Override
    public void heal(int healthGained) {
        int nextHealth = this.health + healthGained;
        this.health = nextHealth < MAX_HEALTH ? nextHealth : MAX_HEALTH;
    }
    
}
