package it.unibo.object_onepiece.model.ship;
import it.unibo.object_onepiece.model.Collider;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.BiArgument;
import it.unibo.object_onepiece.model.events.EventArgs.Argument;

/**
 * This interface represents a Ship Entity that extends the Collider interface.
 * It defines methods for taking damage, setting and retrieving weapons, sails, bows, and direction.
 * It also provides access to events related to changes in direction and damage taken.
 */
public interface Ship extends Collider {
    /**
     * Method to inflict damage to the ship.
     * 
     * @param  damage the amout of damage to be inflicted
     * @param  s      the ShipComponent that needs to be hit
     */
    void takeDamage(int damage, ShipComponent s);

    /**
     * Set method for the Weapon component of the Ship.
     * 
     * @param  weapon the Weapon object to set
    */
    void setWeapon(Weapon weapon);

    /**
     * Set method for the Sail component of the Ship.
     * 
     * @param  sail the Sail object to set
    */
    void setSail(Sail sail);

    /**
     * Set method for the Bow component of the Ship.
     * 
     * @param  bow the Bow object to set
    */
    void setBow(Bow bow);

    /**
     * Get method for the Weapon component of the Ship.
     * 
     * @return the current Weapon mounted on the Ship.
     */
    Weapon getWeapon();

    /**
     * Get method for the Sail component of the Ship.
     * 
     * @return the current Sail mounted on the Ship.
     */
    Sail getSail();

    /**
     * Get method for the Bow component of the Ship.
     * 
     * @return the current Bow mounted on the Ship.
     */
    Bow getBow();

    /**
     * Get method for the current direction of the Ship.
     * 
     * @return the current dirrection on the Ship.
     */
    CardinalDirection getDirection();

    /**
     * Get method for the Event on direction changed.
     * 
     * @return Event of direction changed.
     */
    Event<BiArgument<CardinalDirection>> getDirectionChangedEvent();

    /**
     * Get method for the Event on took damage.
     * 
     * @return Event of took damage.
     */
    Event<Argument<Integer>> getTookDamageEvent();

    /**
     * Get method for the Rigidness of the Entity.
     */
    @Override
    default Rigidness getRigidness() {
        return Rigidness.MEDIUM;
    }
}
