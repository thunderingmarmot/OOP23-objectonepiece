package it.unibo.object_onepiece.model.events;

import java.util.function.Consumer;

public class AutoProperty<T> {
    private T innerValue;
    private final Property<T> innerProperty;

    private AutoProperty() {
        this.innerProperty = new Property<>(() -> innerValue,
                                            (newValue) -> innerValue = newValue);
    }

    public AutoProperty(T initialValue) {
        this();
        this.set(initialValue);
    }

    public AutoProperty(T initialValue, Consumer<T> binding) {
        this();
        this.getSetEvent().subscribe(binding);
        this.set(initialValue);
    }

    public T get() {
        return this.innerProperty.get();
    }
    
    public void set(T newValue) {
        this.innerProperty.set(newValue);
    }

    public Event<T> getSetEvent() {
        return this.innerProperty.getSetEvent();
    }
}
