package it.unibo.object_onepiece.model.events;

public interface Event {
    public void invoke(Object... args);
}
