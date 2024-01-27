package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

public class EnemyImpl extends ShipImpl implements Enemy {

    public EnemyImpl(Section section, Position position, Direction direction, int health, Weapon weapon) {
        super(section, position, direction, health, weapon);
    }

    @Override
    public void goNext() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'goNext'");
    }

    @Override
    public State getCurrentState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentState'");
    }
    
}
