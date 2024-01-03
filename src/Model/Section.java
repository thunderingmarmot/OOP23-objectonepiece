package Model;

import java.util.List;
import java.util.Optional;

import Model.Entity.Position;
import Model.Entity.Type;

// Interface that models a Section of the world, it's itself divided in cells
// that can contain an Entity
public interface Section {
    public <T extends Entity> Optional<T> getEntityAt(Position position);
    public List<Entity> getEntitiesOfType(Type type);
    public List<Entity> getEntities();

    public Player getPlayer();
}
