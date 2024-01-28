package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Enemy.States;

public interface EnemyState {
    public void perform();
    public States getState();
}
