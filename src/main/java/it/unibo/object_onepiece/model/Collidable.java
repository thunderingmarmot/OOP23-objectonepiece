package it.unibo.object_onepiece.model;

public interface Collidable {
    public void onCollisionWith(Collider collider);
    public boolean isStatic();
}
