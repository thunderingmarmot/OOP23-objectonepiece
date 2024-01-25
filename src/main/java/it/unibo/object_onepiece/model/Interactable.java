package it.unibo.object_onepiece.model;

import java.util.function.Consumer;

public interface Interactable<T extends Interactor<T>> {
    public Consumer<T> getInteraction();
}
