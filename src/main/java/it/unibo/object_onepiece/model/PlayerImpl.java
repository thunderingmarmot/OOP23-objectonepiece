package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventImpl;
import it.unibo.object_onepiece.model.events.EventArgs.ValueChanged;
import it.unibo.object_onepiece.model.ship.Bow;
import it.unibo.object_onepiece.model.ship.Sail;
import it.unibo.object_onepiece.model.ship.ShipImpl;
import it.unibo.object_onepiece.model.ship.Weapon;

public class PlayerImpl extends ShipImpl implements Player {

    private int experience;

    public final Event<ValueChanged<Integer>> onExperienceAdded = new EventImpl<>();

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
        onExperienceAdded.invoke(new ValueChanged<Integer>(this.experience, experience));
        this.experience += experience;
    }
}
