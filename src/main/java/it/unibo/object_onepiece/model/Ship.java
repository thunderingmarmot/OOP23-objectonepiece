package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.MoveReturnTypes;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * An animated entity, it can be the player or an enemy
 */
public interface Ship extends Entity, Crashable {
    public MoveReturnTypes move(Direction direction);

    public boolean shoot(Position position);
    public void takeDamage(int damage);

    public Weapon getWeapon();
    public int getHealth();
    public Direction getDirection();
}