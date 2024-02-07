package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

public final class NavalMineImpl extends Entity implements NavalMine {

    private final int damage;

    protected NavalMineImpl(final Section section, final Position position, final int damage) {
        super(section, position);
        this.damage = damage;
    }

    @Override
    public void onCollisionWith(final Collider collider) {
        if (collider instanceof Ship ship) {
            ship.takeDamage(this.damage, ship.getBow());
        }
        this.remove();
    }
}
