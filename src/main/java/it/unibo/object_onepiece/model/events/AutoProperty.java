package it.unibo.object_onepiece.model.events;

public class AutoProperty<T> {
    private T innerValue;
    private Property<T> innerProperty;

    public AutoProperty() {
        this.innerProperty = new Property<>(() -> innerValue,
                                            (newValue) -> innerValue = newValue);
    }

    public AutoProperty(T initialValue) {
        this();
        this.innerValue = initialValue;
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
