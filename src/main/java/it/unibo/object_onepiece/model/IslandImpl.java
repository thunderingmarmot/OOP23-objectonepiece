package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.Utils.State;

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
    public Runnable getInteraction() {
        return this::heal;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ISLAND;
    }
    
}
