package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Ship.Ship;
import it.unibo.object_onepiece.model.Utils.Position;

public class NavalMineImpl extends EntityImpl implements Barrel {

    private final int damage;

    public NavalMineImpl(Section section, Position position, int damage) {
        super(section, position);
        this.damage = damage;
    }

    @Override
    public void take(Ship ship) {
        ship.takeDamage(this.damage, ship.getBow());
    }

    @Override
    public void onCollisionWith(Collider collider) {
        if(collider instanceof Ship ship) {
            take(ship);
        }
        this.remove();
    }

    @Override
    public Rigidness getRigidness() {
        return Rigidness.SOFT;
    }
}
