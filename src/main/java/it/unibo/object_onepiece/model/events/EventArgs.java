package it.unibo.object_onepiece.model.events;

/**
 * Models an object that can be used as a container for Event arguments.
 */
public interface EventArgs {
    /**
     * Defines an Event arguments container of no arguments.
     */
    record NoArgument() implements EventArgs { }

    /**
     * Defines an Event arguments container that uses varargs.
     * @param args a varargs of arguments
     */
    record VarArguments(Object... args) implements EventArgs { }

    /**
     * Defines an Event arguments container of one generic argument.
     * @param <T> the generic type for the only argument
     * @param arg the only argument of type T
     */
    record Argument<T>(T arg) implements EventArgs { }

    /**
     * Defines an Event arguments container of two arguments of the same generic type.
     * @param <T> the generic type for both arguments
     * @param arg1 the first argument of type T
     * @param arg2 the second argument of type T
     */
    record BiArgument<T>(T arg1, T arg2) implements EventArgs { }

    /**
     * Defines an Event arguments container of two generic arguments.
     * @param <T1> the generic type of the first argument
     * @param <T2> the generic type of the second argument
     * @param arg1 the first argument of type T1
     * @param arg2 the second argument of type T2
     */
    record BiArguments<T1, T2>(T1 arg1, T2 arg2) implements EventArgs { }

    /**
     * Defines an Event arguments container of three generic arguments.
     * @param <T1> the generic type of the first argument
     * @param <T2> the generic type of the second argument
     * @param <T3> the generic type of the third argument
     * @param arg1 the first argument of type T1
     * @param arg2 the second argument of type T2
     * @param arg3 the third argument of type T3
     */
    record TriArguments<T1, T2, T3>(T1 arg1, T2 arg2, T3 arg3) implements EventArgs { }
}
