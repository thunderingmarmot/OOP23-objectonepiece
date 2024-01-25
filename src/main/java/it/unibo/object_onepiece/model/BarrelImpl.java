package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

public class BarrelImpl extends EntityImpl implements Barrel {

    private final int experienceGiven;

    public BarrelImpl(Position position, int experienceGiven) {
        super(position);
        this.experienceGiven = experienceGiven;
    }

    @Override
    public void take(Player player) {
        player.addExperience(experienceGiven);
    }
    
}
