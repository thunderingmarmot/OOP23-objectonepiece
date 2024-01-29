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
    public boolean move(final Position finaPosition, final Direction direction) {
        final int steps = this.getPosition().distanceFrom(finaPosition);

        if(this.sail.isInSpeedRange(steps)) {
            IntStream.range(0, steps)
                     .forEach(i -> moveStep(direction));
            return true;
        }

        if(this.sail.getRotationPower() >= steps) {
            IntStream.range(0, steps + 1)
                     .forEach(i -> moveStep(direction));
            return true;
        }

        return false;
    }

    
    @Override
    public MoveDetails canMove(final Direction direction) {
        if(this.sail.getHealth() <= 0) {
            return MoveDetails.SAIL_BROKEN;
        }
        
        if(!direction.equals(this.currDirection)) {
            return MoveDetails.ROTATED;
        }
        
        if(this.getSection().getBounds().isInside(position)) {
            return MoveDetails.BORDER_REACHED;
        }
        
        Optional<Entity> obstacle = this.getSection().getEntityAt(this.position.moveTowards(direction));
        
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
    
    private void moveStep(final Direction direction) {
        Position nextPosition = this.position.moveTowards(direction);
        Collidable obstacle = (Collidable) this.getSection().getEntityAt(nextPosition).get();
    
        switch (canMove(direction)) {
            case MOVED_SUCCESSFULLY:
                this.position = nextPosition;
                break;
    
            case ROTATED:
                rotate(direction);
                break;
    
            case STATIC_COLLISION:
                this.collideWith(obstacle);
                break;
    
            case MOVED_BUT_COLLIDED:
                this.collideWith(obstacle);
                this.position = nextPosition;
                break;
    
            default:
                break;
        }
    }

    private void rotate(final Direction direction) {
        this.currDirection = direction;
    }
    
    @Override
    public Rigidness getRigidness() {
        return Rigidness.MEDIUM;
    }
    
    @Override
    public void onCollisionWith(final Collider collider) {
        if(collider.getRigidness() == Rigidness.MEDIUM) {
            this.takeDamage(this.bow.getCrashDamage(), this.bow);
        }
    }
    
    @Override
    public void collideWith(final Collidable collidable) {
        collidable.onCollisionWith(this);
        if(collidable.getRigidness() == Rigidness.MEDIUM) {
            this.takeDamage(this.bow.getCrashDamage(), this.bow);
        }
    }
}
