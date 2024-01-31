package it.unibo.object_onepiece.controller;

import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.World;

public class ControllerImpl implements Controller{

    @Override
    public void action(Position position, World world) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    @Override
    public void initialize(World world) {
       world.generateSection();
       
    }
    
}
