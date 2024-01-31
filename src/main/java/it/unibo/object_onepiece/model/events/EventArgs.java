package it.unibo.object_onepiece.model.events;

public interface EventArgs {
    public record Generic() implements EventArgs {}
    public static Generic generic() {
        return new Generic();
    }

    public record ValueChanged<T>(T oldValue, T newValue) implements EventArgs {}
    public static <T> ValueChanged<T> valueChanged(T oldValue, T newValue) {
        return new ValueChanged<T>(oldValue, newValue);
    }

    public record ObjectCreation<T>(T object) implements EventArgs {}
    public static <T> ObjectCreation<T> objectCreation(T object) {
        return new ObjectCreation<T>(object);
    }
}
