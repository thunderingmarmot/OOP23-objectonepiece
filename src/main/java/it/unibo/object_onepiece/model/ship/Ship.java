package it.unibo.object_onepiece.model.ship;
import it.unibo.object_onepiece.model.Collider;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.BiArgument;
import it.unibo.object_onepiece.model.events.EventArgs.Argument;

/**
 * This interface represents a ship entity that extends the Collider interface.
 * It defines methods for taking damage, setting and retrieving weapons, sails, bows, and direction.
 * It also provides access to events related to changes in direction and damage taken.
 */
public interface Ship extends Collider {
    void takeDamage(int damage, ShipComponent s);

    void setWeapon(Weapon weapon);
    void setSail(Sail sail);
    void setBow(Bow bow);

    Weapon getWeapon();
    Sail getSail();
    Bow getBow();
    Direction getDirection();
    
    Event<BiArgument<Direction>> getDirectionChangedEvent();
    Event<Argument<Integer>> getTookDamageEvent();

    @Override
    default Rigidness getRigidness() {
        return Rigidness.MEDIUM;
    }
}
