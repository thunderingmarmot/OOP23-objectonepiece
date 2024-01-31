package it.unibo.object_onepiece.model.Ship;

import it.unibo.object_onepiece.model.Utils.Position;

public interface Weapon extends ShipComponent {
    public enum ShootDetails {
        SHOOTED_SUCCESSFULLY,
        OUT_OF_SHOOTING_RANGE,
        WEAPON_BROKEN
    };

    public ShootDetails shoot(Position position);

    public int getMaxDamage();
    public int getMinDamage();
    public int getRange();

    public static Weapon cannon(Ship ship) {
        return new WeaponImpl(20, 10, 3, ship, 100);
    }

    public static Weapon railgun(Ship ship) {
        return new WeaponImpl(50, 0, 5, ship, 70);
    }

    public static Weapon heavycannon(Ship ship) {
        return new WeaponImpl(80, 40, 1, ship, 120);
    }
}
