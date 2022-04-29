package com.aetherwars.controller;

import com.aetherwars.model.card.*;
import com.aetherwars.model.event.GameChannel;
import com.aetherwars.model.game.Phase;
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

        this.card_pane.setOnMouseEntered(event -> {
            if(!this.summonedCard.isEmpty()) {
                channel.getMainController().setSummonedDescription(this.summonedCard);
            }
        });

        this.card_pane.setOnMouseExited(event -> {
            channel.getMainController().resetDescription();
        });

        this.card_pane.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        if (event.getClickCount() == 2) {
                            if (channel.getPhase() == Phase.PLAN && channel.getSummonedController(channel.getMainController().getCurrentPlayerIDX()).contains(this)) {
                                this.summonedCard.setEmpty(true);
                                this.card_pane.setOpacity(0);
                                this.channel.getMainController().description_pane.setOpacity(0);
                                this.updateCard();
                            }
                        }else{
                        switch (channel.getPhase()) {
                            case PLAN:
                                if (channel.isSourcePlan()) {
                                    HandCardController sourceController = channel.getSourcePlanController();
                                    Card card = sourceController.getCard();
                                    if (card instanceof CharacterCard && this.summonedCard.isEmpty()) {
                                        if (channel.getSummonedController(channel.getMainController().getCurrentPlayerIDX()).contains(this)) {
                                            card_pane.setOpacity(1);
                                            CharacterCard characterCard = new CharacterCard((CharacterCard) card);
                                            this.setSummonedCard(new SummonedCard(characterCard));
                                            sourceController.destroyCard();
                                            this.summonedCard.setEmpty(false);
                                            channel.setSourcePlan(false);
                                            channel.getMainController().getCurrentPlayer().decreaseMana(card.getMana());
                                            channel.getMainController().updateUIText();
                                            channel.getMainController().setSummonedDescription(this.summonedCard);
                                        }
                                    } else if (card instanceof SpellCard && !this.summonedCard.isEmpty()) {
                                        if (card instanceof LevelSpellCard) {
                                            if (!(this.summonedCard.getLevel() == 1 && card.getID() == 402) && channel.getMainController().getCurrentPlayer().getMana() > (int) Math.ceil((float) this.summonedCard.getLevel() / 2)) {
                                                summonedCard.addActiveSpell((SpellCard) card);
                                                sourceController.destroyCard();
                                                channel.setSourcePlan(false);
                                                channel.getMainController().getCurrentPlayer().decreaseMana((int) Math.ceil((float) this.summonedCard.getLevel() / 2));
                                                channel.getMainController().updateUIText();

                                                channel.getMainController().setSummonedDescription(this.summonedCard);
                                                if (summonedCard.isDead()) {
                                                    this.summonedCard.setEmpty(true);
                                                    this.card_pane.setOpacity(0);
                                                }
                                            }
                                        } else {
                                            summonedCard.addActiveSpell((SpellCard) card);
                                            sourceController.destroyCard();
                                            channel.setSourcePlan(false);
                                            channel.getMainController().getCurrentPlayer().decreaseMana(card.getMana());
                                            channel.getMainController().updateUIText();
                                            channel.getMainController().setSummonedDescription(this.summonedCard);
                                            if (summonedCard.isDead()) {
                                                this.summonedCard.setEmpty(true);
                                                this.card_pane.setOpacity(0);
                                            }
                                            this.updateCard();
                                        }
                                    }
                                }
                                break;
                            case ATTACK:
                                if (!this.summonedCard.isEmpty()) {
                                    if (channel.isSourceAttack() && !channel.getSummonedController(channel.getMainController().getCurrentPlayerIDX()).contains(this)) {
                                        SummonedCardController sourceController = channel.getSourceAttackController();
                                        sourceController.getSummonedCard().Attack(this.summonedCard);
                                        if (this.summonedCard.isDead() && !sourceController.getSummonedCard().isDead()) {
                                            this.summonedCard.setEmpty(true);
                                            this.card_pane.setOpacity(0);
                                            sourceController.getSummonedCard().addExp(this.summonedCard.getLevel());
                                        } else if (sourceController.getSummonedCard().isDead() && !this.summonedCard.isDead()) {
                                            sourceController.getSummonedCard().setEmpty(true);
                                            sourceController.card_pane.setOpacity(0);
                                            this.summonedCard.addExp(sourceController.getSummonedCard().getLevel());
                                        } else if (this.summonedCard.isDead() && sourceController.getSummonedCard().isDead()) {
                                            this.summonedCard.setEmpty(true);
                                            this.card_pane.setOpacity(0);
                                            sourceController.getSummonedCard().setEmpty(true);
                                            sourceController.card_pane.setOpacity(0);
                                        }
                                        this.updateCard();
                                        if (this.summonedCard.isEmpty()) {
                                            channel.getMainController().description_pane.setOpacity(0);
                                        } else {
                                            channel.getMainController().setSummonedDescription(this.summonedCard);
                                        }
                                        sourceController.updateCard();
                                        sourceController.getSummonedCard().setHasAttacked(true);
                                        channel.setSourceAttack(false);
                                    } else if (channel.getSummonedController(channel.getMainController().getCurrentPlayerIDX()).contains(this)) {
                                        if (!this.summonedCard.hasSummoned() && !this.summonedCard.hasAttacked()) {
                                            out.println("PASS ATTACK MODE");
                                            channel.setSourceAttack(true);
                                            channel.setSourceAttackController(this);
                                        }
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    }else if(event.getButton() == MouseButton.SECONDARY){
                        if(channel.getSummonedController(channel.getMainController().getCurrentPlayerIDX()).contains(this)){
                            if(channel.getMainController().getCurrentPlayer().getMana() > 0 && this.summonedCard.getLevel() < 10){
                                this.summonedCard.addExp(1);
                                channel.getMainController().getCurrentPlayer().decreaseMana(1);
                                channel.getMainController().updateUIText();
                                this.updateCard();
                                channel.getMainController().setSummonedDescription(this.summonedCard);
                            }
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
        if (this.summonedCard.isEmpty()) return;
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
        if(summonedCard.getLevel() < 10) {
            level_exp_text.setText(this.summonedCard.getExp() + "/"
                    + this.summonedCard.getExpToNextLevel() + " ["
                    + this.summonedCard.getLevel() + "]");
        }
        else{
            level_exp_text.setText("MAX ["
                    + this.summonedCard.getLevel() + "]");
        }
    }

}
