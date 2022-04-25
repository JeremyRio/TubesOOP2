package com.aetherwars.model.folder;

import com.aetherwars.controller.AetherWarsController;
import com.aetherwars.controller.HandCardController;
import com.aetherwars.controller.SummonedCardController;
import com.aetherwars.model.game.Phase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameChannel {
    private AetherWarsController mainController;
    private List<SummonedCardController> player1_summonedController;
    private List<SummonedCardController> player2_summonedController;
    private List<HandCardController> cardOnHandController;
    private Map<Publisher, List<Subscriber>> subscribers;
    private Phase phase;
    private boolean sourcePlan;
    private boolean sourceAttack;
    private boolean target;
    private HandCardController sourcePlanController;
    private SummonedCardController targetPlanController;
    private SummonedCardController sourceAttackController;

    public GameChannel(){
        player1_summonedController = new ArrayList<>();
        player2_summonedController = new ArrayList<>();
        cardOnHandController = new ArrayList<>();
        phase = Phase.DRAW;
        sourcePlan = false;
        sourceAttack = false;
        target = false;
    }

    public SummonedCardController getSourceAttackController(){
        return this.sourceAttackController;
    }

    public void setSourceAttackController(SummonedCardController controller){
        this.sourceAttackController = controller;
    }


    public void addSummonedController(int player, SummonedCardController controller){
        switch (player){
            case 0:
                player1_summonedController.add(controller);
                break;
            default:
                player2_summonedController.add(controller);
        }
    }

    public List<SummonedCardController> getSummonedController(int player){
        switch (player){
            case 0:
                return player1_summonedController;
            default:
                return player2_summonedController;
        }
    }

    public boolean isSourceAttack(){
        return this.sourceAttack;
    }

    public void setSourceAttack(boolean sourceAttack){
        this.sourceAttack = sourceAttack;
    }

    public void addHandCardController(HandCardController controller){
        cardOnHandController.add(controller);
    }

    public List<HandCardController> getHandCardController(){
        return cardOnHandController;
    }

    public void setSourcePlanController(HandCardController controller){
        this.sourcePlanController = controller;
    }

    public void setTargetPlanController(SummonedCardController controller){
        this.targetPlanController = controller;
    }

    public HandCardController getSourcePlanController(){
        return sourcePlanController;
    }

    public SummonedCardController getTargetPlanController(){
        return targetPlanController;
    }

    public void setSourcePlan(boolean sourcePlan){
        this.sourcePlan = sourcePlan;
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

    public boolean isSourcePlan(){
        return sourcePlan;
    }

    public boolean isTarget(){
        return target;
    }

    public void setPhase(Phase phase){
        this.phase = phase;
    }
}
