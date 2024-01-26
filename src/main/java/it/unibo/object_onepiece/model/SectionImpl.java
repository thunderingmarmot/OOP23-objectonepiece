package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.Position;

public class SectionImpl implements Section {
   private final List<Entity> board;
   private final World world;

    public SectionImpl(final World world,List<Entity> board){
        this.world = world;
        this.board = board;
    }
    @Override
    public <T extends Entity> Optional<T> getEntityAt(Position position, Class<T> entityClass) {
        Optional<Entity> entity = find(position);

        if (entity != null && entityClass.isInstance(entity)) {
            return Optional.of(entityClass.cast(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public <T extends Entity> List<T> getEntities(Class<T> entityClass) {
        //the cast is safe since filter, blocks other types.
        return (List<T>) board.stream().filter((x) -> entityClass.isInstance(x)).toList();
    }

    @Override
    public void removeEntityAt(Position position) {
        Optional<Entity> entity = find(position);
        if(entity.isPresent()){
            board.remove(entity.get());
        } else{
            System.out.println("entity at position " + position.toString() + "not found, during removal");
        }
        
    }

    @Override
    public Player getPlayer() {
        return getEntities(Player.class).get(0);
    }

    private Optional<Entity> find(Position position){
        return board.stream().filter(x -> x.getPosition().equals(position)).findFirst();
    }

    
}
