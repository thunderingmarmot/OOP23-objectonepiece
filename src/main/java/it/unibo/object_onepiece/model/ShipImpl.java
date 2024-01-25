package it.unibo.object_onepiece.model;

public abstract class ShipImpl extends EntityImpl implements Ship {
    private final Direction currDirection;
    protected int health;

    public ShipImpl(Direction direction, int health, Position p) {
        super(p);
        this.currDirection = direction;
        this.health = health;
    }

    @Override
    public void move(Direction direction) {
        if(direction==this.currDirection) {

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
