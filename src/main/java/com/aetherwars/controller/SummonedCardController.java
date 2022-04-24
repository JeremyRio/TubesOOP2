package com.aetherwars.controller;

import com.aetherwars.model.card.SummonedCard;
import com.aetherwars.model.folder.GameChannel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.input.MouseButton;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SummonedCardController implements Initializable {

    @FXML
    AnchorPane card_pane;
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
        this.card_pane.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        System.out.println("Clicked on card!");
                    }
                }
        );
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
            System.out.println("Error loading image: " + e);
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
