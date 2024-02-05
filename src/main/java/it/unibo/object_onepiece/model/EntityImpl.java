package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.*;

public abstract class EntityImpl implements Entity {
    final private Section section;
    private Position position;

    public final Event<BiArgument<Position>> onPositionChanged = Event.get();
    public final Event<Argument<Position>> onEntityRemoved = Event.get();

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
        onPositionChanged.invoke(new BiArgument<>(this.position, newPosition));
        this.position = newPosition;
    }

    protected void remove() {
        onEntityRemoved.invoke(new Argument<>(this.position));
        this.getSection().removeEntityAt(this.position);
    }
}
