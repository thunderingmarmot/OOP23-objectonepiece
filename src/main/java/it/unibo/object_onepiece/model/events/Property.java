package it.unibo.object_onepiece.model.events;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Property<T> {
    private final Supplier<T> getter;
    private final Consumer<T> setter;

    private final Event<T> onSet;

    public Property(Supplier<T> getter, Consumer<T> setter) {
        this.getter = getter;
        this.setter = setter;
        this.onSet = new Event<>();
    }

    public T get() {
        return this.getter.get();
    }

    public void set(T newValue) {
        this.onSet.invoke(newValue);
        this.setter.accept(newValue);
    }

    public Event<T> getSetEvent() {
        return this.onSet;
    }
}
