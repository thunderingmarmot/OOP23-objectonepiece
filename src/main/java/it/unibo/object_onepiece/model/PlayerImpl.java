package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Ship.Sail;
import it.unibo.object_onepiece.model.Ship.ShipImpl;
import it.unibo.object_onepiece.model.Ship.Weapon;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

public class PlayerImpl extends ShipImpl implements Player {

    private final int MAX_HEALTH = 100;

    private int experience;

    public PlayerImpl(Section section, Position position, Direction direction, int health, Weapon weapon, Sail sail, int crashDamage, int experience) {
        super(section, position, direction, health, weapon, sail, crashDamage);
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
    public void addHealth(int healthGained) {
        int nextHealth = this.getHealth() + healthGained;
        this.setHealth(nextHealth < MAX_HEALTH ? nextHealth : MAX_HEALTH);
    }
}
