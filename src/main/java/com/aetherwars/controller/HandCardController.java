package com.aetherwars.controller;

import com.aetherwars.model.card.*;
import com.aetherwars.model.event.GameChannel;
import com.aetherwars.util.ConfirmationBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.out;

public class HandCardController implements Initializable {

    @FXML
    Text card_type_text, mana_text;

    @FXML
    ImageView image_card;
    private Card card;

    @FXML
    StackPane card_pane;

    private GameChannel gameChannel;


    public HandCardController(GameChannel gameChannel){
        this.gameChannel = gameChannel;
    }

    public void initialize(URL url, ResourceBundle rb) {
        try {
            card_pane.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    switch (gameChannel.getPhase()) {
                        case DRAW:
                            gameChannel.getMainController().addDeckCard(card);
                            break;
                        case PLAN:
                            out.println("PASS PLAN PHASE");
                            if(event.getClickCount() == 2){
                              if(ConfirmationBox.display(event.getScreenX(), event.getScreenY(), "Discarding HandCard", "Discarding ["+card.getName()+"]?")){
                                    destroyCard();
                              }
                            }else if(gameChannel.getMainController().getCurrentPlayer().getMana() >= this.card.getMana()){
                                    gameChannel.setSourcePlan(true);
                                    gameChannel.setSourcePlanController(this);
                                    out.println("Source: " + gameChannel.isSourcePlan());
                                    // gameChannel.getHandCardController().stream().filter(c -> c != this).forEach(
                                    //        controller -> {
                                    //            controller.getCardPane().setStyle("-fx-border-width: 4; -fx-border-color: #3D3107");
                                    //        }
                                    // );
                            }
                            break;
                        case END:
                            if(event.getClickCount() == 2){
                                if(ConfirmationBox.display(event.getScreenX(), event.getScreenY(), "Discarding HandCard", "Discarding ["+card.getName()+"]?")){
                                    destroyCard();
                                }
                            }
                    }
                }
            });
        }
        catch (Exception e) {
            out.println("Error Hand Controller: " + e);
            e.printStackTrace();
        }
    }

    public void destroyCard(){
        gameChannel.getMainController().getCurrentPlayer().getHandCardList().remove(card);
        ((HBox)card_pane.getParent()).getChildren().remove(card_pane);
        gameChannel.getHandCardController().remove(this);
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
            out.println("Error loading image: " + e);
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
        mana_text.setText("MANA " + card.getMana());
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

    public StackPane getCardPane(){
        return card_pane;
    }

    public Card getCard(){
        return card;
    }
}
