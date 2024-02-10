package it.unibo.object_onepiece.model;

import java.util.List;

import it.unibo.object_onepiece.model.Utils.State;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.Argument;

/**
 * Represents World of the game, with the current section and the saved ones.
 */
public interface World {

    /**
     * Grid rows.
     */
    int SECTION_ROWS = 10;
    /**
     * Grid columns.
     */
    int SECTION_COLUMNS = 10;

    /**
     * An Event alias that is used when a Section is created in World.
     * @see Event
     * @see World
     */
    final class SectionInstantiatedEvent
    extends Event<Argument<Section>> {
        /**
         * A less verbose version of invoke that directly takes the Event arguments.
         * @param section the Section object that has been newly instantiated
         * @see Event
         */
        protected void invoke(final Section section) {
            super.invoke(new Argument<>(section));
        }
    }

    /**
     * 
     * @return State last saved section.
     */
    State getSavedState();
    /**
     * Save current section.
     */
    void setSavedState();

    Player getPlayer();
    List<Enemy> getEnemies();

    SectionInstantiatedEvent getSectionInstantiatedEvent();
}
