package com.aetherwars.controller;

import com.aetherwars.model.card.*;
import com.aetherwars.model.folder.GameChannel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HandCardController implements Initializable {

    @FXML
    Text card_type_text;

    @FXML
    ImageView image_card;
    private Card card;

    @FXML
    Pane cardPane;

    private GameChannel gameChannel;


    public HandCardController(GameChannel gameChannel){
        this.gameChannel = gameChannel;
    }

    public void initialize(URL url, ResourceBundle rb) {
        try {
            cardPane.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    switch (gameChannel.getPhase()) {
                        case DRAW:
                           gameChannel.getMainController().addCard(card);
                    }
                }
            });
        }
        catch (Exception e) {
            System.out.println("Error Hand Controller: " + e);
            e.printStackTrace();
        }
    }

    public void setCard(Card card) {
        this.card = card;
        updateCard();
    }

    public void updateCard() {
        File file = null;
        try {
            file = new File(getClass().getResource("../" + this.card.getImagePath()).toURI());
        }
        catch (Exception e) {
            System.out.println("Error loading image: " + e);
        };
        Image image = new Image(file.toURI().toString());
        image_card.setImage(image);
        if(card instanceof CharacterCard){
            card_type_text.setText("CHARACTER");
        }else if(card instanceof PotionSpellCard){
            card_type_text.setText("POTION");
        }else if(card instanceof MorphSpellCard){
            card_type_text.setText("MORPH");
        }else if(card instanceof  SwapSpellCard){
            card_type_text.setText("SWAP");
        }


//        switch(card.getCardType()){
//            case CHARACTER:
//                card_type_text.setText("CHARACTER");
//                break;
//            default:
//                SpellCard spellCard = (SpellCard) card;
//                switch(spellCard.getSpellType()){
//                    case PTN:
//                        card_type_text.setText("POTION");
//                        break;
//                    case MORPH:
//                        card_type_text.setText("MORPH");
//                        break;
//                    case SWAP:
//                        card_type_text.setText("SWAP");
//                        break;
//                }
//        }
    }
}
