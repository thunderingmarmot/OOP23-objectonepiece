package it.unibo.object_onepiece.model.events;

public interface EventArgs {
    public record ValueChanged<T>(T oldValue, T newValue) implements EventArgs {}
    public record Generic() implements EventArgs {}
}
