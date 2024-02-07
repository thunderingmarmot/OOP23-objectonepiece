package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;


public interface NavalMine extends Collidable {
    int DEFAULT_DAMAGE = 50;

    static NavalMineImpl getDefault(Section spawnSection, Position spawnPosition) {
        return new NavalMineImpl(spawnSection, spawnPosition, DEFAULT_DAMAGE);
    }

    @Override
    default Rigidness getRigidness() {
        return Rigidness.SOFT;
    }
}
