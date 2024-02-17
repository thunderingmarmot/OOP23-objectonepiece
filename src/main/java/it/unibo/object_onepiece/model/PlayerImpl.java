package it.unibo.object_onepiece.model;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * A Player, a Ship that represents the Player itself.
 * Different from its counter-part, the Enemy.
 * @see PlayerImpl
 */
@Viewable(getName = "Player")
public final class PlayerImpl extends Ship implements Player {
    private static final int DEFAULT_EXPERIENCE_HEAL_COST = 100;

    private final Event<PlayerUpdatedArgs> onPlayerUpdated = new Event<>();

    private int experience;

    /**
     * Constructor that creates a default Player.
     * @param spawnSection the Section containing this Player
     * @param spawnPosition the Position this Player spawns at
     */
    protected PlayerImpl(final Section spawnSection, final Position spawnPosition) {
        super(spawnSection,
              spawnPosition,
              CardinalDirection.NORTH,
              Weapon.cannon(),
              Sail.sloop(),
              Bow.standard(),
              Keel.standard());
        this.experience = 0;
    }

    /**
     * Constructor that creates a Player by copying another in a different Section and a different Position.
     * @param origin the Player to copy from
     * @param customSection the Section containing the new Player
     */
    protected PlayerImpl(final PlayerImpl origin, final Section customSection) {
        super(customSection,
              origin.getPosition(),
              origin.getDirection(),
              origin.getWeapon(),
              origin.getSail(),
              origin.getBow(),
              origin.getKeel());
        this.experience = origin.experience;
    }

    /**
     * Constructor that creates a Player by copying another.
     * @param origin the Player to copy from
     */
    protected PlayerImpl(final PlayerImpl origin) {
        super(origin);
        this.experience = origin.experience;
    }

    @Override
    protected PlayerImpl copy() {
        return new PlayerImpl(this);
    }

    /**
     * Moves the Player's ship towards a specified position.
     * @param destination the position to reach
     * @return a boolean that indicates wether the Player has moved
     * @see Ship
     */
    @Override
    public boolean moveTo(final Position destination) {
        if (this.isShipDead()) {
            this.die();
            return false;
        }

        final CardinalDirection direction = this.getPosition().whereTo(destination);
        final int distance = this.getPosition().distanceFrom(destination);
        final MoveDetails moveResult = super.move(direction, distance);
        if (moveResult.equals(MoveDetails.BORDER_REACHED)) {
            this.getWorld().createNewSection(
                (newSection) -> {
                    final PlayerImpl player = new PlayerImpl(this, newSection);
                    player.setPosition(player.getPosition().opposite(player.getDirection(), newSection.getBounds()));
                    return player;
                });
        }
        return EnemyImpl.ACTION_SUCCESS_CONDITIONS.contains(moveResult);
    }

    /**
     * Makes the Player shoot at a target position.
     * @param target the position to shoot at
     * @return a ShootReturnType as it's defined in Weapon
     * @see Weapon
     */
    @Override
    public boolean shootAt(final Position target) {
        if (this.isShipDead()) {
            this.die();
            return false;
        }

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
     * Getter for the names of each component.
     * @return a List of healths
     */
    protected List<String> getNames() {
        return getFromShipComponent((c) -> c.getClass().getSimpleName()).toList();
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
        this.onPlayerUpdated.invoke(new PlayerUpdatedArgs(getNames(), getHealths(), getMaxHealths(), this.experience));
    }

    /**
     * Subtracts experience to the Player's owned experience.
     * @param experienceToSubtract the experience value to subtract
     */
    protected void subtractExperience(final int experienceToSubtract) {
        this.experience -= experienceToSubtract;
        this.onPlayerUpdated.invoke(new PlayerUpdatedArgs(getNames(), getHealths(), getMaxHealths(), this.experience));
    }

    /**
     * Fully heals the Player by spending experience.
     */
    @Override
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
        this.onPlayerUpdated.invoke(new PlayerUpdatedArgs(getNames(), getHealths(), getMaxHealths(), this.experience));
    }

    /**
     * Fully heals the Player when called.
     */
    protected void heal() {
        this.getShipComponents().forEach((c) -> c.setHealth(c.getMaxHealth()));
        this.onPlayerUpdated.invoke(new PlayerUpdatedArgs(getNames(), getHealths(), getMaxHealths(), this.experience));
    }

    /**
     * Handles Player death.
     * @see Ship
     */
    @Override
    protected void die() {
        super.die();
        this.onPlayerUpdated.invalidate();
        if (this.getWorld().getSavedState().isPresent()) {
            this.getWorld().loadSavedSection();
        } else {
            this.getWorld().createNewSection(
                (newSection) -> new PlayerImpl(newSection, this.getWorld().getPlayerDefaultSpawnPoint()));
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
