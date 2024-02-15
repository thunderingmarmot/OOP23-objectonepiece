package it.unibo.object_onepiece.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import de.articdive.jnoise.pipeline.JNoise;
import it.unibo.object_onepiece.model.Utils.Bound;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.Entity.EntityCreatedArgs;
import it.unibo.object_onepiece.model.Entity.EntityUpdatedArgs;
import it.unibo.object_onepiece.model.Entity.EntityRemovedArgs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;

/**
 * Implementation of Section interface.
 */
public final class Section {
    private static final double SCALING_FACTOR = 50.5;
    private static final int NOISE_DISPERSION = 50;
    private static final int INSET_FACTOR = 7;

    private final int rows;
    private final int columns;
    private final int rowInset;
    private final int colInset;
    private final int genAreaCols;
    private final int genAreaRows;
    private final WorldImpl world;
    private final List<Entity> entities;
    private final Bound bound;
    /**
     * This map is used to associate noise values with an object. The enemy is repeated two times for higher frequency.
     */
    private final Map<Integer, BiFunction<Position, CardinalDirection, Entity>> getEntity = new HashMap<>(Map.of(
        0, (p, d) -> new Island(this, p, d),
        1, (p, d) -> new Barrel(this, p, d),
        2, (p, d) -> new NavalMine(this, p, d),
        3, (p, d) -> new Enemy(this, p),
        4, (p, d) -> new Enemy(this, p)
    ));
    /**
     * Record for the entity concerning Events.
     * 
     * @param onEntityCreated entity creation event
     * @param onEntityUpdated entity update event
     * @param onEntityRemoved entity removed event
     */
    public record EntityAddedArgs(
        Event<EntityCreatedArgs> onEntityCreated,
        Event<EntityUpdatedArgs> onEntityUpdated,
        Event<EntityRemovedArgs> onEntityRemoved
    ) { }
    /**
     * Record for the player concerning Events.
     * 
     * @param onPlayerUpdated player information (health, xp etc.) update event
     */
    public record PlayerAddedArgs(Event<PlayerUpdatedArgs> onPlayerUpdated) { }

    private final Event<EntityAddedArgs> onEntityAdded = new Event<>();
    private final Event<PlayerAddedArgs> onPlayerAdded = new Event<>();
    /**
     * 
     * @param world reference to World object (used to consent islands to save game state)
     */
    Section(final WorldImpl world) {
        this.rows = world.getMapRows();
        this.columns = world.getMapCols();
        this.rowInset = this.rows / INSET_FACTOR;
        this.colInset = this.columns / INSET_FACTOR;
        this.genAreaRows = this.rows - rowInset;
        this.genAreaCols = this.columns - rowInset;
        this.bound = new Bound(this.rows, this.columns);
        this.world = world;
        entities = new LinkedList<>();
    }
    /**
     * Constructor for copying.
     * @param s Section to copy
     */
    Section(final Section s) {
        this.rows = s.rows;
        this.columns = s.columns;
        this.rowInset = s.rowInset;
        this.colInset = s.colInset;
        this.genAreaRows = s.genAreaRows;
        this.genAreaCols = s.genAreaCols;
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
        for (int i = rowInset; i < genAreaRows - 1; i++) {
            for (int j = colInset; j < genAreaCols - 1; j++) {
                final Position p = new Position(i, j);
                final double noise = whiteNoise.evaluateNoise(i, j);
                final int floored = (int) Math.floor(noise * NOISE_DISPERSION);

                final CardinalDirection d = 
                    CardinalDirection.values()[Utils.getRandom().nextInt(CardinalDirection.values().length)];

                if (getEntity.containsKey(floored)) {
                    this.addEntity(getEntity.get(floored).apply(p, d));
                }

            }
        }
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
            .findFirst().ifPresent(e -> {
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
