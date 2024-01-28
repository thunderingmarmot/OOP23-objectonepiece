package it.unibo.object_onepiece.model.Ship;
import it.unibo.object_onepiece.model.Collider;
import it.unibo.object_onepiece.model.Entity;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * An animated entity, it can be the player or an enemy
 */
public interface Ship extends Entity, Collider {
    public MoveReturnType move(Direction direction, Position nextPos);

    public void takeDamage(int damage);

    public void setWeapon(Weapon weapon);
    public void setSail(Sail sail);
    public void setBow(Bow bow);
    public void setHealth(int health);

    public Weapon getWeapon();
    public Sail getSail();
    public Bow getBow();
    public int getHealth();
    public Direction getDirection();
}