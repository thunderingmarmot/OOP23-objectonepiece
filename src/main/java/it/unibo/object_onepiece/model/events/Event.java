package it.unibo.object_onepiece.model.events;

import java.util.function.Consumer;

public interface Event<T extends EventArgs> {
    public void subscribe(Consumer<T> action);
    public void unsubscribe(Consumer<T> action);

    public void invoke(T args);
}
