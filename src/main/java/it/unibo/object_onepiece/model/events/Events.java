package it.unibo.object_onepiece.model.events;

import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Events {
    private static Map<Event, List<Consumer<EventArgs>>> events;

    public static void subscribe(Event event, Consumer<EventArgs> listener) {
        if(!events.containsKey(event)) {
            events.put(event, new ArrayList<>());
        }
        events.get(event).add(listener);
    }

    public static void unsubscribe(Event event, Consumer<EventArgs> listener) {
        events.remove(event);
    }

    public static void invoke(Event event, EventArgs args) {
        if(events.containsKey(event)) {
            events.get(event).forEach((action) -> action.accept(null));
        }
    }
}
