package it.unibo.object_onepiece.model.events;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventImpl<T extends EventArgs> implements Event<T> {

    private List<Consumer<T>> listeners = new ArrayList<>();

    protected EventImpl() {}

    @Override
    public void subscribe(Consumer<T> listener) {
        listeners.add(listener);
    }

    @Override
    public void unsubscribe(Consumer<T> listener) {
        listeners.remove(listener);
    }

    @Override
    public void invoke(T args) {
        listeners.forEach((listener) -> listener.accept(args));
    }
    
}
