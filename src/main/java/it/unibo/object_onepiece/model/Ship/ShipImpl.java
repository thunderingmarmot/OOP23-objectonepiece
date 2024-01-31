package it.unibo.object_onepiece.model.ship;
import java.util.Map;
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

    protected ShipImpl(final Section s, final Position p, final Direction direction, final Weapon weapon, final Sail sail, final Bow bow) {
        super(s, p);
        this.currDirection = direction;
        this.setWeapon(weapon);
        this.setSail(sail);
        this.setBow(bow);
    }

    @Override
    public MoveReturnType move(final Direction direction, final int steps) {
        Position nextPosition = this.position.moveTowards(direction);
        Collidable obstacle = (Collidable) this.getSection().getEntityAt(nextPosition).get();

        Map<MoveDetails, Runnable> moveCondition = Map.of(
            MoveDetails.MOVED_SUCCESSFULLY, () -> this.position = nextPosition,
            MoveDetails.ROTATED, () -> rotate(direction),
            MoveDetails.STATIC_COLLISION, () -> this.collideWith(obstacle),
            MoveDetails.MOVED_BUT_COLLIDED, () -> { this.collideWith(obstacle);
                                                    this.position = nextPosition; }
        );

        MoveReturnType nextStep = canMove(direction);
        moveCondition.get(nextStep.details()).run();
                
        if(steps==0) {
            return nextStep;
        } else {
            return move(direction, steps-1);
        }
    }

    public MoveReturnType move(final Direction direction) {
        return move(direction, 1);
    }

    @Override
    public MoveReturnType canMove(final Direction direction) {
        if(this.sail.getHealth() <= 0) {
            return new MoveReturnType(false, MoveDetails.SAIL_BROKEN);
        }
        
        if(!direction.equals(this.currDirection)) {
            return new MoveReturnType(false, MoveDetails.ROTATED);
        }
        
        if(!this.getSection().getBounds().isInside(position)) {
            return new MoveReturnType(false, MoveDetails.BORDER_REACHED);
        }
        
        Optional<Entity> obstacle = this.getSection().getEntityAt(this.position.moveTowards(direction));
        
        if(obstacle.isPresent() && obstacle.get() instanceof Collidable c &&
        (c.getRigidness() == Rigidness.HARD || c.getRigidness() == Rigidness.MEDIUM)) {
            return new MoveReturnType(false, MoveDetails.STATIC_COLLISION);
        }
        
        if(obstacle.isPresent() && obstacle.get() instanceof Collidable c && c.getRigidness() == Rigidness.SOFT) {
            return new MoveReturnType(true, MoveDetails.MOVED_BUT_COLLIDED);
        }
        
        return new MoveReturnType(true, MoveDetails.MOVED_SUCCESSFULLY);
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
        this.weapon.setShip(this);
    }
    
    @Override
    public void setSail(final Sail sail) {
        this.sail = sail;
        this.sail.setShip(this);
    }
    
    @Override
    public void setBow(final Bow bow) {
        this.bow = bow;
        this.bow.setShip(this);
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
