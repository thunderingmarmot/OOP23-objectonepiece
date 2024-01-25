package it.unibo.object_onepiece.model;

//Interface that models the player's ship

public interface Player extends Ship {
    public void getExperience();
    public void addExperience(int experience);

    public void heal(int health);

    public void interact();
}
