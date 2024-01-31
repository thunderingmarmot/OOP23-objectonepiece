package it.unibo.object_onepiece.model.Ship;

public interface Bow extends ShipComponent {
    public int getCrashDamage();

    public static Bow standard() {
        return new BowImpl(20, 100);
    }

    public static Bow heavy() {
        return new BowImpl(40, 200);
    }
    
    public static Bow light() {
        return new BowImpl(5, 75);
    }
}
