package it.unibo.object_onepiece.model.ship;

import it.unibo.object_onepiece.model.Utils.Position;

public interface Weapon extends ShipComponent {
    enum ShootDetails {
        SHOOTED_SUCCESSFULLY,
        OUT_OF_SHOOTING_RANGE,
        WEAPON_BROKEN
    };

    record ShootReturnType(boolean hasShooted, ShootDetails details) { }; 

    ShootReturnType shoot(Position position);

    int getMaxDamage();
    int getMinDamage();
    int getRange();

    static Weapon cannon() {
        final int MAX_DAMAGE = 20;
        final int MIN_DAMAGE = 10;
        final int RANGE = 3;
        final int HEALTH = 100;

        return new WeaponImpl(MAX_DAMAGE, MIN_DAMAGE, RANGE, HEALTH);
    }

    static Weapon railgun() {
        final int MAX_DAMAGE = 50;
        final int MIN_DAMAGE = 0;
        final int RANGE = 5;
        final int HEALTH = 70;

        return new WeaponImpl(MAX_DAMAGE, MIN_DAMAGE, RANGE, HEALTH);
    }

    static Weapon heavycannon() {
        final int MAX_DAMAGE = 80;
        final int MIN_DAMAGE = 40;
        final int RANGE = 1;
        final int HEALTH = 120;

        return new WeaponImpl(MAX_DAMAGE, MIN_DAMAGE, RANGE, HEALTH);
    }
}
