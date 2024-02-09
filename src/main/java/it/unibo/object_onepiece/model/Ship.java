package it.unibo.object_onepiece.model;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.object_onepiece.model.Weapon.ShootReturnType;
import it.unibo.object_onepiece.model.Weapon.ShootDetails;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.Argument;
import it.unibo.object_onepiece.model.events.EventArgs.BiArgument;

/**
 * This class represents a ship that extends the Collider class.
 * It defines methods for taking damage, setting and retrieving weapons, sails, bows, and direction.
 * 
 * Can be extended by specific types of Ship like Player and Enemy.
 * 
 * @see Collider
 */
public abstract class Ship extends Collider {
    private CardinalDirection currDirection;
    private Weapon weapon;
    private Sail sail;
    private Bow bow;

    private final Event<BiArgument<CardinalDirection>> onDirectionChanged = new Event<>();
    private final Event<Argument<Integer>> onTookDamage = new Event<>();

    /**
     * Defines the possible values of MoveDetails which explains what happened while moving.
     */
    enum MoveDetails {
        /**
         * Did not move, Collidable with HARD Rigidness in the way.
         * @see Collidable
         */
        STATIC_COLLISION,
        /**
         * Did not move, reached the border of the map.
         */
        BORDER_REACHED,
        /**
         * Rotated (direction changed), position unchanged.
         */
        ROTATED,
        /**
         * Moved, but collided with a Collidable with MEDIUM or SOFT Rigidness.
         * @see Collidable
         */
        MOVED_BUT_COLLIDED,
        /**
         * Moved without any problems.
         */
        MOVED_SUCCESSFULLY,
        /**
         * Destination too far for the Ship's current range.
         */
        OUT_OF_SPEED_RANGE,
        /**
         * Sail is broken, moving is impossible.
         */
        SAIL_BROKEN
    }

    /**
     * Defines the return type of the move method.
     * @param canStep a boolean which tells the caller of move if this Movable moved or not
     * @param details a MoveDetails object which explains in detail what happened
     */
    record MoveReturnType(boolean canStep, MoveDetails details) { }

