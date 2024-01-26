package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Position;

public abstract class EntityImpl implements Entity {
    protected Position position;

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
        return this.position;
    }
    
}
