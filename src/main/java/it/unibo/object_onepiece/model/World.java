package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

/**Interface that models the factory of Sections while also keeping track
* of the Section where the player saved the game 
*/
public interface World {
    public record State(Section section, Position playerPosition, int playerExperience) {}
    public State getSavedState();
    public void setSavedState();

    public Section getCurrentSection();
    public Section generateSection();
}
