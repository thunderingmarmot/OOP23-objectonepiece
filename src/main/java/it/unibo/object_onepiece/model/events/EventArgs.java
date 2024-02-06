package it.unibo.object_onepiece.model.events;

/*
 * Defines an object that can be used as a container for Event arguments.
 */
public interface EventArgs {
    /*
     * Defines an Event arguments container of no arguments
     */
    public record NoArgument() implements EventArgs {}

    /*
     * Defines an Event arguments container of one generic argument
     */
    public record Argument<T>(T arg) implements EventArgs {}

    /*
     * Defines an Event arguments container of two arguments of the same generic type
     */
    public record BiArgument<T>(T arg1, T arg2) implements EventArgs {}

    /*
     * Defines an Event arguments container of two generic arguments
     */
    public record BiArguments<T1, T2>(T1 arg1, T2 arg2) implements EventArgs {}

    /*
     * Defines an Event arguments container of three generic arguments
     */
    public record TriArguments<T1, T2, T3>(T1 arg1, T2 arg2, T3 arg3) implements EventArgs {}
}
