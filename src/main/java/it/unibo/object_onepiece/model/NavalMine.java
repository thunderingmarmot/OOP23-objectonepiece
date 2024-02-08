package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

public final class NavalMine extends Collidable {
    private final int damage;
    private final static int DEFAULT_DAMAGE = 50;

    protected NavalMine(final Section section, final Position position, final int damage) {
        super(section, position);
        this.damage = damage;
    }

    protected static NavalMine getDefault(Section spawnSection, Position spawnPosition) {
        return new NavalMine(spawnSection, spawnPosition, DEFAULT_DAMAGE);
    }

    @Override
    protected Rigidness getRigidness() {
        return Rigidness.SOFT;
    }

    @Override
    protected void onCollisionWith(final Collider collider) {
        if (collider instanceof Ship ship) {
            ship.takeDamage(this.damage, ship.getBow());
        }
        this.remove();
    }
}
