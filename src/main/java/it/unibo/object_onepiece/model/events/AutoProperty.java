package it.unibo.object_onepiece.model.events;

public class AutoProperty<T> {
    private T innerValue;
    private final Property<T> innerProperty;

    public AutoProperty(T initialValue) {
        this.innerProperty = new Property<>(() -> innerValue,
                                            (newValue) -> innerValue = newValue);
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
