package it.unibo.object_onepiece.model.ship;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import it.unibo.object_onepiece.model.Entity;
import it.unibo.object_onepiece.model.Utils;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Class that defines the implementation of the Weapon interface.
 */
public final class WeaponImpl extends ShipComponentImpl implements Weapon {
    private final int maxDamage;
    private final int minDamage;
    private final int attackRange;

    /**
     * Constructor for class WeaponImpl.
     * 
     * @param  max    the maximum damage of the weapon
     * @param  min    the minimum damage of the weapon
     * @param  range  the range of the weapon
     * @param  health the health of the weapon
     */
    protected WeaponImpl(final int max, final int min, final int range, final int health) {
        super(health);
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
    @Override
    public ShootReturnType shoot(final Position position) {
        if (this.getHealth() <= 0) {
            return new ShootReturnType(false, ShootDetails.WEAPON_BROKEN);
        }

        if ((this.getShip().getPosition().isInlineWith(position, this.getShip().getDirection())) 
        && (this.getShip().getPosition().distanceFrom(position) <= this.attackRange)) {
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
     * Get method for the maximum damage of the ship weapon.
     * 
     * @return weapon maximum damage.
     */
    @Override
    public int getMaxDamage() {
        return this.maxDamage;
    }

    /**
     * Get method for the minimum damage of the ship weapon.
     * 
     * @return weapon minimum damage.
     */
    @Override
    public int getMinDamage() {
        return this.minDamage;
    }

    /**
     * Get method for the range of the ship weapon.
     * 
     * @return weapon range.
     */
    @Override
    public int getRange() {
        return this.attackRange;
    }

    /**
     * This method is used to deal damage to a random ShipComponent of as ship.
     * 
     * @param  position the position of the target to hit
     * @param  damage   the damage to deal to the target
     */
    private void hitTarget(final Position position, final int damage) {
        Optional<Entity> ship = this.getShip().getSection().getEntityAt(position);
        if (ship.get() instanceof Ship s) {
            List<ShipComponent> sc = List.of(this, this.getShip().getSail(), this.getShip().getBow());
            s.takeDamage(damage, sc.stream()
                                   .skip(new Random().nextInt(sc.size()))
                                   .findFirst()
                                   .orElse(null));
        }
    }
}
