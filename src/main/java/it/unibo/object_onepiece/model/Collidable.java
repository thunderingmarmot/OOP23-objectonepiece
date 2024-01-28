package it.unibo.object_onepiece.model;

// A Collidable is something that can be collided with

public interface Collidable {
    public enum Rigidness {
        SOFT, // Gets instantly destroyed by ramming and can be passed on
        MEDIUM, // Can be passed on after being destroyed by ramming
        HARD, // Cannot be passed on or destroyed by ramming
    }

    public Rigidness getRigidness();
    public void onCollisionWith(Collider collider);
}
