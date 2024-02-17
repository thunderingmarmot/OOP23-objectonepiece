package it.unibo.object_onepiece.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class defining a data container for the onPlayerUpdated Event.
 * @see PlayerImpl
 */
public final class PlayerUpdatedArgs {
    private final List<String> nameList;
    private final List<Integer> healthList;
    private final List<Integer> maxHealthList;
    private final int experience;

    /**
     * Creates an instance of this class.
     * @param nameList the list of components names
     * @param healthList the list of components current health
     * @param maxHealthList the list of components maximum health
     * @param experience the experience of the Player
     */
    protected PlayerUpdatedArgs(final List<String> nameList,
                                final List<Integer> healthList,
                                final List<Integer> maxHealthList,
                                final int experience) {
        this.nameList = nameList;
        this.healthList = healthList;
        this.maxHealthList = maxHealthList;
        this.experience = experience;
    }

    /**
     * Getter for the list of components names.
     * @return the List of current healths
     */
    public List<String> getNames() {
        return new ArrayList<>(this.nameList);
    }

    /**
     * Getter for the list of components current health.
     * @return the List of current healths
     */
    public List<Integer> getHealthList() {
        return new ArrayList<>(this.healthList);
    }

    /**
     * Getter for the list of components maximum health.
     * @return the List of maximum healths
     */
    public List<Integer> getMaxHealthList() {
        return new ArrayList<>(this.maxHealthList);
    }

    /**
     * Getter for the experience.
     * @return the experience value
     */
    public int getExperience() {
        return this.experience;
    }
}
