package it.unibo.object_onepiece.model;

public abstract class EntityImpl implements Entity {
    private Position position;

    public EntityImpl(Position p) {
        this.position = p;
    }

    @Override
    public Section getSection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSection'");
    }

    @Override
    public Position getPosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPosition'");
    }
    
}
