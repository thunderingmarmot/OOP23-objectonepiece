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
    public void heal(Player player) {
        player.addHealth(healthGiven);
    }

    @Override
    public void onCollisionWith(Collider collider) {
        if(collider instanceof Player player) {
            heal(player);
            save();
        }
    }

    @Override
    public Rigidness getRigidness() {
        return Rigidness.HARD;
    }
}
