package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;


public interface NavalMine extends Collidable {
    static final int defaultDamage = 50;

    static NavalMine getDefault(Section spawnSection, Position spawnPosition) {
        return new NavalMineImpl(spawnSection, spawnPosition, defaultDamage);
    }

    @Override
    default Rigidness getRigidness() {
        return Rigidness.SOFT;
    }
}
