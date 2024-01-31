package it.unibo.object_onepiece.model.events;

public interface EventArgs {
    public record NoArgs() implements EventArgs {}
    public static NoArgs noArgs() {
        return new NoArgs();
    }

    public record ValueChanged<T>(T oldValue, T newValue) implements EventArgs {}
    public static <T> ValueChanged<T> valueChanged(T oldValue, T newValue) {
        return new ValueChanged<T>(oldValue, newValue);
    }

    public record ObjectChanged<T>(T object) implements EventArgs {}
    public static <T> ObjectChanged<T> objectChanged(T object) {
        return new ObjectChanged<T>(object);
    }
}
