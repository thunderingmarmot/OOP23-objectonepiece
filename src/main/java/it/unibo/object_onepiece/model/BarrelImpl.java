package it.unibo.object_onepiece.model;

public class BarrelImpl extends EntityImpl implements Barrel {

    private final int experienceGiven;

    public BarrelImpl(int experienceGiven) {
        this.experienceGiven = experienceGiven;
    }

    @Override
    public void take(Player player) {
        player.addExperience(experienceGiven);
    }
    
}
