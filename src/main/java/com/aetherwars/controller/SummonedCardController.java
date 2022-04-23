package com.aetherwars.controller;

import com.aetherwars.model.card.SummonedCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SummonedCardController implements Initializable {


    @FXML
    Text health_text;
    @FXML
    Text attack_text;
    @FXML
    Text level_exp_text;
    @FXML
    ImageView image_card;

    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setCard(SummonedCard card) {
        File file = null;
        try {
            file = new File(getClass().getResource("../" + card.getImagePath()).toURI());
        }
        catch (Exception e) {
            System.out.println("Error loading image: " + e);
        }
;       Image image = new Image(file.toURI().toString());
        image_card.setImage(image);
        health_text.setText(String.valueOf(card.getTotalHealth()));
        attack_text.setText(String.valueOf(card.getTotalAttack()));
        level_exp_text.setText(card.getExp() + "/" + card.getExpToNextLevel() + " [" + card.getLevel() + "]");
    }
}
