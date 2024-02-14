package it.unibo.object_onepiece.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Base Event implementation.
 * @param <T> the generic type defining the Event argument
 */
public class Event<T> {

    private final List<Consumer<T>> listeners = new ArrayList<>();

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
     * Invalidates this Event unsubscribing every Consumer<T> from it.
     */
    public void invalidate() {
        listeners.clear();
    }

    /**
     * Invoke the Event with some argument.
     * @param args the argument passed to the subscribed Consumers
     */
    public void invoke(final T args) {
        listeners.forEach((listener) -> listener.accept(args));
    }

    /**
     * Invokes the Event with some argument the invalidates it.
     * @param args the argument passed to the subscribed Consumers
     */
    public void lastInvoke(final T args) {
        this.invoke(args);
        this.invalidate();
    }
}
