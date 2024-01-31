package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Position;

public abstract class EntityImpl implements Entity {
    final protected Section section;
    protected Position position;

    protected EntityImpl(final Section s, final Position p) {
        this.section = s;
        this.position = p;
    }

    @Override
    public Section getSection() {
       return this.section;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public void remove() {
        this.getSection().removeEntityAt(this.position);
    }
}
