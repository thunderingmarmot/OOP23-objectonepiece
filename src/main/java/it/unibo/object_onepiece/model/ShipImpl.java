package it.unibo.object_onepiece.model;
import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

public abstract class ShipImpl extends EntityImpl implements Ship {
    private Direction currDirection;
    private int health;
    private Weapon weapon;
    private int crashDamage;

    //public Map<MoveReturnTypes, Predicate<>>

    public ShipImpl(final Section s, final Position p, final Direction direction, final int health, final Weapon weapon, final int crashDamage) {
        super(s, p);
        this.currDirection = direction;
        this.health = health;
        this.weapon = weapon;
        this.crashDamage = crashDamage;
    }

    @Override
    public MoveReturnType move(final Direction direction) {
        if(direction.equals(this.currDirection)) {
            Optional<Entity> collidable = this.getSection().getEntityAt(this.position.moveTowards(direction));

            if(collidable.get() instanceof Collidable c && c.isRigid()) {
                this.collideWith(c);
                return MoveReturnType.COLLIDABLE;
            } else if(this.getSection().getBounds().isInside(position)) {
                return MoveReturnType.BORDER;
            }

            this.position = this.position.moveTowards(direction);
            if(collidable.get() instanceof Collidable c && !c.isRigid()) {
                this.collideWith(c);
                return MoveReturnType.CRASHABLE;
            } else {
                return MoveReturnType.MOVED;
            }
        } else {
            rotate(direction);
            return MoveReturnType.ROTATED;
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
    public void setWeapon(final Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public void setHealth(final int health) {
        this.health = health;
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

    private void rotate(final Direction direction) {
        this.currDirection = direction;
    }

    @Override
    public boolean isRigid() {
        return false;
    }

    @Override
    public void collideWith(Collidable collidable) {
        if(collidable instanceof StaticCollidable s) {
            s.onCollision(this);
        }
    }
}
