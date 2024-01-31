package it.unibo.object_onepiece.model.events;

import java.util.function.Consumer;

public interface Event<T extends EventArgs> {
    public void subscribe(Consumer<T> listener);
    public void unsubscribe(Consumer<T> listener);
    public void invoke(T args);

    public static <T extends EventArgs> Event<T> get() {
        return new EventImpl<T>();
    }
}
