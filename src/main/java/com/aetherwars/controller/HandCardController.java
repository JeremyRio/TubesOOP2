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

    @FXML
    StackPane card_pane;

    private Card card;
    private GameChannel gameChannel;


    public HandCardController(GameChannel gameChannel){
        this.gameChannel = gameChannel;
    }

    public void initialize(URL url, ResourceBundle rb) {
        try {

            card_pane.setOnMouseEntered(event -> {
                    gameChannel.getMainController().setDescription(this.card);
            });

            card_pane.setOnMouseExited(event -> {
                gameChannel.getMainController().resetDescription();
            });

            card_pane.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    switch (gameChannel.getPhase()) {
                        case DRAW:
                            gameChannel.getMainController().addDeckCard(this.card);
                            break;
                        case PLAN:
                            if(event.getClickCount() == 2){
                                destroyCard();
                            } else if(gameChannel.getMainController().getCurrentPlayer().getMana() >= this.card.getMana()){
                                gameChannel.getHandCardController().stream().filter(c -> !c.equals(this)).forEach(c -> {
                                c.card_pane.setStyle("-fx-border-width:  4; -fx-border-color: #3D3107; -fx-background-color: #ffffffff");
                                    });
                                this.card_pane.setStyle("-fx-border-width:  4; -fx-border-color: green; -fx-background-color: #ffffffff");
                                gameChannel.setSourcePlan(true);
                                gameChannel.setSourcePlanController(this);
                                out.println("Source: " + gameChannel.isSourcePlan());
                            }
                            break;
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
        }else if(card instanceof  LevelSpellCard){
            card_type_text.setText("LEVEL");
        }
        mana_text.setText("MANA " + (card instanceof LevelSpellCard ? "?" : card.getMana()));
    }

    public StackPane getCardPane(){
        return card_pane;
    }

    public Card getCard(){
        return card;
    }
}