    /**
    * Constructor for class ShipImpl.
    *
    * @param  s      the section where the ship is located
    * @param  p      the position of the entity
    * @param  d      the direction of the ship
    * @param  weapon the weapon of the ship
    * @param  sail   the weapon of the ship
    * @param  bow    the weapon of the ship
    */
    protected Ship(final Section s, 
                   final Position p, 
                   final CardinalDirection d, 
                   final Weapon weapon, 
                   final Sail sail, 
                   final Bow bow) {
        super(s, p);
        this.currDirection = d;
        this.weapon = weapon;
        this.sail = sail;
        this.bow = bow;
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
    protected MoveDetails move(final CardinalDirection direction, final int steps) {
        final Position nextPosition = this.getPosition().moveTowards(direction);
        final Optional<Entity> obstacle = this.getSection().getEntityAt(nextPosition);

        final Map<MoveDetails, Runnable> moveCondition = Map.of(
            MoveDetails.MOVED_SUCCESSFULLY, () -> this.setPosition(nextPosition),
            MoveDetails.ROTATED, () -> rotate(direction),
            MoveDetails.STATIC_COLLISION, () -> this.collideWith((Collidable) obstacle.get()),
            MoveDetails.MOVED_BUT_COLLIDED, () -> {
                this.collideWith((Collidable) obstacle.get());
                this.setPosition(nextPosition);
            }
        );

        final MoveReturnType nextStep = checkMove(direction);
        moveCondition.get(nextStep.details()).run();

        if (steps > 1) {
            return move(direction, steps - 1);
        } else {
            return nextStep.details();
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
     * @return           if the ship can move and a MoveDetails for a more detailed feedback on the movement.
     */
    protected MoveReturnType checkMove(final CardinalDirection direction) {
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
     * This method defines the mechanics of the shooting.
     * 
     * If the weapon health is below or equal to zero, 
     * the weapon is broken so it can't shoot.
     * 
     * If the given position is in range with the weapon range
     * and is inline with the side of the ship, the weapon shoots
     * to the position and deals damage in a 3x3 area, where the
     * center is the maximum damage and around it is minimum damage.
     * To deals damage it calls the hitTarget method for every cell.
     * 
     * Otherwise the cell position is out of range and the weapon
     * doesn't shoot.
     * 
     * @param  position the cell position where the weapon should shoot
     * @return          a ShootReturnType that contains the result of the shooting and the details.
     */
    protected ShootReturnType shoot(final Position position) {
        if (this.getWeapon().getHealth() <= 0) {
            return new ShootReturnType(false, ShootDetails.WEAPON_BROKEN);
        }

        if (this.getPosition().isInlineWith(position, this.getDirection()) 
        && this.getPosition().distanceFrom(position) <= this.getWeapon().getRange()) {
            hitTarget(position, this.getWeapon().getMaxDamage());

            Utils.getCardinalDirectionsTranslationMap().values()
                                                       .stream()
                                                       .forEach((f) -> hitTarget(f.apply(position), this.getWeapon()
                                                                                                        .getMinDamage()));

            Utils.getOrdinalDirectionsTranslationMap().values()
                                                      .stream()
                                                      .forEach((f) -> hitTarget(f.apply(position), this.getWeapon()
                                                                                                       .getMinDamage()));

            return new ShootReturnType(true, ShootDetails.SHOOTED_SUCCESSFULLY);
        }

        return new ShootReturnType(false, ShootDetails.OUT_OF_SHOOTING_RANGE);
    }

    /**
     * This method is used to deal damage to a random ShipComponent of as ship.
     * 
     * @param  position the position of the target to hit
     * @param  damage   the damage to deal to the target
     */
    private void hitTarget(final Position position, final int damage) {
        final Optional<Entity> ship = this.getSection().getEntityAt(position);

        if (ship.get() instanceof Ship s) {
            final List<ShipComponent> sc = List.of(
                this.getWeapon(),
                this.getSail(),
                this.getBow()
            );

            s.takeDamage(damage, sc.stream()
                                   .skip((long) (Math.random() * (sc.size() - 1)))
                                   .findFirst()
                                   .orElse(null));
        }
    }

    /**
     * This method is used to cause the ship to take damage from enemy attacks or collisions.
     * Since the ship have multiple component, this method is called on one ShipComponent.
     * It also invoke an Event onTookDamage.
     * 
     * @param  damage the amout of damage to be inflicted
     * @param  s      the ShipComponent that needs to be hit
     */
    protected void takeDamage(final int damage, final ShipComponent s) {
        onTookDamage.invoke(new Argument<>(damage));
        s.setHealth(s.getHealth() - damage);
        if (this.bow.getHealth() <= 0) {
            this.remove();
        }
    }

    /**
     * Setter for the Weapon component of the Ship.
     * 
     * @param  weapon is the weapon to set
     * @see    Weapon
     */
    protected void setWeapon(final Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Setter for the Sail component of the Ship.
     * 
     * @param  sail is the sail to set
     * @see    Sail
     */
    protected void setSail(final Sail sail) {
        this.sail = sail;
    }

    /**
     * Setter for the Bow component of the Ship.
     * 
     * @param  bow is the bow to set
     * @see    Bow
     */
    protected void setBow(final Bow bow) {
        this.bow = bow;
    }

    /**
     * Getter for the Weapon component of the Ship.
     * 
     * @return the current Weapon mounted on the Ship.
     * @see    Weapon
     */
    protected Weapon getWeapon() {
        return this.weapon;
    }

    /**
     * Getter for the Sail component of the Ship.
     * 
     * @return the current Sail mounted on the Ship.
     * @see    Sail
     */
    protected Sail getSail() {
        return this.sail;
    }

    /**
     * Getter for the Bow component of the Ship.
     * 
     * @return the current Bow mounted on the Ship.
     * @see    Bow
     */
    protected Bow getBow() {
        return this.bow;
    }

    /**
     * Getter for the current direction of the Ship.
     * 
     * @return the current direction of the Ship.
     */
    protected CardinalDirection getDirection() {
        return this.currDirection;
    }

    /**
     * Getter for the onDirectionChanged Event.
     * 
     * @return the event of the direction changed.
     * @see    Event
     */
    public Event<BiArgument<CardinalDirection>> getDirectionChangedEvent() {
        return this.onDirectionChanged;
    }

    /**
     * Getter for the onTookDamage Event.
     * 
     * @return the event of the took damage.
     * @see    Event
     */
    public Event<Argument<Integer>> getTookDamageEvent() {
        return this.onTookDamage;
    }

    /**
     * Getter for the Rigidness of the ship.
     * 
     * @return the Rigidnes of the Collider.
     * @see    Collidable
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
     * 
     * @see Collider
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
     * 
     * @see Collidable
     */
    @Override
    protected void collideWith(final Collidable collidable) {
        collidable.onCollisionWith(this);
        if (collidable.getRigidness() == Rigidness.MEDIUM) {
            this.takeDamage(this.bow.getCrashDamage(), this.bow);
        }
    }
}
