package it.unibo.object_onepiece.model.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Events {
    private static List<Event<Dispatcher>> events = new ArrayList<>();

    public static <T extends Dispatcher> Optional<Event<T>> getEvent(Class<T> classObj) {
        return null;
    } 
}
