package Model;

//Interface that models the player's ship
public interface Player extends Ship {
    public void getExperience();
    public void addExperience(int experience);

    public void interact();
}
