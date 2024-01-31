package it.unibo.object_onepiece.model.events;

import java.util.function.Consumer;

public interface Event {
    public void subscribe(Consumer<EventArgs> listener);
    public void unsubscribe(Consumer<EventArgs> listener);
    public void invoke();
}
