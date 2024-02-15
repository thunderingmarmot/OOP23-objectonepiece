package it.unibo.object_onepiece.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * An Event class that mimics C# events.
 * Supports the subscribe and unsubscribe operations.
 * Supports invoking the Event with a generic argument.
 * Also supports a way for invalidating the Event which means
 * unsubscribing all listeners and making the Event unusable.
 * @param <T> the generic type defining the Event argument
 */
public class Event<T> {

    private boolean isValid = true;
    private final List<Consumer<T>> listeners = new ArrayList<>();

    /**
     * Subscribe a Consumer<T> to the Event.
     * @param listener the Consumer to be executed when Event is invoked
     * @see Consumer
     */
    public void subscribe(final Consumer<T> listener) {
        if (this.isValid) {
            listeners.add(listener);
        } else {
            throw new IllegalStateException("Tried to call 'subscribe' on an invalid Event!");
        }
    }

    /**
     * Unsubscribe a Consumer<T> to the Event.
     * @param listener the Consumer to be executed when Event is invoked
     * @see Consumer
     */
    public void unsubscribe(final Consumer<T> listener) {
        if (this.isValid) {
            listeners.remove(listener);
        } else {
            throw new IllegalStateException("Tried to call 'unsubscribe' on an invalid Event!");
        }
    }

    /**
     * Invalidates this Event unsubscribing every Consumer<T> from it.
     */
    public void invalidate() {
        if (this.isValid) {
            listeners.clear();
            this.isValid = false;
        } else {
            throw new IllegalStateException("Tried to call 'invalidate' on an invalid Event!");
        }
    }

    /**
     * Invoke the Event with some argument.
     * @param args the argument passed to the subscribed Consumers
     */
    public void invoke(final T args) {
        if (this.isValid) {
            listeners.forEach((listener) -> listener.accept(args));
        } else {
            throw new IllegalStateException("Tried to call 'invoke' on an invalid Event!");
        }
    }

    /**
     * Try invoking the Event with some argument, if it fails, don't throw exception.
     * @param args the argument passed to the subscribed Consumers
     * @return a boolean which tells wether the invoke has been successfully called 
     */
    public boolean tryInvoke(final T args) {
        if (this.isValid) {
            listeners.forEach((listener) -> listener.accept(args));
        }
        return this.isValid;
    }

    /**
     * Invokes the Event with some argument then invalidates it.
     * @param args the argument passed to the subscribed Consumers
     */
    public void lastInvoke(final T args) {
        if (this.isValid) {
            this.invoke(args);
            this.invalidate();
        } else {
            throw new IllegalStateException("Tried to call 'lastInvoke' on an invalid Event!");
        }
    }
}
