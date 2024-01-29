package it.unibo.object_onepiece.model.Ship;
import java.util.Optional;

import it.unibo.object_onepiece.model.Collidable;
import it.unibo.object_onepiece.model.Collider;
import it.unibo.object_onepiece.model.Entity;
import it.unibo.object_onepiece.model.EntityImpl;
import it.unibo.object_onepiece.model.Section;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

public abstract class ShipImpl extends EntityImpl implements Ship {
    private Direction currDirection;
    private Weapon weapon;
    private Sail sail;
    private Bow bow;

    public ShipImpl(final Section s, final Position p, final Direction direction, final Weapon weapon, final Sail sail, final Bow bow) {
        super(s, p);
        this.currDirection = direction;
        this.weapon = weapon;
        this.sail = sail;
        this.bow = bow;
    }

    @Override
    public MoveDetails move(final Direction direction, final Position nextPos) {
        if(!this.sail.isInSpeedRange(this.getPosition(), nextPos)) {
            return MoveDetails.OUT_OF_SPEED_RANGE;
        }

        if(this.sail.getHealth() <= 0) {
            return MoveDetails.SAIL_BROKEN;
        }

        Position nextPosition = this.position.moveTowards(direction, this.getPosition().distanceFrom(nextPos));
        
        if(!direction.equals(this.currDirection)) {
            rotate(direction);
            if(this.sail.getRotationPower() >= this.getPosition().distanceFrom(nextPos)) {
                this.position = nextPosition;
                return MoveDetails.ROTATED_AND_MOVED;
            }
            return MoveDetails.ROTATED;
        }

        if(this.getSection().getBounds().isInside(position)) {
            return MoveDetails.BORDER_REACHED;
        }

        Optional<Entity> obstacle = this.getSection().getEntityAt(nextPosition);
        
        if(obstacle.isPresent() && obstacle.get() instanceof Collidable c &&
        (c.getRigidness() == Rigidness.HARD || c.getRigidness() == Rigidness.MEDIUM)) {
            this.collideWith(c);
            return MoveDetails.STATIC_COLLISION;
        }

        this.position = nextPosition; // Here the Ship actually moves

        if(obstacle.isPresent() && obstacle.get() instanceof Collidable c && c.getRigidness() == Rigidness.SOFT) {
            this.collideWith(c);
            return MoveDetails.MOVED_BUT_COLLIDED;
        }
        
        return MoveDetails.MOVED_SUCCESSFULLY;
    }
    
    @Override
    public void takeDamage(final int damage, final ShipComponent s) {
        s.setHealth(s.getHealth() - damage);
        if(this.bow.getHealth() <= 0) {
            this.remove();
        }
    }

    @Override
    public void setWeapon(final Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public void setSail(final Sail sail) {
        this.sail = sail;
    }

    @Override
    public void setBow(final Bow bow) {
        this.bow = bow;
    }

    @Override
    public void setTotalHealth(final int health) {
        this.weapon.setHealth(health);
        this.sail.setHealth(health);
        this.bow.setHealth(health);
    }
    
    @Override
    public Weapon getWeapon() {
        return this.weapon;
    }

    @Override
    public Sail getSail() {
        return this.sail;
    }

    @Override
    public Bow getBow() {
        return this.bow;
    }
    
    @Override
    public Direction getDirection() {
        return this.currDirection;
    }

    private void rotate(final Direction direction) {
        this.currDirection = direction;
    }

    public Rigidness getRigidness() {
        return Rigidness.MEDIUM;
    }

    @Override
    public void onCollisionWith(Collider collider) {
        if(collider.getRigidness() == Rigidness.MEDIUM) {
            this.takeDamage(this.bow.getCrashDamage(), this.bow);
        }
    }

    @Override
    public void collideWith(Collidable collidable) {
        collidable.onCollisionWith(this);
        if(collidable.getRigidness() == Rigidness.MEDIUM) {
            this.takeDamage(this.bow.getCrashDamage(), this.bow);
        }
    }
}
