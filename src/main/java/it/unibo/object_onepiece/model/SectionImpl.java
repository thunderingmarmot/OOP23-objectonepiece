package it.unibo.object_onepiece.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.Bound;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.BiArguments;

public class SectionImpl implements Section {

    private static final int ROWS = World.SECTION_ROWS;
    private static final int COLUMNS = World.SECTION_COLUMNS;

    private final List<Entity> entities = new LinkedList<>();
    private final Bound bound = new Bound(0, 0, ROWS, COLUMNS);

    public final Event<BiArguments<Viewable.Type, Position>> onEntityCreated = Event.get();

    public SectionImpl() {
        Player p = Player.getDefault(this, new Position(4, 4));
        entities.add(p);
    }

    @Override
    public World getWorld() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWorld'");
    }

    @Override
    public Bound getBounds() {
        return this.bound;
    }

    @Override
    public void removeEntityAt(Position position) {
        entities.removeIf(e -> e.getPosition() == position);
    }

    @Override
    public Player getPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPlayer'");
    }

    @Override
    public Optional<Entity> getEntityAt(Position position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEntityAt'");
    }

    @Override
    public List<Entity> getEntities() {
        return entities;
    }

    @Override
    public List<Viewable> getViewables() {
        return entities.stream().filter(e -> e instanceof Viewable).map(e -> (Viewable) e).toList();
    }

    @Override
    public void addEntity(Entity e) {
        if(e instanceof Viewable viewable) {
            onEntityCreated.invoke(new BiArguments<>(viewable.getViewType(), viewable.getViewPosition()));
        }
        // Continue addEntity implementation
    }    
}
