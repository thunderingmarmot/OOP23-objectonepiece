package it.unibo.object_onepiece.model;

public interface Crashable {
    public void crash(int damage, Crashable c);

    public int getCrashDamage();
}
