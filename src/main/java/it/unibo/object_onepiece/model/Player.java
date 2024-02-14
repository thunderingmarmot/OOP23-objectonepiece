package it.unibo.object_onepiece.model;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Implementation of the Player interface.
 * @see Player
 */
public final class Player extends Ship {
    private static final int DEFAULT_EXPERIENCE_HEAL_COST = 100;

    private final Event<PlayerUpdatedArgs> onPlayerUpdated;

    private int experience;

    private Player(final Section section,
                   final Position position,
                   final CardinalDirection direction,
                   final int experience,
                   final Event<PlayerUpdatedArgs> onPlayerUpdated,
                   final Weapon weapon,
                   final Sail sail,
                   final Bow bow,
                   final Keel keel) {
        super(section, position, direction, weapon, sail, bow, keel);
        this.onPlayerUpdated = onPlayerUpdated;
        this.experience = experience;
    }

    /**
     * Constructor that creates a default Player.
     * @param spawnSection the Section containing this Player
     * @param spawnPosition the Position this Player spawns at
     */
    protected Player(final Section spawnSection, final Position spawnPosition) {
        this(spawnSection,
             spawnPosition,
             CardinalDirection.NORTH,
             0,
             new Event<>(),
             Weapon.cannon(),
             Sail.sloop(),
             Bow.standard(),
             Keel.standard());
    }

    /**
     * Constructor that creates a Player by copying another in a different Section and a different Position.
     * @param player the Player to copy
     * @param customSection the Section containing the new Player
     * @param customPosition the Position the new Player is at
     */
    protected Player(final Player player, final Section customSection, final Position customPosition) {
        this(customSection,
             customPosition,
             player.getDirection(),
             player.getExperience(),
             player.getPlayerUpdatedEvent(),
             player.getWeapon(),
             player.getSail(),
             player.getBow(),
             player.getKeel());
    }

    /**
     * Constructor that creates a Player by copying another.
     * @param player the Player to copy
     */
    protected Player(final Player player) {
        this(player.getSection(),
             player.getPosition(),
             player.getDirection(),
             player.getExperience(),
             player.getPlayerUpdatedEvent(),
             player.getWeapon(),
             player.getSail(),
             player.getBow(),
             player.getKeel());
    }

    @Override
    protected Player duplicate() {
        return new Player(this);
    }

    /**
     * Checks wether the Player's current position is the same as the one passed as argument.
     * @param position the position to check against
     * @return a boolean that indicates wether the Player is in that same position
     */
    public boolean isInSamePositionAs(final Position position) {
        return this.getPosition().equals(position);
    }

    /**
     * Moves the Player's ship towards a specified position.
     * @param destination the position to reach
     * @return a boolean that indicates wether the Player has moved
     * @see Ship
     */
    public boolean moveTo(final Position destination) {
        final CardinalDirection direction = this.getPosition().whereTo(destination);
        final int distance = this.getPosition().distanceFrom(destination);
        final MoveDetails moveResult = super.move(direction, distance);
        if (moveResult.equals(MoveDetails.BORDER_REACHED)) {
            this.getWorld().createNewSection(
                (newSection) -> new Player(this, newSection, 
                                           this.getPosition().opposite(this.getDirection(), newSection.getBounds())));
        }
        return Enemy.ACTION_SUCCESS_CONDITIONS.contains(moveResult);
    }

    /**
     * Makes the Player shoot at a target position.
     * @param target the position to shoot at
     * @return a ShootReturnType as it's defined in Weapon
     * @see Weapon
     */
    public boolean shootAt(final Position target) {
        return super.shoot(target).hasShooted();
    }

    private <T> Stream<T> getFromShipComponent(final Function<ShipComponent, T> map) {
        return super.getShipComponents().stream().map(map);
    }

    /**
     * Getter for the maximum healths of each component.
     * @return a List of max healths
     * @see ShipComponent
     */
    protected List<Integer> getMaxHealths() {
        return getFromShipComponent((c) -> c.getMaxHealth()).toList();
    }

    /**
     * Getter for the current healths of each component.
     * @return a List of healths
     */
    protected List<Integer> getHealths() {
        return getFromShipComponent((c) -> c.getHealth()).toList();
    }

    /**
     * Getter for the experience private field.
     * @return the currently owned experience value
     */
    protected int getExperience() {
        return this.experience;
    }

    /**
     * Adds experience to the Player's owned experience.
     * @param experienceToAdd the experience value to add
     */
    protected void addExperience(final int experienceToAdd) {
        this.experience += experienceToAdd;
        this.onPlayerUpdated.invoke(new PlayerUpdatedArgs(getHealths(), getMaxHealths(), this.experience));
    }

    /**
     * Subtracts experience to the Player's owned experience.
     * @param experienceToSubtract the experience value to subtract
     */
    protected void subtractExperience(final int experienceToSubtract) {
        this.experience -= experienceToSubtract;
        this.onPlayerUpdated.invoke(new PlayerUpdatedArgs(getHealths(), getMaxHealths(), this.experience));
    }

    /**
     * Fully heals the Player by spending experience.
     */
    public void healWithExperience() {
        if (this.experience >= DEFAULT_EXPERIENCE_HEAL_COST) {
            this.subtractExperience(DEFAULT_EXPERIENCE_HEAL_COST);
            this.heal();
        }
    }

    /**
     * Overridden version of takeDamage to allow event invoking.
     * @see Ship
     */
    @Override
    protected void takeDamage(final int damage, final ShipComponent s) {
        super.takeDamage(damage, s);
        this.onPlayerUpdated.invoke(new PlayerUpdatedArgs(getHealths(), getMaxHealths(), this.experience));
    }

    /**
     * Fully heals the Player when called.
     */
    protected void heal() {
        this.getShipComponents().forEach((c) -> c.setHealth(c.getMaxHealth()));
        this.onPlayerUpdated.invoke(new PlayerUpdatedArgs(getHealths(), getMaxHealths(), this.experience));
    }

    /**
     * Handles Player death.
     * @see Ship
     */
    @Override
    protected void die() {
        super.die();
        if (this.getWorld().getSavedState().isPresent()) {
            this.getWorld().loadSavedSection();
        } else {
            this.getWorld().createNewSection(
                (newSection) -> new Player(newSection, this.getWorld().getPlayerDefaultSpawnPoint()));
        }
    }

    /**
     * Getter for the onPlayerUpdated Event.
     * @return the onPlayerUpdated Event
     * @see Event
     */
    public Event<PlayerUpdatedArgs> getPlayerUpdatedEvent() {
        return this.onPlayerUpdated;
    }
}
