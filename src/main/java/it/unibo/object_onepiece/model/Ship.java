package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * This class represents a ship that extends the Collider class.
 * It defines methods for taking damage, setting and retrieving weapons, sails, bows, and direction.
 * 
 * Can be extended by specific types of Ship like Player and Enemy.
 * 
 * @see Collider
 */
public abstract class Ship extends Collider {
    private Weapon weapon;
    private Sail sail;
    private Bow bow;
    private Keel keel;

    /**
     * Defines the possible values of MoveDetails which explains what happened while moving.
     */
    enum MoveDetails {
        /**
         * Did not move, Collidable with HARD Rigidness in the way.
         * @see Collidable
         */
        HARD_COLLISION,
        /**
         * Did not move but can rotate, Collidable with MEDIUM Rigidness in the way.
         * @see Collidable
         */
        MEDIUM_COLLISION,
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
        SAIL_BROKEN,
        /**
         * The next direction of the ship is null.
         */
        NO_MOVEMENT
    }

    /**
     * Enum to define various type of result returned by shoot.
     */
    enum ShootDetails {
        /** 
         * The weapon has shooted succesfully. 
         */
        SHOOTED_SUCCESSFULLY,
        /**
         * The position where you want to shoot is out of range. 
         */
        OUT_OF_SHOOTING_RANGE,
        /** 
         * The weapon is broken. 
         */
        WEAPON_BROKEN
    }

    /**
     * Record returned by shoot.
     * 
     * @param  hasShooted result of the shooting
     * @param  details    details about the shooting result
     */
    record ShootReturnType(boolean hasShooted, ShootDetails details) { }

    /**
     * List of the MoveDetails returned when the ship has successfully moved.
     */
    protected static final List<MoveDetails> MOVE_SUCCESS_CONDITIONS = List.of(
        MoveDetails.MOVED_SUCCESSFULLY,
        MoveDetails.MOVED_BUT_COLLIDED,
        MoveDetails.ROTATED
    );

    /**
     * List of the MoveDetails returned when the ship has collided.
     */
    protected static final List<MoveDetails> MOVE_COLLISION_CONDITIONS = List.of(
        MoveDetails.HARD_COLLISION,
        MoveDetails.MEDIUM_COLLISION,
        MoveDetails.BORDER_REACHED
    );

    /**
     * Constructor to copy from an existing Ship.
     * @param origin the Ship to copy from
     * @see Entity
     */
    protected Ship(final Ship origin) {
        super(origin);
        this.weapon = origin.getWeapon().copy();
        this.sail = origin.getSail().copy();
        this.bow = origin.getBow().copy();
        this.keel = origin.getKeel().copy();
    }

    /**
    * Constructor for class ShipImpl.
    *
    * @param  s      the section where the ship is located
    * @param  p      the position of the entity
    * @param  d      the direction of the ship
    * @param  weapon the weapon of the ship
    * @param  sail   the weapon of the ship
    * @param  bow    the weapon of the ship
    * @param  keel   the keel of the ship
    */
    protected Ship(final Section s, 
                   final Position p, 
                   final CardinalDirection d, 
                   final Weapon weapon, 
                   final Sail sail, 
                   final Bow bow,
                   final Keel keel) {
        super(s, p, d);
        this.weapon = weapon;
        this.sail = sail;
        this.bow = bow;
        this.keel = keel;
    }

    /**
     * This method define the actual ship movement by calling step() steps times.
     * Every step the method try to move the Ship to the next position up to the final position.
     * If along the path there are static collision the Ship stops right before them.
     * 
     * @param  direction is the direction where the ship should move
     * @param  steps     is the number of steps that the ship should do to reach the final position
     * @return           a MoveDetails that contains the result of the last movement made by the Ship.
     */
    protected MoveDetails move(final CardinalDirection direction, final int steps) {
        if (!this.getSail().isInSpeedRange(steps)) {
            return MoveDetails.OUT_OF_SPEED_RANGE;
        }

        MoveDetails nextStep = MoveDetails.NO_MOVEMENT;

        for (int i = 0; i < steps && !MOVE_COLLISION_CONDITIONS.contains(nextStep); i++) {
            nextStep = this.step(direction);
        }

        return nextStep;
    }

