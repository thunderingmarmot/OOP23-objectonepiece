package it.unibo.object_onepiece.model;
import java.util.Map;
import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.Argument;
import it.unibo.object_onepiece.model.events.EventArgs.BiArgument;

/**
 * This class represents a Ship Entity that extends the Collider class.
 * It defines methods for taking damage, setting and retrieving weapons, sails, bows, and direction.
 * 
 * Can be extended by specific types of Ship like Player and Enemy.
 */
public abstract class Ship extends Entity {
    private CardinalDirection currDirection;
    private Weapon weapon;
    private Sail sail;
    private Bow bow;

    private final Event<BiArgument<CardinalDirection>> onDirectionChanged = Event.get();
    private final Event<Argument<Integer>> onTookDamage = Event.get();

    /**
    * Constructor for class ShipImpl.
    *
    * @param  s the section where the ship is located
    * @param  p the position of the entity
    * @param  d the direction of the ship
    */
    protected Ship(final Section s, final Position p, final CardinalDirection d) {
        super(s, p);
        this.currDirection = d;
    }

    /**
     * This method define the actual ship movement by checking the next position
     * using canMove() and move the Ship based on the MoveReturnType returned.
     * 
     * @param  direction is the direction where the ship should move
     * @param  steps     is the number of steps that the ship should do to reach the final position
     * @return           a MoveDetails that contains the result of the last movement made by the Ship.
     * 
     * This is a recursive method that calls himself steps times.
     * Every call the method try to move the Ship to the next position up to the final position.
     * If along the path there are immovable obstacles the Ship stops right before them.
     */
    @Override
    protected MoveDetails move(final CardinalDirection direction, final int steps) {
        final Position nextPosition = this.getPosition().moveTowards(direction);
        final Collidable obstacle = (Collidable) this.getSection().getEntityAt(nextPosition).get();

        final Map<MoveDetails, Runnable> moveCondition = Map.of(
            MoveDetails.MOVED_SUCCESSFULLY, () -> this.setPosition(nextPosition),
            MoveDetails.ROTATED, () -> rotate(direction),
            MoveDetails.STATIC_COLLISION, () -> this.collideWith(obstacle),
            MoveDetails.MOVED_BUT_COLLIDED, () -> { 
                this.collideWith(obstacle); 
                this.setPosition(nextPosition); 
            }
        );

        final MoveReturnType nextStep = canMove(direction);
        moveCondition.get(nextStep.details()).run();

        if (steps == 0) {
            return nextStep.details();
        } else {
            return move(direction, steps - 1);
        }
    }

    /**
     * Overloading of the default move method.
     * This only accept the direction as a parameter 
     * because it calls the move method by passing only 1 step.
     * 
     * This is made to simplify the calls to this method by Enemy ships,
     * because they can't pick up power ups for their ships, so by default
     * they can move by only one cell per turn.
     * 
     * @param  direction is the direction where the ship should move to
     * @return           a MoveDetails that contains the result of the last movement made by the Ship.
     */
    protected MoveDetails move(final CardinalDirection direction) {
        return move(direction, 1);
    }

    /**
     * This method is used to check if the Ship can move to the next cell.
     * 
     * @param  direction is the direction where the ship should move to
     * @return a MoveReturnType that contains a boolean field canStep which indicates 
     * if the Ship can move and a MoveDetails field for a more detailed feedback on the movement.
     */
    @Override
    protected MoveReturnType canMove(final CardinalDirection direction) {
        if (this.sail.getHealth() <= 0) {
            return new MoveReturnType(false, MoveDetails.SAIL_BROKEN);
        }

        if (!direction.equals(this.currDirection)) {
            return new MoveReturnType(false, MoveDetails.ROTATED);
        }

        if (!this.getSection().getBounds().isInside(this.getPosition())) {
            return new MoveReturnType(false, MoveDetails.BORDER_REACHED);
        }

        final Optional<Entity> obstacle = this.getSection().getEntityAt(this.getPosition().moveTowards(direction));

        if (obstacle.isPresent() && obstacle.get() instanceof Collidable c 
        && (c.getRigidness() == Rigidness.HARD || c.getRigidness() == Rigidness.MEDIUM)) {
            return new MoveReturnType(false, MoveDetails.STATIC_COLLISION);
        }

        if (obstacle.isPresent() && obstacle.get() instanceof Collidable c && c.getRigidness() == Rigidness.SOFT) {
            return new MoveReturnType(true, MoveDetails.MOVED_BUT_COLLIDED);
        }

        return new MoveReturnType(true, MoveDetails.MOVED_SUCCESSFULLY);
    }

