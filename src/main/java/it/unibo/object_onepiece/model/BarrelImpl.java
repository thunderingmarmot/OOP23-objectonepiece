package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

public class BarrelImpl extends EntityImpl implements Barrel {

    private final int experienceGiven;

    public BarrelImpl(Section section, Position position, int experienceGiven) {
        super(section, position);
        this.experienceGiven = experienceGiven;
    }

    @Override
    public void take(Player player) {
        player.addExperience(experienceGiven);
        this.remove();
    }

    @Override
    public void crash(Crashable c) {
        if(c instanceof Player player) {
            take(player);
        }
    }

    @Override
    public int getCrashDamage() {
        return 0;
    }
}
