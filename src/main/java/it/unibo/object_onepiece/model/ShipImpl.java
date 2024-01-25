package it.unibo.object_onepiece.model;
import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

public abstract class ShipImpl extends EntityImpl implements Ship {
    private Direction currDirection;
    protected int health;
    private final int MAX_DAMAGE = 20;
    private final int MIN_DAMAGE = 10;

    public ShipImpl(Position p, Direction direction, int health) {
        super(p);
        this.currDirection = direction;
        this.health = health;
    }

    @Override
    public void move(Direction direction) {
        if(direction==this.currDirection) {
            this.position = this.position.move(direction);
        } else {
            rotate(direction);
        }
    }

    @Override
    public boolean shoot(Position position) {
        switch (this.currDirection) {
            case UP: case DOWN:
                if(position.x() == this.position.x() && position.y() != this.position.y() && (position.x() >= this.position.x()+3 || position.x() <= this.position.x()-3)) {
                    hitTarget(position, MAX_DAMAGE);
                    Position.aroundPosition.values().stream().forEach((f) -> hitTarget(f.apply(position), MIN_DAMAGE));
                    Position.diagonalPosition.stream().forEach((f) -> hitTarget(f.apply(position), MIN_DAMAGE));
                    return true;
                }
                break;
        
            case LEFT: case RIGHT:
                if(position.y() == this.position.y() && position.x() != this.position.x() && (position.y() >= this.position.y()+3 || position.y() <= this.position.y()-3)) {
                    hitTarget(position, MAX_DAMAGE);
                    Position.aroundPosition.values().stream().forEach((f) -> hitTarget(f.apply(position), MIN_DAMAGE));
                    Position.diagonalPosition.stream().forEach((f) -> hitTarget(f.apply(position), MIN_DAMAGE));
                    return true;
                }
                break;

            default:
                break;
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
