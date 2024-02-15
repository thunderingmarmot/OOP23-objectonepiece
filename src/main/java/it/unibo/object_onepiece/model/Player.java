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

    private final Event<PlayerUpdatedArgs> onPlayerUpdated = new Event<>();

    private int experience;

    /**
     * Constructor that creates a default Player.
     * @param spawnSection the Section containing this Player
     * @param spawnPosition the Position this Player spawns at
     */
    protected Player(final Section spawnSection, final Position spawnPosition) {
        super(spawnSection, spawnPosition, CardinalDirection.NORTH, Weapon.cannon(), Sail.sloop(), Bow.standard(), Keel.standard());
        this.experience = 0;
    }

    /**
     * Constructor that creates a Player by copying another in a different Section and a different Position.
     * @param origin the Player to copy from
     * @param customSection the Section containing the new Player
     * @param customPosition the Position the new Player is at
     */
    protected Player(final Player origin, final Section customSection) {
        super(customSection, origin.getPosition(), origin.getDirection(), origin.getWeapon(), origin.getSail(), origin.getBow(), origin.getKeel());
        this.experience = origin.experience;
    }

    /**
     * Constructor that creates a Player by copying another.
     * @param origin the Player to copy from
     */
    protected Player(final Player origin) {
        super(origin);
        this.experience = origin.experience;
    }

    @Override
    protected Player copy() {
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
                (newSection) -> {
                    Player player = new Player(this, newSection);
                    player.setPosition(player.getPosition().opposite(player.getDirection(), newSection.getBounds()));
                    return player;
                });
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
        // tryInvoke is needed here because if super.takeDamage decides the Ship has died,
        // the Player.die() will be executed first, invalidating all events.
        this.onPlayerUpdated.tryInvoke(new PlayerUpdatedArgs(getHealths(), getMaxHealths(), this.experience));
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
        this.onPlayerUpdated.lastInvoke(new PlayerUpdatedArgs(getHealths(), getMaxHealths(), this.experience));
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
