package it.unibo.object_onepiece.model.events;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Base Alert implementation.
 */
public class Alert {

    private final List<Runnable> listeners = new ArrayList<>();

    /**
     * Subscribe a Consumer<T> to the Event.
     * @param listener the Consumer to be executed when Event is invoked
     * @see Consumer
     */
    public void subscribe(final Runnable listener) {
        listeners.add(listener);
    }

    /**
     * Unsubscribe a Consumer<T> to the Event.
     * @param listener the Consumer to be executed when Event is invoked
     * @see Consumer
     */
    public void unsubscribe(final Runnable listener) {
        listeners.remove(listener);
    }

    /**
     * Invoke the Event with some arguments.
     * @param args the arguments passed to the subscribed Consumers
     * @see EventArgs
     */
    public void invoke() {
        listeners.forEach((listener) -> listener.run());
    }
}