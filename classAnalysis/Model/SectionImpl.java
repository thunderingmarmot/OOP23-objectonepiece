package Model;

import java.util.List;
import java.util.Optional;

import Model.Entity.Position;

public class SectionImpl implements Section {

    @Override
    public <T extends Entity> Optional<T> getEntityAt(Position position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEntityAt'");
    }

    @Override
    public <T extends Entity> List<T> getEntities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEntities'");
    }

    @Override
    public Player getPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPlayer'");
    }
    
}
