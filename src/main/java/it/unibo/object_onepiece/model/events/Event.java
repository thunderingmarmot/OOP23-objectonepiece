package it.unibo.object_onepiece.model.events;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Base Event implementation.
 * @param <T> the generic type implementing EventArgs defining the Event arguments
 * @see Event
 */
public class Event<T extends EventArgs> {

    private final Supplier<T> value;
    private final List<Consumer<T>> listeners;

    protected Event(Supplier<T> value) {
        this.value = value;
        this.listeners = new ArrayList<>();
    }

    /**
     * Subscribe a Consumer<T> to the Event.
     * @param listener the Consumer to be executed when Event is invoked
     * @see Consumer
     */
    public void subscribe(final Consumer<T> listener) {
        listeners.add(listener);
    }

    /**
     * Unsubscribe a Consumer<T> to the Event.
     * @param listener the Consumer to be executed when Event is invoked
     * @see Consumer
     */
    public void unsubscribe(final Consumer<T> listener) {
        listeners.remove(listener);
    }

    /**
     * Invoke the Event with some arguments.
     * @param args the arguments passed to the subscribed Consumers
     * @see EventArgs
     */
    protected void invoke(final T args) {
        listeners.forEach((listener) -> listener.accept(args));
    }
}