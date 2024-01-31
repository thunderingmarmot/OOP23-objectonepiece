package it.unibo.object_onepiece.model.events;

public interface Event {
    public void subscribe();
    public void unsubscribe();

    public void invoke();
}
