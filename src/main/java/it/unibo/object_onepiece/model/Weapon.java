package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import it.unibo.object_onepiece.model.Utils.Position;

/**
 * This class represents a weapon, which is a type of ShipComponent.
 * It defines a method to shoot with the weapon and retrieving maximum damage, 
 * minimum damage and range.
 * It also defines builder methods and builder methods for the available weapons.
 */
public final class Weapon extends ShipComponent {
    /**
     * Enum to define various type of result returned by shoot.
     */
    enum ShootDetails {
        /** 
         * The weapon has shooted succesfully. 
         */
        SHOOTED_SUCCESSFULLY,
        /**
         * The position where you want to shoot is out of range. 
         */
        OUT_OF_SHOOTING_RANGE,
        /** 
         * The weapon is broken. 
         */
        WEAPON_BROKEN
    }

    /**
     * Record returned by shoot.
     * 
     * @param  hasShooted result of the shooting
     * @param  details    details about the shooting result
     */
    record ShootReturnType(boolean hasShooted, ShootDetails details) { }

    private final int maxDamage;
    private final int minDamage;
    private final int attackRange;

    /**
     * Constructor for class Weapon.
     * 
     * @param  max    the maximum damage of the weapon
     * @param  min    the minimum damage of the weapon
     * @param  range  the range of the weapon
     * @param  ship   the ship where the weapon is mounted
     * @param  health the health of the weapon
     */
    protected Weapon(final int max, final int min, final int range, final ShipImpl ship, final int health) {
        super(ship, health);
        this.maxDamage = max;
        this.minDamage = min;
        this.attackRange = range;
    }

    /**
     * This method defines the mechanics of the shooting.
     * 
     * If the weapon health is below or equal to zero, 
     * the weapon is broken so it can't shoot.
     * 
     * If the given position is in range with the weapon range
     * and is inline with the side of the ship, the weapon shoots
     * to the position and deals damage in a 3x3 area, where the
     * center is the maximum damage and around it is minimum damage.
     * To deals damage it calls the hitTarget method for every cell.
     * 
     * Otherwise the cell position is out of range and the weapon
     * doesn't shoot.
     * 
     * @param  position the cell position where the weapon should shoot
     * @return          a ShootReturnType that contains the result of the shooting and the details.
     */
    protected ShootReturnType shoot(final Position position) {
        if (this.getHealth() <= 0) {
            return new ShootReturnType(false, ShootDetails.WEAPON_BROKEN);
        }

        if (this.getShip().getPosition().isInlineWith(position, this.getShip().getDirection()) 
        && this.getShip().getPosition().distanceFrom(position) <= this.attackRange) {
            hitTarget(position, this.maxDamage);

            Utils.getCardinalDirectionsTranslationMap().values()
                                                       .stream()
                                                       .forEach((f) -> hitTarget(f.apply(position), this.minDamage));

            Utils.getOrdinalDirectionsTranslationMap().values()
                                                      .stream()
                                                      .forEach((f) -> hitTarget(f.apply(position), this.minDamage));

            return new ShootReturnType(true, ShootDetails.SHOOTED_SUCCESSFULLY);
        }

        return new ShootReturnType(false, ShootDetails.OUT_OF_SHOOTING_RANGE);
    }

    /**
     * Getter for the maximum damage of the ship weapon.
     * 
     * @return weapon maximum damage.
     */
    protected int getMaxDamage() {
        return this.maxDamage;
    }

    /**
     * Getter for the minimum damage of the ship weapon.
     * 
     * @return weapon minimum damage.
     */
    protected int getMinDamage() {
        return this.minDamage;
    }

    /**
     * Getter for the range of the ship weapon.
     * 
     * @return weapon range.
     */
    protected int getRange() {
        return this.attackRange;
    }

    /**
     * This method is used to deal damage to a random ShipComponent of as ship.
     * 
     * @param  position the position of the target to hit
     * @param  damage   the damage to deal to the target
     */
    private void hitTarget(final Position position, final int damage) {
        final Optional<Entity> ship = this.getShip().getSection().getEntityAt(position);

        if (ship.get() instanceof Ship s) {
            final List<ShipComponent> sc = List.of(this, this.getShip().getSail(), this.getShip().getBow());
            s.takeDamage(damage, sc.stream()
                                   .skip(new Random().nextInt(sc.size()))
                                   .findFirst()
                                   .orElse(null));
        }
    }

    /**
     * Builder for the cannon, the standard type of weapon.
     * 
     * @param  ship the ship that should be assigned to the weapon
     * @return      weapon with his standard stats.
     */
    protected static Weapon cannon(final ShipImpl ship) {
        final int maxDamage = 20;
        final int minDamage = 10;
        final int range = 3;
        final int health = 100;

        return new Weapon(maxDamage, minDamage, range, ship, health);
    }

    /**
     * Builder for the railgun, the most powerful weapon for damage and range,
     * but more fragile.
     * 
     * @param  ship the ship that should be assigned to the weapon
     * @return      weapon with his standard stats.
     */
    protected static Weapon railgun(final ShipImpl ship) {
        final int maxDamage = 50;
        final int minDamage = 0;
        final int range = 5;
        final int health = 70;

        return new Weapon(maxDamage, minDamage, range, ship, health);
    }

    /**
     * Builder for the heavycannon, the best weapon for damage and health,
     * but with very short range.
     * 
     * @param  ship the ship that should be assigned to the weapon
     * @return      weapon with his standard stats.
     */
    protected static Weapon heavycannon(final ShipImpl ship) {
        final int maxDamage = 80;
        final int minDamage = 40;
        final int range = 1;
        final int health = 120;

        return new Weapon(maxDamage, minDamage, range, ship, health);
    }
}
