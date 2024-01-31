package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

public class IslandImpl extends EntityImpl implements Island {

    public IslandImpl(Section section, Position position) {
        super(section, position);
    }

    @Override
    public void save() {
        this.getSection().getWorld().setSavedState();
    }

    @Override
    public void heal(Player player) {
        player.getWeapon().setHealth(player.getWeapon().getMaxHealth());
        player.getSail().setHealth(player.getSail().getMaxHealth());
        player.getBow().setHealth(player.getBow().getMaxHealth());
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

    @Override
    public Type getViewType() {
        return Type.ISLAND;
    }
}
