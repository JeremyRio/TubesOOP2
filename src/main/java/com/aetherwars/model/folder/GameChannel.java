package com.aetherwars.model.folder;

import com.aetherwars.AetherWars;
import com.aetherwars.controller.AetherWarsController;
import com.aetherwars.model.game.Phase;

import java.util.List;
import java.util.Map;

public class GameChannel {
    private AetherWarsController mainController;
    private Map<Publisher, List<Subscriber>> subscribers;
    private Phase phase;
    private boolean source;
    private boolean target;

    public GameChannel(){
        phase = Phase.DRAW;
        source = false;
        target = false;
    }



    public void setSource(boolean source){
        this.source = source;
    }

    public void setTarget(boolean target){
        this.target = target;
    }

    public void setMainController(AetherWarsController mainController){
        this.mainController = mainController;
    }

    public AetherWarsController getMainController(){
        return mainController;
    }

    public Phase getPhase(){
        return phase;
    }

    public boolean isSource(){
        return source;
    }

    public boolean isTarget(){
        return target;
    }

    public void setPhase(Phase phase){
        this.phase = phase;
    }
}
