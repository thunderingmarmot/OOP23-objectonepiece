package it.unibo.object_onepiece.model.ship;
import it.unibo.object_onepiece.model.Collider;
import it.unibo.object_onepiece.model.Utils.Direction;

/**
 * An animated entity, it can be the player or an enemy
 */
public interface Ship extends Collider {
    public void takeDamage(int damage, ShipComponent s);

    public void setWeapon(Weapon weapon);
    public void setSail(Sail sail);
    public void setBow(Bow bow);

    public Weapon getWeapon();
    public Sail getSail();
    public Bow getBow();
    public Direction getDirection();

    @Override
    public default Rigidness getRigidness() {
        return Rigidness.MEDIUM;
    }
}