package it.unibo.object_onepiece.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import de.articdive.jnoise.pipeline.JNoise;
import it.unibo.object_onepiece.model.Utils.Bound;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.Entity.EntityCreatedArgs;
import it.unibo.object_onepiece.model.Entity.EntityUpdatedArgs;
import it.unibo.object_onepiece.model.Entity.EntityRemovedArgs;
<<<<<<< HEAD
import it.unibo.object_onepiece.model.events.Event;
=======

>>>>>>> 4b69a596fba2b27918611efabed1b4efe61a0ab6
import java.util.stream.Collectors;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Implementation of Section interface.
 */
public final class Section {
    private static final double SCALING_FACTOR = 50.5;
    private static final int NOISE_DISPERSION = 50;
    
    private final int ROWS;
    private final int COLUMNS;
    private final int ROW_INSET;
    private final int COL_INSET;
    private final int GEN_AREA_COLS;
    private final int GEN_AREA_ROWS;
    private final WorldImpl world;
    private final List<Entity> entities;
    private final Bound bound;
    public record EntityAddedArgs(Event<EntityCreatedArgs> onEntityCreated,
                                  Event<EntityUpdatedArgs> onEntityUpdated,
                                  Event<EntityRemovedArgs> onEntityRemoved) { }
    private Event<EntityAddedArgs> onEntityAdded = new Event<>();

    public record PlayerAddedArgs(Event<PlayerUpdatedArgs> onPlayerUpdated) { }
    private Event<PlayerAddedArgs> onPlayerAdded = new Event<>();

    /**
     * 
     * @param world reference to World object (used to consent islands to save game
     *              state)
     */
    Section(final WorldImpl world) {
        this.ROWS = world.getMapRows();
        this.COLUMNS = world.getMapCols();
        this.ROW_INSET = this.ROWS / 7;
        this.COL_INSET = this.COLUMNS / 7;
        this.GEN_AREA_ROWS = this.ROWS - ROW_INSET;
        this.GEN_AREA_COLS = this.COLUMNS - ROW_INSET;
        this.bound = new Bound(this.ROWS, this.COLUMNS);
        this.world = world;
        entities = new LinkedList<>();
    }
    /**
     * Constructor for copying.
     * @param s Section to copy
     */
    Section(Section s) {
        this.ROWS = s.ROWS;
        this.COLUMNS = s.COLUMNS;
        this.ROW_INSET = s.ROW_INSET;
        this.COL_INSET = s.COL_INSET;
        this.GEN_AREA_ROWS = s.GEN_AREA_ROWS;
        this.GEN_AREA_COLS = s.GEN_AREA_COLS;
        this.bound = s.bound;
        this.world = s.world;
        this.entities = new ArrayList<>(s.entities);
    }
    
    /**
     * Populates entities list using white noise algorithm from JNoise.
     */
    void generateEntities() {
        final int seed = Utils.getRandom().nextInt();
        final var whiteNoise = JNoise.newBuilder().white(seed).addModifier(v -> (v + 1) / 2.0).scale(SCALING_FACTOR)
            .build();
        for (int i = ROW_INSET; i < GEN_AREA_ROWS - 1; i++) {
            for (int j = COL_INSET; j < GEN_AREA_COLS - 1; j++) {
                final Position p = new Position(i, j);
                final double noise = whiteNoise.evaluateNoise(i, j);
                final int floored = (int) Math.floor(noise * NOISE_DISPERSION);

                final CardinalDirection d = CardinalDirection.values()[Utils.getRandom().nextInt(CardinalDirection.values().length)];

                switch (floored) {
                    case 0:
                        /* Don't do anything because water */
                        break;
                    case 1:
                        this.addEntity(new Island(this, p, d));
                        break;
                    case 2:
                        this.addEntity(new Barrel(this, p, d));
                        break;
                    case 3:
                        this.addEntity(new NavalMine(this, p, d));
                        break;
                    case 4:
                        this.addEntity(new Enemy(this, p));
                        break;
                    case 5:
                        this.addEntity(new Enemy(this, p));
                        break;
                    default:
                        break;
                }
            }
        }
        /** Prints duplicate positions in entities list */
        final Set<Position> items = new HashSet<>();
        entities.stream().filter(n -> !items.add(n.getPosition()))
                .collect(Collectors.toSet())
                .forEach(e -> System.out.println(e.getPosition()));
    }

    WorldImpl getWorld() {
        return this.world;
    }

    Bound getBounds() {
        return this.bound;
    }

    void removeEntityAt(final Position position) {
        entities.stream()
            .filter(e -> e.getPosition().equals(position))
            .findFirst().ifPresent(e -> 
                {
                    entities.remove(e);
                    e.getEntityRemovedEvent().invoke(new EntityRemovedArgs(position));
                }
            );
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
        return entities.stream().filter(e -> e.getPosition().equals(position)).findFirst();
    }

    List<Entity> getEntities() {
        return entities;
    }

    void addPlayer(final Player p) {
        if  (entities.stream().anyMatch(e -> e instanceof Player)) {
            throw new IllegalStateException("Player already exists. Cannot generate it again");
        } else {
            removeEntityAt(p.getPosition());
            this.onPlayerAdded.invoke(new PlayerAddedArgs(p.getPlayerUpdatedEvent()));
            p.getPlayerUpdatedEvent().invoke(new PlayerUpdatedArgs(p.getHealths(), p.getMaxHealths(), p.getExperience()));
            addEntity(p);
        }
    }

    void addEntity(final Entity e) {
        this.onEntityAdded.invoke(new EntityAddedArgs(
            e.getEntityCreatedEvent(), e.getEntityUpdatedEvent(), e.getEntityRemovedEvent()));
        e.getEntityCreatedEvent().invoke(new EntityCreatedArgs(
            e.getClass().getSimpleName(), e.getPosition(), e.getDirection()));
        entities.add(e);
    }

    Event<EntityAddedArgs> getEntityAddedEvent() {
        return onEntityAdded;
    }

    Event<PlayerAddedArgs> getPlayerAddedEvent() {
        return onPlayerAdded;
    }

    void entityRemovedEventInvoke() {
        entities.forEach(e -> e.getEntityRemovedEvent().invoke(new EntityRemovedArgs(e.getPosition())));
    }
}
