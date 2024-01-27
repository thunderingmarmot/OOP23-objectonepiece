package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

public class PlayerImpl extends ShipImpl implements Player {

    private final int MAX_HEALTH = 100;

    private int experience;

    public PlayerImpl(Section section, Position position, Direction direction, int health, Weapon weapon, int experience) {
        super(section, position, direction, health, weapon);
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
    public void interactWith(Interactable interactable) {
        interactable.interact();
    }

    @Override
    public void heal(int healthGained) {
        int nextHealth = this.getHealth() + healthGained;
        this.setHealth(nextHealth < MAX_HEALTH ? nextHealth : MAX_HEALTH);
    }
}
