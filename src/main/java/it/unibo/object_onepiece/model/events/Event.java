package it.unibo.object_onepiece.model.events;

import java.util.function.Consumer;

/**
 * Models an Event which can be invoked by the observed and subscribed to by the observer.
 * @param <T> the generic type implementing EventArgs defining the Event arguments
 * @see EventArgs
 */
public interface Event<T extends EventArgs> {
    /**
     * Subscribe a Consumer<T> to the Event.
     * @param listener
     * @see Consumer
     */
    void subscribe(Consumer<T> listener);

    /**
     * Unsubscribe a Consumer<T> to the Event.
     * @param listener
     * @see Consumer
     */
    void unsubscribe(Consumer<T> listener);

    /**
     * Invoke the Event with some arguments.
     * @param args
     * @see EventArgs
     */
    void invoke(T args);

    /**
     * Creates a new Event.
     * @param <T> the genric type implementing EventArgs defining the Event arguments
     * @return a newly created Event object with the matching generic EventArgs
     */
    static <T extends EventArgs> Event<T> get() {
        return new EventImpl<T>();
    }
}
