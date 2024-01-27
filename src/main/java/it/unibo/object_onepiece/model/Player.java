package it.unibo.object_onepiece.model;

//Interface that models the player's ship

public interface Player extends Ship {
    public int getExperience();
    public void addExperience(int experience);

    public void addHealth(int healthGained);
}
