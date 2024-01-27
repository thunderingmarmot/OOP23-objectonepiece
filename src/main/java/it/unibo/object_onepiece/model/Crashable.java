package it.unibo.object_onepiece.model;

// Defines something that can be crashed or crash into another Crashable resulting
// in two entities briefly occupying the same cell before destroying themselves

public interface Crashable {
    public void crash(Crashable crashable);

    public int getCrashDamage();
}
