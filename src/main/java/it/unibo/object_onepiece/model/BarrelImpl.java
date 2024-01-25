package it.unibo.object_onepiece.model;

public class BarrelImpl extends EntityImpl implements Barrel {

    private final int experience;

    public BarrelImpl(int experience) {
        this.experience = experience;
    }

    @Override
    public void take(Player player) {
        player.addExperience(experience);
    }
    
}
