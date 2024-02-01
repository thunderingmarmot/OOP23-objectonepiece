package it.unibo.object_onepiece.model.events;

public interface EventArgs {
    public record NoArgument() implements EventArgs {}
    public record Argument<T>(T arg) implements EventArgs {}
    public record BiArgument<T>(T arg1, T arg2) implements EventArgs {}
    public record BiArguments<T1, T2>(T1 arg1, T2 arg2) implements EventArgs {}
}
