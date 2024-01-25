package it.unibo.object_onepiece.model;

public interface Interactor<T extends Interactor<T>> {
    public void interact(Interactable<T> interactedWith);
}
