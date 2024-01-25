package it.unibo.object_onepiece.model;
import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

public abstract class ShipImpl extends EntityImpl implements Ship {
    private Direction currDirection;
    protected int health;
    private final int DAMAGE = 20;

    public ShipImpl(Position p, Direction direction, int health) {
        super(p);
        this.currDirection = direction;
        this.health = health;
    }

    @Override
    public void move(Direction direction) {
        if(direction==this.currDirection) {
            switch (this.currDirection) {
                case UP:
                    this.position = this.position.moveUP();
                    break;
            
                case DOWN:
                    this.position = this.position.moveDOWN();
                    break;

                case LEFT:
                    this.position = this.position.moveLEFT();
                    break;

                case RIGHT:
                    this.position = this.position.moveRIGHT();
                    break;

                default:
                    break;
            }
        } else {
            rotate(direction);
        }
    }

    @Override
    public boolean shoot(Position position) {
        switch (this.currDirection) {
            case UP: case DOWN:
                if(position.x() == this.position.x() && position.y() != this.position.y() && (position.x() >= this.position.x()+3 || position.x() <= this.position.x()-3)) {
                    hitTargets(position);
                    return true;
                }
                break;
        
            case LEFT: case RIGHT:
                if(position.y() == this.position.y() && position.x() != this.position.x() && (position.y() >= this.position.y()+3 || position.y() <= this.position.y()-3)) {
                    hitTargets(position);
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
    }
    
    @Override
    public int getHealth() {
        return this.health;
    }
    
    @Override
    public Direction getDirection() {
        return this.currDirection;
    }
    
    private void hitTargets(Position position) {
        Optional<Enemy> enemy = this.getSection().<Enemy>getEntityAt(position);
        if(enemy.isPresent()) {
            enemy.get().takeDamage(DAMAGE);
        }
    }

    private void rotate(Direction direction) {
        this.currDirection = direction;
    }
}
