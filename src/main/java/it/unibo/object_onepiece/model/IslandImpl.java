package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

public class IslandImpl extends EntityImpl implements Island {

    private final int healthGiven;

    public IslandImpl(Section section, Position position, int healthGiven) {
        super(section, position);
        this.healthGiven = healthGiven;
    }

    @Override
    public void save() {
        this.getSection().getWorld().setSavedState();
    }

    @Override
    public void heal() {
        this.getSection().getPlayer().heal(healthGiven);
    }

    @Override
    public void interact() {
        heal();
        save();
    }
}
