package it.unibo.object_onepiece.model.Ship;
import it.unibo.object_onepiece.model.Collider;
import it.unibo.object_onepiece.model.Entity;
import it.unibo.object_onepiece.model.Utils.Direction;

/**
 * An animated entity, it can be the player or an enemy
 */
public interface Ship extends Entity, Collider {
    public void takeDamage(int damage, ShipComponent s);

    public void setWeapon(Weapon weapon);
    public void setSail(Sail sail);
    public void setBow(Bow bow);
    public void setTotalHealth(int health);

    public Weapon getWeapon();
    public Sail getSail();
    public Bow getBow();
    public Direction getDirection();
}