    /**
     * This method move the ship by one cell depending on the result of checkMove().
     * 
     * @param  direction the direction where the ship must move at
     * @return           the MoveDetails returned by checkMove().
     */
    protected MoveDetails step(final CardinalDirection direction) {
        final Position nextPosition = this.getPosition().moveTowards(direction);
        final Optional<Entity> obstacle = this.getSection().getEntityAt(nextPosition);
        final MoveDetails nextStep = checkMove(direction, obstacle);

        if ((MOVE_COLLISION_CONDITIONS.contains(nextStep)
        || nextStep.equals(MoveDetails.MOVED_BUT_COLLIDED))
        && obstacle.isPresent()
        && obstacle.get() instanceof Collidable c) {
            this.collideWith(c);
        }

        if (nextStep.equals(MoveDetails.ROTATED)) {
            this.rotate(direction);
            if (this.getSail().haveRotationPower()) {
                step(direction);
            }
        }

        if (nextStep.equals(MoveDetails.MOVED_SUCCESSFULLY)
        || nextStep.equals(MoveDetails.MOVED_BUT_COLLIDED)) {
            this.setPosition(nextPosition);
        }

        return nextStep;
    }

    /**
     * This method is used to check if the Ship can move to the next cell.
     * 
     * @param  direction the direction where the ship should move to
     * @param  obstacle  optional of an obstacle in front of the ship
     * @return           a MoveDetails for a detailed outcome of the movement check.
     */
    protected MoveDetails checkMove(final CardinalDirection direction, final Optional<Entity> obstacle) {
        if (Objects.isNull(direction)) {
            return MoveDetails.NO_MOVEMENT;
        }

        if (obstacle.isPresent() 
        && obstacle.get() instanceof Collidable c
        && c.getRigidness() == Rigidness.HARD) {
            return MoveDetails.HARD_COLLISION;
        }

        if (!direction.equals(this.getDirection())) {
            return MoveDetails.ROTATED;
        }

        if (this.getSail().getHealth() <= 0) {
            return MoveDetails.SAIL_BROKEN;
        }

        if (!this.getSection().getBounds().isInside(this.getPosition().moveTowards(direction))) {
            return MoveDetails.BORDER_REACHED;
        }

        if (obstacle.isPresent() 
        && obstacle.get() instanceof Collidable c
        && c.getRigidness() == Rigidness.MEDIUM) {
            return MoveDetails.MEDIUM_COLLISION;
        }

        if (obstacle.isPresent() 
        && obstacle.get() instanceof Collidable c 
        && c.getRigidness() == Rigidness.SOFT) {
            return MoveDetails.MOVED_BUT_COLLIDED;
        }

        return MoveDetails.MOVED_SUCCESSFULLY;
    }

    /**
     * This method rotates the Ship to the given direction.
     * 
     * @param  direction the direction in which the ship must rotate
     */
    protected void rotate(final CardinalDirection direction) {
        this.setDirection(direction);
    }

    /**
     * This method calls takeDamage on the collider's 
     * bow if its Rigidness is MEDIUM and if it's a Ship.
     * If the collider is colliding laterally or 
     * behind this ship, the damage is received by the keel
     * of this ship, also if this keel is damaged the damage
     * of the collider's bow is multiplied by its crash damage multiplier.
     * Instead if the two ships are colliding frontally the
     * damage is received by the ship's bow.
     * 
     * @see  Collider
     * @see  Utils
     * @see  ShipComponent
     * @see  Bow
     * @see  Keel
     */
    @Override
    protected void onCollisionWith(final Collider collider) {
        if (collider.getRigidness() == Rigidness.MEDIUM && collider instanceof Ship s) {
            int damage = s.getBow().getCrashDamage();
            ShipComponent shipComponent = this.getBow();

            if (Utils.areEntitiesPerpendicular(this, collider)
            || Utils.areEntitiesInSameDirection(this, s)) {
                if (this.getKeel().isKeelDamaged()) {
                    damage *= s.getBow().getCrashDamageMultiplier();
                }
                shipComponent = this.getKeel();
            }

            this.takeDamage(damage, shipComponent);
        }
    }

