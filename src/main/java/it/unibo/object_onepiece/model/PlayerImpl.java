package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Ship.ShipImpl;
import it.unibo.object_onepiece.model.Ship.Weapon;
import it.unibo.object_onepiece.model.Ship.Sail;
import it.unibo.object_onepiece.model.Ship.Bow;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

public class PlayerImpl extends ShipImpl implements Player {

    private int experience;

    protected PlayerImpl(Section section, Position position, Direction direction, Weapon weapon, Sail sail, Bow bow, int experience) {
        super(section, position, direction, weapon, sail, bow);
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
    public Type getViewType() {
        return Type.PLAYER;
    }
}
