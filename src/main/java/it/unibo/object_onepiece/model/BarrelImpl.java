package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

public class BarrelImpl extends EntityImpl implements Barrel {

    private final int experienceGiven;

    public BarrelImpl(Section section, Position position, int experienceGiven) {
        super(section, position);
        this.experienceGiven = experienceGiven;
    }

    @Override
    public void take() {
        this.getSection().getPlayer().addExperience(experienceGiven);
    }

    @Override
    public Runnable getInteraction() {
        return this::take;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.BARREL;
    }
    
}