    /**
     * This method is used to cause the ship to take damage from enemy attacks or collisions.
     * Since the ship have multiple component, this method is called on one ShipComponent.
     * It also invoke an Event onTookDamage.
     * 
     * @param  damage the amout of damage to be inflicted
     * @param  s      the ShipComponent that needs to be hit
     */
    @Override
    protected void takeDamage(final int damage, final ShipComponent s) {
        onTookDamage.invoke(new Argument<>(damage));
        s.setHealth(s.getHealth() - damage);
        if (this.bow.getHealth() <= 0) {
            this.remove();
        }
    }

    /**
     * Method to check if the ship has all the ship component
     * by checking if they're not setted to null.
     * 
     * @return the result of the check.
     */
    @Override
    protected boolean isShipValid() {
        return this.weapon != null && this.sail != null && this.bow != null;
    }

    /**
     * Setter for the Weapon component of the Ship.
     * 
     * @param  weapon is the weapon to set
     */
    @Override
    protected void setWeapon(final Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Setter for the Sail component of the Ship.
     * 
     * @param  sail is the sail to set
     */
    @Override
    protected void setSail(final Sail sail) {
        this.sail = sail;
    }

    /**
     * Setter for the Bow component of the Ship.
     * 
     * @param  bow is the bow to set
     */
    @Override
    protected void setBow(final Bow bow) {
        this.bow = bow;
    }

    /**
     * Getter for the Weapon component of the Ship.
     * 
     * @return the current Weapon mounted on the Ship.
     */
    @Override
    protected Weapon getWeapon() {
        return this.weapon;
    }

    /**
     * Getter for the Sail component of the Ship.
     * 
     * @return the current Sail mounted on the Ship.
     */
    @Override
    protected Sail getSail() {
        return this.sail;
    }

    /**
     * Getter for the Bow component of the Ship.
     * 
     * @return the current Bow mounted on the Ship.
     */
    @Override
    protected Bow getBow() {
        return this.bow;
    }

    /**
     * Getter for the current direction of the Ship.
     * 
     * @return the current direction of the Ship.
     */
    @Override
    protected CardinalDirection getDirection() {
        return this.currDirection;
    }

    /**
     * Getter for the onDirectionChanged Event.
     */
    @Override
    public Event<BiArgument<CardinalDirection>> getDirectionChangedEvent() {
        return this.onDirectionChanged;
    }

    /**
     * Getter for the onTookDamage Event.
     */
    @Override
    public Event<Argument<Integer>> getTookDamageEvent() {
        return this.onTookDamage;
    }

    /**
     * Getter for the Rigidness of the Entity.
     */
    @Override
    protected Rigidness getRigidness() {
        return Rigidness.MEDIUM;
    }

    /**
     * This method rotates the Ship to the given direction and
     * invoke an Event onDirectionChanged.
     * 
     * @param  direction the direction in which the ship must rotate
     */
    protected void rotate(final CardinalDirection direction) {
        onDirectionChanged.invoke(new BiArgument<>(this.currDirection, direction));
        this.currDirection = direction;
    }

    /**
     * This method calls takeDamage on the collider's 
     * bow if its Rigidness is MEDIUM.
     */
    @Override
    protected void onCollisionWith(final Collider collider) {
        if (collider.getRigidness() == Rigidness.MEDIUM) {
            this.takeDamage(this.bow.getCrashDamage(), this.bow);
        }
    }

    /**
     * This method calls onCollisionWith() on the collidable
     * and if the Rigidness of the collidable is MEDIUM it calls
     * takeDamage() on himself.
     */
    @Override
    protected void collideWith(final Collidable collidable) {
        collidable.onCollisionWith(this);
        if (collidable.getRigidness() == Rigidness.MEDIUM) {
            this.takeDamage(this.bow.getCrashDamage(), this.bow);
        }
    }
}
