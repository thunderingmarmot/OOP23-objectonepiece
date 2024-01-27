package it.unibo.object_onepiece.model;
import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.Utils.MoveReturnTypes;

public abstract class ShipImpl extends EntityImpl implements Ship {
    private Direction currDirection;
    protected int health;
    private Weapon weapon;

    //public Map<MoveReturnTypes, Predicate<>>

    public ShipImpl(final Section s, final Position p, final Direction direction, final int health, final Weapon weapon) {
        super(s, p);
        this.currDirection = direction;
        this.health = health;
        this.weapon = weapon;
    }

    @Override
    public MoveReturnTypes move(final Direction direction) {
        if(direction.equals(this.currDirection)) {
            Optional<Entity> collidable = this.getSection().getEntityAt(this.position.moveTowards(direction));

            if(collidable.get() instanceof Collidable) {
                return MoveReturnTypes.COLLIDABLE;
            } else if(this.getSection().getBounds().isInside(position)) {
                return MoveReturnTypes.BORDER;
            }

            this.position = this.position.moveTowards(direction);
            if(collidable.get() instanceof Crashable s) {
                s.crash(this.getCrashDamage(), this);
                this.crash(s.getCrashDamage(), s);
                return MoveReturnTypes.CRASHABLE;
            } else {
                return MoveReturnTypes.MOVED;
            }
        } else {
            rotate(direction);
            return MoveReturnTypes.ROTATED;
        }
    }
    
    @Override
    public void takeDamage(final int damage) {
        this.health -= damage;
        if(this.health <= 0) {
            this.remove();
        }
    }
    
    @Override
    public Weapon getWeapon() {
        return this.weapon;
    }

    @Override
    public int getHealth() {
        return this.health;
    }
    
    @Override
    public Direction getDirection() {
        return this.currDirection;
    }

    @Override
    public void crash(final int damage, final Crashable c) {
        this.takeDamage(damage);
    }

    @Override
    public int getCrashDamage() {
        return 100;
    }

    private void rotate(final Direction direction) {
        this.currDirection = direction;
    }
}
