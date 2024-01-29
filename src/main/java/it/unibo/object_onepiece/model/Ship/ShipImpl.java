package it.unibo.object_onepiece.model.Ship;
import java.util.Optional;
import java.util.stream.IntStream;

import it.unibo.object_onepiece.model.Collidable;
import it.unibo.object_onepiece.model.Collider;
import it.unibo.object_onepiece.model.Entity;
import it.unibo.object_onepiece.model.EntityImpl;
import it.unibo.object_onepiece.model.Section;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

public abstract class ShipImpl extends EntityImpl<Ship> implements Ship {
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
    public void move(final Direction direction, final Position finalPosition) {
        for(int i = 0; i < this.getPosition().distanceFrom(finalPosition); i++) {
            Position nextPosition = this.position.moveTowards(direction);
            Collidable obstacle = this.getSection().getEntityAt(nextPosition);

            switch (canMove(direction, nextPosition)) {
                case MOVED_SUCCESSFULLY:
                    this.position = nextPosition;
                    break;
    
                case ROTATED_AND_MOVED:
                    rotate(direction);
                    this.position = nextPosition;
                    break;
    
                case ROTATED:
                    rotate(direction);
                    break;
    
                case STATIC_COLLISION:
                    this.collideWith(obstacle);
                    break;
    
                case MOVED_BUT_COLLIDED:
                    this.position = nextPosition;
                    this.collideWith(obstacle);
                    break;
    
                default:
                    break;
            }
        }
    }

    @Override
    public MoveDetails canMove(final Direction direction, final Position nextPosition) {        
        if(!this.sail.isInSpeedRange(this.getPosition(), nextPosition)) {
            return MoveDetails.OUT_OF_SPEED_RANGE;
        }

        if(this.sail.getHealth() <= 0) {
            return MoveDetails.SAIL_BROKEN;
        }
        
        if(!direction.equals(this.currDirection)) {
            if(this.sail.getRotationPower() >= this.getPosition().distanceFrom(nextPosition)) {
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
            return MoveDetails.STATIC_COLLISION;
        }

        if(obstacle.isPresent() && obstacle.get() instanceof Collidable c && c.getRigidness() == Rigidness.SOFT) {
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
