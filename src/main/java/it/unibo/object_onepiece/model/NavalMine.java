package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

public interface NavalMine extends Collidable {
    public static NavalMine getDefault(Section spawnSection, Position spawnPosition) {
        return new NavalMineImpl(spawnSection, spawnPosition, 50);
    }

    @Override
    public default Rigidness getRigidness() {
        return Rigidness.SOFT;
    }
}
