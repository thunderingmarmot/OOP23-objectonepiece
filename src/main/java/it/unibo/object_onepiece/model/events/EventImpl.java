package it.unibo.object_onepiece.model.events;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Implementation of the Event interface.
 * @param <T> the generic type implementing EventArgs defining the Event arguments
 * @see Event
 */
public final class EventImpl<T extends EventArgs> implements Event<T> {

    private final List<Consumer<T>> listeners = new ArrayList<>();

    /**
     * Default constructor for EventImpl.
     * It's protected to only allow creating this object inside this package.
     * Actual object creation is handled in the static method inside Event interface.
     * @see Event
     */
    protected EventImpl() {
        // This constructor is intentionally empty.
    }

    /**
     * Subscribe a Consumer<T> to the Event.
     * @param listener the Consumer to be executed when Event is invoked
     * @see Consumer
     */
    @Override
    public void subscribe(final Consumer<T> listener) {
        listeners.add(listener);
    }

    /**
     * Unsubscribe a Consumer<T> to the Event.
     * @param listener the Consumer to be executed when Event is invoked
     * @see Consumer
     */
    @Override
    public void unsubscribe(final Consumer<T> listener) {
        listeners.remove(listener);
    }

    /**
     * Invoke the Event with some arguments.
     * @param args the arguments passed to the subscribed Consumers
     * @see EventArgs
     */
    @Override
    public void invoke(final T args) {
        listeners.forEach((listener) -> listener.accept(args));
    }
}
