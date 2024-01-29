package it.unibo.object_onepiece.controller;
import java.util.List;

import it.unibo.object_onepiece.model.GuiEntity;
import it.unibo.object_onepiece.model.Utils.Position;

public interface Controller {
    public List<GuiEntity> getElements();
    public void action(Position position);
}
