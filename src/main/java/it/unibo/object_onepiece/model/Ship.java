package it.unibo.object_onepiece.model;
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
    public void setHealth(int health);

    public Weapon getWeapon();
    public Sail getSail();
    public int getHealth();
    public Direction getDirection();
}