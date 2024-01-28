package it.unibo.object_onepiece.model;

// Define what a Ship can collide with

public interface StaticCollidable extends Collidable {
    public void onCollision(MovingCollidable collider);
}
