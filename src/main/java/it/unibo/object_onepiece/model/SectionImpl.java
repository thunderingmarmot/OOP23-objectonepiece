package it.unibo.object_onepiece.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.Bound;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

public class SectionImpl implements Section {

    private final List<Entity> entities = new LinkedList<>();

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBounds'");
    }

    @Override
    public void removeEntityAt(Position position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeEntityAt'");
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
    
}
