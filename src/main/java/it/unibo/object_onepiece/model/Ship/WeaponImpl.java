package it.unibo.object_onepiece.model.Ship;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import it.unibo.object_onepiece.model.Entity;
import it.unibo.object_onepiece.model.Utils.Position;

public class WeaponImpl extends ShipComponentImpl implements Weapon {
    private final int maxDamage;
    private final int minDamage;
    private final int attackRange;

    public WeaponImpl(final int max, final int min, final int range, final Ship ship, final int health) {
        super(ship, health);
        this.maxDamage = max;
        this.minDamage = min;
        this.attackRange = range;
    }

    @Override
    public ShootDetails shoot(final Position position) {
        if(this.getHealth() <= 0) {
            return ShootDetails.WEAPON_BROKEN;
        }

        if((this.getShip().getPosition().isInlineWith(position, this.getShip().getDirection())) && 
        (this.getShip().getPosition().distanceFrom(position) <= this.attackRange)) {
            hitTarget(position, this.maxDamage);
            Position.directionPositions.values().stream().forEach((f) -> hitTarget(f.apply(position), this.minDamage));
            Position.diagonalPositions.values().stream().forEach((f) -> hitTarget(f.apply(position), this.minDamage));
            return ShootDetails.SHOOTED_SUCCESSFULLY;
        }

        return ShootDetails.OUT_OF_SHOOTING_RANGE;
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
        Optional<Entity> ship = this.getShip().getSection().getEntityAt(position);
        if(ship.get() instanceof Ship s) {
            List<ShipComponent> sc = List.of(this, this.getShip().getSail(), this.getShip().getBow());
            s.takeDamage(damage, sc.stream()
                                   .skip(new Random().nextInt(sc.size()))
                                   .findFirst()
                                   .orElse(null));
        }
    }
}
