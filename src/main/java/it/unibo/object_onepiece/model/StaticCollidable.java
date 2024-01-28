package it.unibo.object_onepiece.model;

public interface StaticCollidable extends Collidable {
    public void onCollision(MovingCollidable collider);
}
