package it.unibo.object_onepiece.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import de.articdive.jnoise.pipeline.JNoise;
import it.unibo.object_onepiece.model.Utils.Bound;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.enemy.Enemy;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.TriArguments;
import it.unibo.object_onepiece.model.ship.Ship;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;

/**
 * Implementation of Section interface.
 */
public final class SectionImpl implements Section {

    private static final int ROWS = World.SECTION_ROWS;
    private static final int COLUMNS = World.SECTION_COLUMNS;
    private static final int ROW_INSET = ROWS / 7;
    private static final int COL_INSET = COLUMNS / 7;
    private static final int GEN_AREA_COLS = ROWS - ROW_INSET;
    private static final int GEN_AREA_ROWS = COLUMNS - COL_INSET;

    private final List<Entity> entities = new LinkedList<>();
    private final Bound bound = new Bound(0, 0, ROWS, COLUMNS);

    private final Event<TriArguments<Class<? extends Entity>, Position, Optional<CardinalDirection>>> 
    onEntityCreated = Event.get();
    /**
     * Populates entities list using white noise algorithm from JNoise.
     */
    public void generateEntities() {
        Random m = new Random();
        var whiteNoise = JNoise.newBuilder().white(1077).addModifier(v -> (v + 1) / 2.0).scale(50.5).build();
        for (int i = ROW_INSET; i < GEN_AREA_ROWS; i++) {
            for (int j = COL_INSET; j < GEN_AREA_COLS; j++) {
                Position p = new Position(i, j);
                double noise = whiteNoise.evaluateNoise(i, j);
                int floored = (int) Math.floor(noise * 50);
                switch (floored) {
                    case 0:
                        /* Don't do anything because water */
                        break;
                    case 1:
                        this.addEntity(Island.getDefault(this, p));
                        break;
                    case 2:
                        this.addEntity(Barrel.getDefault(this, p, 50));
                        break;
                    case 3:
                        this.addEntity(NavalMine.getDefault(this, p));
                    case 4:
                        this.addEntity(Enemy.getDefault(this, p));
                    default:
                        break;
                }
            }
        }
        this.addEntity(Player.getDefault(this, new Position(0, 0)));

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
    public void removeEntityAt(final Position position) {
        entities.removeIf(e -> e.getPosition() == position);
    }

    @Override
    public Player getPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPlayer'");
    }

    @Override
    public Optional<Entity> getEntityAt(final Position position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEntityAt'");
    }

    @Override
    public List<Entity> getEntities() {
        return entities;
    }

    @Override
    public void addEntity(final Entity e) {
        Optional<CardinalDirection> direction = e instanceof Ship s ? Optional.of(s.getDirection()) : Optional.empty();
        onEntityCreated.invoke(new TriArguments<>(e.getClass(), e.getPosition(), direction));
        entities.add(e);
    }

    /**
     * @return event to generate entities in view
     */
    public Event<TriArguments<Class<? extends Entity>, Position, Optional<CardinalDirection>>> getEntityCreatedEvent() {
        return onEntityCreated;
    }
}
