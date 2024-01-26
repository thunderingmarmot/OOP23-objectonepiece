package it.unibo.object_onepiece.model;
import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.Utils.MoveReturnTypes;

public abstract class ShipImpl extends EntityImpl implements Ship {
    private Direction currDirection;
    protected int health;
    public final int MAX_DAMAGE = 20;
    public final int MIN_DAMAGE = 10;
    public final int ATTACK_DISTANCE = 3;

    public ShipImpl(final Section s, final Position p, final Direction direction, final int health) {
        super(s, p);
        this.currDirection = direction;
        this.health = health;
    }

    @Override
    public MoveReturnTypes move(final Direction direction) {
        if(direction.equals(this.currDirection)) {
            Optional<Entity> collidable = this.getSection().getEntityAt(this.position.moveTowards(direction));

            if(collidable.get() instanceof Collidable) {
                return MoveReturnTypes.COLLIDABLE;
            } else if(this.position.moveTowards(direction).x() == 0 || this.position.moveTowards(direction).y() == 0 || this.position.moveTowards(direction).x() == 10 || this.position.moveTowards(direction).y() == 10) {
                return MoveReturnTypes.BORDER;
            }

            this.position = this.position.moveTowards(direction);
            return MoveReturnTypes.SUCCESS;
        } else {
            rotate(direction);
            return MoveReturnTypes.ROTATION;
        }
    }

    @Override
    public boolean shoot(final Position position) {
        if(this.position.isInlineWith(position, this.currDirection) && this.position.distanceFrom(position) <= ATTACK_DISTANCE) {
            hitTarget(position, MAX_DAMAGE);
            Position.directionPositions.values().stream().forEach((f) -> hitTarget(f.apply(position), MIN_DAMAGE));
            Position.diagonalPositions.values().stream().forEach((f) -> hitTarget(f.apply(position), MIN_DAMAGE));
            return true;
        }
        return false;
    }
    
    @Override
    public void takeDamage(int damage) {
        this.health -= damage;
        if(this.health <= 0) {
            this.getSection().removeEntityAt(this.getPosition());
        }
    }
    
    @Override
    public int getHealth() {
        return this.health;
    }
    
    @Override
    public Direction getDirection() {
        return this.currDirection;
    }

    private void hitTarget(Position position, int damage) {
        Optional<Ship> ship = this.getSection().<Ship>getEntityAt(position);
        if(ship.isPresent()) {
            ship.get().takeDamage(damage);
        }
    }

    private void rotate(Direction direction) {
        this.currDirection = direction;
    }
}
