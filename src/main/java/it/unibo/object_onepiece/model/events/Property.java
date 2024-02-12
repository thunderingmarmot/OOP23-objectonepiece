package it.unibo.object_onepiece.model.events;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Property<T> {
    private final Supplier<T> getter;
    private final Consumer<T> setter;

    private Event<T> onValueSet;

    public Property(Supplier<T> getter, Consumer<T> setter) {
        this.getter = getter;
        this.setter = setter;
        this.setter.andThen(onValueSet::invoke);
    }

    public T get() {
        return getter.get();
    }

    public void set(T newValue) {
        setter.accept(newValue);
    }

    public Event<T> getValueSetEvent() {
        return onValueSet;
    }
}
