package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.ship.Bow;
import it.unibo.object_onepiece.model.ship.Sail;
import it.unibo.object_onepiece.model.ship.ShipImpl;
import it.unibo.object_onepiece.model.ship.Weapon;

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
