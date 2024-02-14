package it.unibo.object_onepiece.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerUpdatedArgs {
    private final List<Integer> healthList;
    private final List<Integer> maxHealthList;
    private final int experience;

    protected PlayerUpdatedArgs(final List<Integer> healthList,
                             final List<Integer> maxHealthList,
                             final int experience) {
        this.healthList = healthList;
        this.maxHealthList = maxHealthList;
        this.experience = experience;
    }

    public List<Integer> getHealthList() {
        return new ArrayList<>(this.healthList);
    }

    public List<Integer> getMaxHealthList() {
        return new ArrayList<>(this.maxHealthList);
    }

    public int getExperience() {
        return this.experience;
    }
}