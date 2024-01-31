package it.unibo.object_onepiece.model.events;

import java.util.List;
import java.util.function.Consumer;

public class EventImpl<T extends EventArgs> implements Event<T> {

    private List<Consumer<T>> listeners;

    @Override
    public void subscribe(Consumer<T> action) {
        listeners.add(action);
    }

    @Override
    public void unsubscribe(Consumer<T> action) {
        listeners.remove(action);
    }

    @Override
    public void invoke(T args) {
        listeners.forEach((listener) -> listener.accept(args));
    }
    
}
