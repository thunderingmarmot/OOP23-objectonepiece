package it.unibo.object_onepiece.model.events;

public class AutoProperty<T> {
    private T innerValue;
    private final Property<T> innerProperty;

    public AutoProperty(T initialValue) {
        this.innerValue = initialValue;
        this.innerProperty = new Property<>(() -> innerValue,
                                            (newValue) -> innerValue = newValue);
    }

    public T get() {
        return innerProperty.get();
    }
    
    public void set(T newValue) {
        innerProperty.set(newValue);
    }

    public Event<T> getValueSetEvent() {
        return innerProperty.getValueSetEvent();
    }
}
