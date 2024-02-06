package it.unibo.object_onepiece.model.ship;

public interface Bow extends ShipComponent {
    int getCrashDamage();

    static Bow standard() {
        final int crashDamage = 20;
        final int health = 100;

        return new BowImpl(crashDamage, health);
    }

    static Bow heavy() {
        final int crashDamage = 40;
        final int health = 200;

        return new BowImpl(crashDamage, health);
    }

    static Bow light() {
        final int crashDamage = 5;
        final int health = 75;

        return new BowImpl(crashDamage, health);
    }
}
