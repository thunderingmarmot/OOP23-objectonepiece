package it.unibo.object_onepiece.model.ship;

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

    public static Weapon cannon() {
        return new WeaponImpl(20, 10, 3, 100);
    }

    public static Weapon railgun() {
        return new WeaponImpl(50, 0, 5, 70);
    }

    public static Weapon heavycannon() {
        return new WeaponImpl(80, 40, 1, 120);
    }
}
