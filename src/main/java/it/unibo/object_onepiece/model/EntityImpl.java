package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Position;

public abstract class EntityImpl implements Entity {
    final protected Position position;
    final protected Section section;

    public EntityImpl(final Position p, final Section s) {
        this.position = p;
        this.section = s;
    }

    @Override
    public Section getSection() {
       return this.getSection();
    }

    @Override
    public Position getPosition() {
        return this.position;
    }
    
}
