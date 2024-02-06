package it.unibo.object_onepiece.model.events;

/**
 * Models an object that can be used as a container for Event arguments.
 */
public interface EventArgs {
    /**
     * Defines an Event arguments container of no arguments
     */
    record NoArgument() implements EventArgs { }

    /**
     * Defines an Event arguments container of one generic argument
     */
    record Argument<T>(T arg) implements EventArgs { }

    /**
     * Defines an Event arguments container of two arguments of the same generic type
     */
    record BiArgument<T>(T arg1, T arg2) implements EventArgs { }

    /**
     * Defines an Event arguments container of two generic arguments
     */
    record BiArguments<T1, T2>(T1 arg1, T2 arg2) implements EventArgs { }

    /**
     * Defines an Event arguments container of three generic arguments
     */
    record TriArguments<T1, T2, T3>(T1 arg1, T2 arg2, T3 arg3) implements EventArgs { }
}
