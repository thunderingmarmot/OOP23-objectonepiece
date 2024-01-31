package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.ship.Ship;

public class NavalMineImpl extends EntityImpl implements NavalMine {

    private final int damage;

    protected NavalMineImpl(Section section, Position position, int damage) {
        super(section, position);
        this.damage = damage;
    }

    @Override
    public void onCollisionWith(Collider collider) {
        if(collider instanceof Ship ship) {
            ship.takeDamage(this.damage, ship.getBow());
        }
        this.remove();
    }
}
