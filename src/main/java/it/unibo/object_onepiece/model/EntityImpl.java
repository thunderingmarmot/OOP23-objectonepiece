package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs;
import it.unibo.object_onepiece.model.events.EventImpl;
import it.unibo.object_onepiece.model.events.EventArgs.NoArgs;
import it.unibo.object_onepiece.model.events.EventArgs.ObjectChanged;
import it.unibo.object_onepiece.model.events.EventArgs.ValueChanged;

public abstract class EntityImpl implements Entity {
    final protected Section section;
    protected Position position;

    public final Event<ValueChanged<Position>> onPositionChanged = new EventImpl<>();
    public final Event<ObjectChanged<Entity>> onEntityRemoved = new EventImpl<>();

    protected EntityImpl(final Section s, final Position p) {
        this.section = s;
        this.setPosition(p);
    }

    @Override
    public Section getSection() {
       return this.section;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    protected void setPosition(Position newPosition) {
        onPositionChanged.invoke(EventArgs.valueChanged(this.position, newPosition));
        this.position = newPosition;
    }

    protected void remove() {
        onEntityRemoved.invoke(EventArgs.objectChanged(this));
        this.getSection().removeEntityAt(this.position);
    }
}
