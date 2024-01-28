package it.unibo.object_onepiece.model.Ship;

import java.util.Optional;

import it.unibo.object_onepiece.model.Entity;
import it.unibo.object_onepiece.model.Utils.Position;

public class WeaponImpl implements Weapon {
    private final int maxDamage;
    private final int minDamage;
    private final int attackRange;
    private final Ship ship;
    private int health;

    public WeaponImpl(final int max, final int min, final int range, final Ship ship, final int health) {
        this.maxDamage = max;
        this.minDamage = min;
        this.attackRange = range;
        this.ship = ship;
        this.health = health;
    }

    @Override
    public ShootReturnType shoot(final Position position) {
        if(this.health <= 0) {
            return new ShootReturnType(false, ShootDetails.WEAPON_BROKEN);
        }

        if(this.ship.getPosition().isInlineWith(position, this.ship.getDirection()) && this.ship.getPosition().distanceFrom(position) <= this.attackRange) {
            hitTarget(position, this.maxDamage);
            Position.directionPositions.values().stream().forEach((f) -> hitTarget(f.apply(position), this.minDamage));
            Position.diagonalPositions.values().stream().forEach((f) -> hitTarget(f.apply(position), this.minDamage));
            return new ShootReturnType(true, ShootDetails.SHOOTED_SUCCESSFULLY);
        }

        return new ShootReturnType(false, ShootDetails.OUT_OF_SHOOTING_RANGE);
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public Ship getShip() {
        return this.ship;
    }

    @Override
    public int getMaxDamage() {
        return this.maxDamage;
    }

    @Override
    public int getMinDamage() {
        return this.minDamage;
    }

    @Override
    public int getRange() {
        return this.attackRange;
    }

    @Override
    public int getHealth() {
        return this.health;
    }
    
    private void hitTarget(final Position position, final int damage) {
        Optional<Entity> ship = this.ship.getSection().getEntityAt(position);
        if(ship.get() instanceof Ship s) {
            s.takeDamage(damage, s.getBow());
        }
    }
}
