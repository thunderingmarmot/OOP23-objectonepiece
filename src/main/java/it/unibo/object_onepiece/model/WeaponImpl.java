package it.unibo.object_onepiece.model;

import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.Position;

public class WeaponImpl implements Weapon {
    private final int maxDamage;
    private final int minDamage;
    private final int attackRange;
    private final Ship ship;

    public WeaponImpl(final int max, final int min, final int range, final Ship ship) {
        this.maxDamage = max;
        this.minDamage = min;
        this.attackRange = range;
        this.ship = ship;
    }

    @Override
    public boolean shoot(final Position position) {
        if(this.ship.getPosition().isInlineWith(position, this.ship.getDirection()) && this.ship.getPosition().distanceFrom(position) <= this.attackRange) {
            hitTarget(position, this.maxDamage);
            Position.directionPositions.values().stream().forEach((f) -> hitTarget(f.apply(position), this.minDamage));
            Position.diagonalPositions.values().stream().forEach((f) -> hitTarget(f.apply(position), this.minDamage));
            return true;
        }
        return false;
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

    private void hitTarget(final Position position, final int damage) {
        Optional<Entity> ship = this.ship.getSection().getEntityAt(position);
        if(ship.get() instanceof Ship s) {
            s.takeDamage(damage);
        }
    }

}
