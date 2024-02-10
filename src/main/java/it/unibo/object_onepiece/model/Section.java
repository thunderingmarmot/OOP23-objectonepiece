package it.unibo.object_onepiece.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import de.articdive.jnoise.pipeline.JNoise;
import it.unibo.object_onepiece.model.Entity.EntityCreatedEvent;
import it.unibo.object_onepiece.model.Entity.EntityRemovedEvent;
import it.unibo.object_onepiece.model.Entity.EntityUpdatedEvent;
import it.unibo.object_onepiece.model.Utils.Bound;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.TriArguments;

import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;

/**
 * Implementation of Section interface.
 */
public final class Section {

    private static final int ROWS = World.SECTION_ROWS;
    private static final int COLUMNS = World.SECTION_COLUMNS;
    private static final int ROW_INSET = ROWS / 7;
    private static final int COL_INSET = COLUMNS / 7;
    private static final int GEN_AREA_COLS = ROWS - ROW_INSET;
    private static final int GEN_AREA_ROWS = COLUMNS - COL_INSET;
    private static final double SCALING_FACTOR = 50.5;
    private static final int NOISE_DISPERSION = 50;
    private static final CardinalDirection DEFAULT_DIRECTION = CardinalDirection.NORTH;

    private final Random rand = new Random();
    private final World world;
    private final List<Entity> entities = new LinkedList<>();
    private final Bound bound = new Bound(ROWS, COLUMNS);

    private final EntityAddedEvent onEntityAdded = new EntityAddedEvent();

    /**
     * An Event alias that is used when an Entity is created in a Section.
     * 
     * @see Event
     * @see Section
     */
    public static final class EntityAddedEvent
            extends Event<TriArguments<EntityCreatedEvent, EntityUpdatedEvent, EntityRemovedEvent>> {
        /**
         * A less verbose version of invoke that directly takes the Event arguments.
         * 
         * @param entityName         the name of the Entity class that has been created
         * @param spawnPosition      the position this Entity has been spawned at
         * @param spawnDirection     the direction this Entity has been spawned in
         * @param entityUpdatedEvent the EntityUpdatedEvent of the newly created Entity
         * @see Event
         */
        protected void invoke(final EntityCreatedEvent entityCreatedEvent, final EntityUpdatedEvent entityUpdatedEvent, final EntityRemovedEvent entityRemovedEvent) {
            super.invoke(new TriArguments<>(entityCreatedEvent, entityUpdatedEvent, entityRemovedEvent));
        }
    }

    /**
     * 
     * @param world reference to World object (used to consent islands to save game
     *              state)
     */
    Section(final World world) {
        this.world = world;
    }

    /**
     * Populates entities list using white noise algorithm from JNoise.
     */
    void generateEntities() {
        final int seed = 120350;
        final var whiteNoise = JNoise.newBuilder().white(seed).addModifier(v -> (v + 1) / 2.0).scale(SCALING_FACTOR)
                .build();
        for (int i = ROW_INSET; i < GEN_AREA_ROWS; i++) {
            for (int j = COL_INSET; j < GEN_AREA_COLS; j++) {
                final Position p = new Position(i, j);
                final double noise = whiteNoise.evaluateNoise(i, j);
                final int floored = (int) Math.floor(noise * NOISE_DISPERSION);

                switch (floored) {
                    case 0:
                        /* Don't do anything because water */
                        break;
                    case 1:
                        this.addEntity(Island.getDefault(this, p, DEFAULT_DIRECTION));
                        break;
                    case 2:
                        this.addEntity(Barrel.getDefault(this, p, DEFAULT_DIRECTION));
                        break;
                    case 3:
                        this.addEntity(NavalMine.getDefault(this, p, DEFAULT_DIRECTION));
                        break;
                    case 4:
                        this.addEntity(Enemy.getDefault(this, p));
                        break;
                    default:
                        break;
                }
            }
        }
        this.addEntity(Player.getDefault(this, new Position(1, 1)));

        /** Prints duplicate positions in entities list */
        final Set<Position> items = new HashSet<>();
        entities.stream().filter(n -> !items.add(n.getPosition()))
                .collect(Collectors.toSet())
                .forEach(e -> System.out.println(e.getPosition()));
    }

    World getWorld() {
        return this.world;
    }

    Bound getBounds() {
        return this.bound;
    }

    void removeEntityAt(final Position position) {
        entities.removeIf(e -> e.getPosition() == position);
    }

    Player getPlayer() {
        if (entities.stream().filter(e -> e instanceof Player).count() != 1) {
            throw new IllegalStateException("There is no player in section or there's more than one player");
        }
        final Optional<Player> p = entities.stream().filter(e -> e instanceof Player).map(e -> (Player) e).findFirst();
        if (!p.isPresent()) {
            throw new IllegalStateException("No player found");
        }

        return p.get();
    }

    Optional<Entity> getEntityAt(final Position position) {
        return entities.stream().filter(e -> e.getPosition() == position).findFirst();
    }

    List<Entity> getEntities() {
        return entities;
    }

    void addEntity(final Entity e) {
        onEntityAdded.invoke(e.getEntityCreatedEvent(), e.getEntityUpdatedEvent(), e.getEntityRemovedEvent());
        e.getEntityCreatedEvent().invoke(e.getClass().getSimpleName(),
            e.getPosition(),
            e.getDirection());
        entities.add(e);
    }

    /**
     * @return event to generate entities in view
     */
    public EntityAddedEvent getEntityAddedEvent() {
        return onEntityAdded;
    }
}
