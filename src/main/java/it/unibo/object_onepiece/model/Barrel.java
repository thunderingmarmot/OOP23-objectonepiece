package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

/**
 * The barrels are collectable entities for the user
 */
public interface Barrel extends Viewable, Collidable {
    public static Barrel getDefault(Section spawnSection, Position spawnPosition) {
        return new BarrelImpl(spawnSection, spawnPosition, 50);
    }

    public void take(Player player);

    @Override
    public default Rigidness getRigidness() {
        return Rigidness.SOFT;
    }

    @Override
    public default Type getViewType() {
        return Type.BARREL;
    }
}
