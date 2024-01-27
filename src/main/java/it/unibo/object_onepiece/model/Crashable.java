package it.unibo.object_onepiece.model;

public interface Crashable {
    public void crash(Crashable c);

    public int getCrashDamage();
}
