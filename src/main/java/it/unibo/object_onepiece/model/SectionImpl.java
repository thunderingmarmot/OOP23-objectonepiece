package it.unibo.object_onepiece.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import de.articdive.jnoise.modules.octavation.OctavationModule;
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
    private static final int ROW_INSET = ROWS / 7;
    private static final int COL_INSET = COLUMNS / 7;
    private static final int GEN_AREA_COLS = ROWS - ROW_INSET;
    private static final int GEN_AREA_ROWS = COLUMNS - COL_INSET;

    private final List<Entity> entities = new LinkedList<>();
    private final Bound bound = new Bound(0, 0, ROWS, COLUMNS);

    private final Event<BiArguments<Class<? extends Entity>, Position>> onEntityCreated = Event.get();

    private void generateEntities() {
        Random m = new Random();
        var whiteNoise = JNoise.newBuilder().white(m.nextInt()).addModifier(v -> (v + 1) / 2.0).scale(50.5).build();
        //var octavatedWhite = OctavationModule.newBuilder().setNoiseSource(whiteNoise).setOctaves(1).setGain(0.9).build();
        for (int i = ROW_INSET; i < GEN_AREA_ROWS; i++) {
            for (int j = COL_INSET; j < GEN_AREA_COLS; j++) {
                Position p = new Position(i, j);
                double noise = whiteNoise.evaluateNoise(i, j);
                int floored = (int)Math.floor(noise * 50);
                switch (floored) {
                    case 0:
                        /* Don't do anything because water */
                        break;
                    case 1:
                        this.addEntity(Island.getDefault(this, p));
                        break;
                    case 2:
                        this.addEntity(Barrel.getDefault(this, p));
                        break;
                    case 3:
                        this.addEntity(NavalMine.getDefault(this, p));
                    default:
                        break;
                }
            }
        }

        /** Prints duplicate positions in entities list*/
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
        entities.add(e);
    }  
    
    public Event<BiArguments<Class<? extends Entity>, Position>> getEntityCreatedEvent() {
        return onEntityCreated;
    }
}
