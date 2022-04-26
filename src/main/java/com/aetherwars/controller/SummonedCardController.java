package com.aetherwars.controller;

import com.aetherwars.model.card.CharacterCard;
import com.aetherwars.model.card.SummonedCard;
import com.aetherwars.model.event.GameChannel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.input.MouseButton;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.out;

public class SummonedCardController implements Initializable {

    @FXML
    StackPane card_pane;
    @FXML
    Text health_text;
    @FXML
    Text attack_text;
    @FXML
    Text level_exp_text;
    @FXML
    ImageView image_card;
    private SummonedCard summonedCard;
    private GameChannel channel;



    public SummonedCardController(GameChannel gameChannel){
        this.channel = gameChannel;
    }

    public void initialize(URL url, ResourceBundle rb) {
        summonedCard = new SummonedCard();
        this.card_pane.setOpacity(0);
        this.card_pane.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        switch(channel.getPhase()) {
                            case PLAN:
                                if(this.summonedCard.isEmpty()) {
                                    if (channel.isSourcePlan() && channel.getSummonedController(channel.getMainController().getCurrentPlayerIDX()).contains(this)) {
                                        if (channel.getSourcePlanController().getCard() instanceof CharacterCard) {
                                            card_pane.setOpacity(1);
                                            CharacterCard characterCard = new CharacterCard((CharacterCard) channel.getSourcePlanController().getCard());
                                            this.setSummonedCard(new SummonedCard(characterCard));
                                            this.summonedCard.setEmpty(false);
                                            channel.getSourcePlanController().destroyCard();
                                            channel.setSourcePlan(false);
                                        }
                                    }
                                }
                                break;
                            case ATTACK:
                                if(!this.summonedCard.isEmpty()) {
                                    if (channel.isSourceAttack() && !channel.getSummonedController(channel.getMainController().getCurrentPlayerIDX()).contains(this)) {
                                        SummonedCardController targetController = channel.getSourceAttackController();
                                        this.summonedCard.Attack(targetController.getSummonedCard());
                                        out.println(this.summonedCard.isDead());
                                        if(this.summonedCard.isDead() && !targetController.getSummonedCard().isDead()){
                                            this.summonedCard.setEmpty(true);
                                            this.card_pane.setOpacity(0);
                                            targetController.getSummonedCard().addExp(this.summonedCard.getLevel());
                                        }else if(targetController.getSummonedCard().isDead() && !this.summonedCard.isDead()){
                                            targetController.getSummonedCard().setEmpty(true);
                                            targetController.card_pane.setOpacity(0);
                                            this.summonedCard.addExp(targetController.getSummonedCard().getLevel());
                                        }else if (this.summonedCard.isDead() && targetController.getSummonedCard().isDead()){
                                            this.summonedCard.setEmpty(true);
                                            this.card_pane.setOpacity(0);
                                            targetController.getSummonedCard().setEmpty(true);
                                            targetController.card_pane.setOpacity(0);
                                        }
                                        this.updateCard();
                                        targetController.updateCard();
                                        channel.setSourceAttack(false);
                                    } else {
                                        channel.setSourceAttack(true);
                                        channel.setSourceAttackController(this);
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
        );
    }

    public SummonedCard getSummonedCard(){
        return this.summonedCard;
    }

    public void setSummonedCard(SummonedCard summonedCard) {
        this.summonedCard = summonedCard;
        updateCard();
    }

    public void updateCard() {
        File file = null;
        try {
            file = new File(getClass().getResource("../" + this.summonedCard.getImagePath()).toURI());
        }
        catch (Exception e) {
            out.println("Error loading image: " + e);
        }
;       Image image = new Image(file.toURI().toString());
        image_card.setImage(image);
        health_text.setText(String.valueOf(this.summonedCard.getTotalHealth()));
        attack_text.setText(String.valueOf(this.summonedCard.getTotalAttack()));
        level_exp_text.setText(this.summonedCard.getExp() + "/"
                + this.summonedCard.getExpToNextLevel() + " ["
                + this.summonedCard.getLevel() + "]");
    }

}
