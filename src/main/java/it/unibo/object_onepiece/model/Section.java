package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.Bound;
import it.unibo.object_onepiece.model.Utils.Position;

/**
* Interface that models a Section of the world, it's itself divided in cells
* that can contain an Entity 
*/
public interface Section {
    public World getWorld();

    public Bound getBounds();

    public Optional<Entity> getEntityAt(Position position);
    public List<Entity> getEntities();

    public void removeEntityAt(Position position);

    public Player getPlayer();
}
