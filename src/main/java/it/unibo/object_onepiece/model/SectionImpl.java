package it.unibo.object_onepiece.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import de.articdive.jnoise.pipeline.JNoise;
import it.unibo.object_onepiece.model.Utils.Bound;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.BiArguments;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;

public class SectionImpl implements Section {

    private static final int ROWS = World.SECTION_ROWS;
    private static final int COLUMNS = World.SECTION_COLUMNS;

    private final List<Entity> entities = new LinkedList<>();
    private final Bound bound = new Bound(0, 0, ROWS, COLUMNS);

    public final Event<BiArguments<Class<? extends Entity>, Position>> onEntityCreated = Event.get();

    public SectionImpl() {
        var whiteNoise = JNoise.newBuilder().white(325080).scale(1 / 2.0).addModifier(v -> (v + 1) / 2.0).build();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Position p = new Position(i, j);
                double noise = whiteNoise.evaluateNoise(i, j);
                //System.out.println(noise + "Floored: " + Math.floor(noise * 4));
                int floored = (int)Math.floor(noise * 4);
                switch (floored) {
                    case 1:
                        entities.add(Island.getDefault(this, p));
                        break;
                    case 2:
                        entities.add(Barrel.getDefault(this, p));
                        break;
                    case 3:
                        entities.add(Player.getDefault(this, p));
                        break;
                    default:
                        break;
                }
            }
        }

        Set<Position> items = new HashSet<>();
        entities.stream().filter(n -> !items.add(n.getPosition()))
        .collect(Collectors.toSet())
        .forEach(e -> System.out.println(e.getPosition()));
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
        onEntityCreated.invoke(new BiArguments<>(e.getClass(), e.getPosition()));
        // Continue addEntity implementation
    }    
}