    /**
     * This method calls takeDamage() on its bow with the damage
     * of the collidable's bow if the Rigidness of the collidable is MEDIUM,
     * if it's a Ship and if the two ships are colliding frontally
     * Then it calls onCollisionWith() on the collidable.
     * 
     * @see  Collidable
     * @see  Utils
     * @see  ShipComponent
     * @see  Bow
     */
    @Override
    protected void collideWith(final Collidable collidable) {
        if (collidable.getRigidness() == Rigidness.MEDIUM
        && !Utils.areEntitiesPerpendicular(collidable, this)
        && collidable instanceof Ship s
        && !Utils.areEntitiesInSameDirection(this, s)
        && s.getBow().getHealth() > 0) {
            this.takeDamage(s.getBow().getCrashDamage(), this.getBow());
        }
        collidable.onCollisionWith(this);
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
            final Position shootPosition = this.getEntityPositionInTrajectory(position);

            hitTarget(shootPosition, this.getWeapon().getMaxDamage());

            Utils.getCardinalDirectionsTranslationMap().values()
                                                       .stream()
                                                       .forEach((f) -> hitTarget(f.apply(shootPosition), this.getWeapon()
                                                                                                        .getMinDamage()));

            Utils.getOrdinalDirectionsTranslationMap().values()
                                                      .stream()
                                                      .forEach((f) -> hitTarget(f.apply(shootPosition), this.getWeapon()
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

        if (ship.isPresent() && ship.get() instanceof Ship s) {
            final long count = s.getShipComponents().stream()
                                                    .filter(c -> c.getHealth() > 0)
                                                    .count();

            s.takeDamage(damage, s.getShipComponents().stream()
                                                      .filter(c -> c.getHealth() > 0)
                                                      .skip(Utils.getRandom().nextLong(count))
                                                      .findFirst()
                                                      .orElse(null));
        }
    }

    private Position getEntityPositionInTrajectory(final Position p) {
        Position nextPosition = this.getPosition();
        CardinalDirection direction = this.getDirection();
        Optional<Entity> entity = Optional.empty();

        final Map<CardinalDirection, BiPredicate<Position, Position>> positionsOnTrajectoryConditions = Map.of(
            CardinalDirection.NORTH, (p1, p2) -> p1.row() > p2.row(),
            CardinalDirection.SOUTH, (p1, p2) -> p1.row() < p2.row(),
            CardinalDirection.EAST, (p1, p2) -> p1.column() < p2.column(),
            CardinalDirection.WEST, (p1, p2) -> p1.column() > p2.column()
        );

        while (this.getPosition().distanceFrom(p) > this.getPosition().distanceFrom(nextPosition)
        && entity.isEmpty()) {

            for (final CardinalDirection d : CardinalDirection.values()) {
                if (positionsOnTrajectoryConditions.get(d).test(nextPosition, p)) {
                    direction = d;
                }
            }

            nextPosition = nextPosition.moveTowards(direction);
            entity = this.getSection().getEntityAt(nextPosition);
        }

        if (entity.isPresent()) {
            return entity.get().getPosition();
        }

        return p;
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
        if (s.getHealth() > 0) {
            if (s.getHealth() > damage) {
                s.setHealth(s.getHealth() - damage);
            } else {
                s.setHealth(0);
            }
        }
    }

    /**
     * This method checks if the ship is dead by looking at the keel health.
     * 
     * @return the result of the control.
     */
    protected boolean isShipDead() {
        return this.getKeel().getHealth() <= 0;
    }

    /**
     * This method remove the ship.
     */
    protected void die() {
        this.remove();
        this.getEntityCreatedEvent().invalidate();
        this.getEntityUpdatedEvent().invalidate();
        this.getEntityRemovedEvent().invalidate();
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
     * Setter for the Keel component of the Ship.
     * 
     * @param  keel is the keel to set
     * @see    Keel
     */
    protected void setKeel(final Keel keel) {
        this.keel = keel;
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
     * Getter for the Keel component of the Ship.
     * 
     * @return the current Keel mounted on the Ship.
     * @see    Keel
     */
    protected Keel getKeel() {
        return this.keel;
    }

    /**
     * Getter for all the component of the ship.
     * 
     * @return a list that contains all the ship component.
     */
    protected List<ShipComponent> getShipComponents() {
        return List.of(
            this.getWeapon(),
            this.getBow(),
            this.getSail(),
            this.getKeel()
        );
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
}
