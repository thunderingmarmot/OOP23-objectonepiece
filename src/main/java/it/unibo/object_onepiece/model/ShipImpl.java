package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

public abstract class ShipImpl extends EntityImpl implements Ship {
    private Direction currDirection;
    protected int health;

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
            this.currDirection = direction;
        }
    }

    @Override
    public boolean shoot(Position position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shoot'");
    }
    
    @Override
    public void takeDamage(int damage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeDamage'");
    }
    
    @Override
    public int getHealth() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHealth'");
    }
    
    @Override
    public Direction getDirection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDirection'");
    }
    
    private void rotate(Direction direction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rotate'");
    }
}
