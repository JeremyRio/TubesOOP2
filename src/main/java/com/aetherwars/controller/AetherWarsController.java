package com.aetherwars.controller;

import com.aetherwars.model.card.*;
import com.aetherwars.model.event.GameChannel;
import com.aetherwars.model.game.Phase;
import com.aetherwars.model.game.Player;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.fxml.FXML;


import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.System.out;

public class AetherWarsController implements Initializable {

    @FXML
    AnchorPane player1_zone1, player1_zone2, player1_zone3, player1_zone4, player1_zone5;

    @FXML
    AnchorPane player2_zone1, player2_zone2, player2_zone3, player2_zone4, player2_zone5;

    @FXML
    ImageView player1_avatar, player2_avatar;

    @FXML
    StackPane game_zone;

    @FXML
    Button phase_button;

    @FXML
    Rectangle draw, plan, attack, end;

    @FXML
    HBox hand_card_box;

    @FXML
    Text player1_health_text, player2_health_text;

    @FXML
    Text mana_text, deck_text;

    @FXML
    Label description_label, stats_label, card_name_label, attack_bonus_label, hp_bonus_label;

    @FXML
    ImageView description_image;

    @FXML
    Pane description_pane;

    private AnchorPane[][] player_board;
    private int current_player = 0;
    private int phase_idx = 1;
    private Player player1, player2;
    private Player[] playerList;
    private GameChannel channel;
    private Rectangle[] phase_rectangle;
    private Phase[] phase = new Phase[] {Phase.DRAW, Phase.PLAN, Phase.ATTACK, Phase.END};
    private DrawCardController drawCardController;
    private List<Card> drawnCard;

    public AetherWarsController(Player player1, Player player2, GameChannel channel){
        this.player1 = player1;
        this.player2 = player2;
        this.channel = channel;
    }

    public void initialize(URL Location, ResourceBundle resources) {
        try {
            player_board = new AnchorPane[2][];
            player_board[0] = new AnchorPane[]{player1_zone1, player1_zone2, player1_zone3, player1_zone4, player1_zone5};
            player_board[1] = new AnchorPane[]{player2_zone1, player2_zone2, player2_zone3, player2_zone4, player2_zone5};
            playerList = new Player[]{player1, player2};
            phase_rectangle = new Rectangle[]{draw, plan, attack, end};

            StackPane summonedPane = null;
            StackPane drawCardPane = null;

            FXMLLoader drawCardLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/DrawCard.fxml"));
            drawCardLoader.setControllerFactory(c -> new DrawCardController(channel));
            game_zone.getChildren().add(drawCardLoader.load());
            drawCardController = drawCardLoader.getController();

            for(int i = 0; i < 5; i++){
                FXMLLoader summonedCardLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/SummonedCard.fxml"));
                summonedCardLoader.setControllerFactory(c -> new SummonedCardController(channel));
                summonedPane = summonedCardLoader.load();
                channel.addSummonedController(0, summonedCardLoader.getController());
                player_board[0][i].getChildren().add(summonedPane);
            }

            for(int i = 0; i < 5; i++){
                FXMLLoader summonedCardLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/SummonedCard.fxml"));
                summonedCardLoader.setControllerFactory(c -> new SummonedCardController(channel));
                summonedPane = summonedCardLoader.load();
                channel.addSummonedController(1, summonedCardLoader.getController());
                player_board[1][i].getChildren().add(summonedPane);
            }

            player1_health_text.setText(Integer.toString(playerList[0].getHealth()));
            player2_health_text.setText(Integer.toString(playerList[1].getHealth()));
            updateUIText();
            drawCard();
            resetDescription();

            player1_avatar.setOnMouseClicked(event -> {
                if(event.getButton() == MouseButton.PRIMARY){
                    if(channel.getPhase() == Phase.ATTACK && this.isSummonedZoneEmpty(channel.getSummonedController(0)) && channel.isSourceAttack() && !channel.getSummonedController(0).contains(channel.getSourceAttackController())){
                        playerList[0].takeDamage(channel.getSourceAttackController().getSummonedCard().getTotalAttack());
                        channel.getSourceAttackController().getSummonedCard().setHasAttacked(true);
                        channel.setSourceAttack(false);
                        player1_health_text.setText(Integer.toString(playerList[0].getHealth()));
                        if(playerList[0].getHealth() == 0){
                            createWinWindow("PLAYER 2");
                        }
                    }
                }
            });

            player2_avatar.setOnMouseClicked(event -> {
                if(event.getButton() == MouseButton.PRIMARY){
                    if(channel.getPhase() == Phase.ATTACK && this.isSummonedZoneEmpty(channel.getSummonedController(1)) && channel.isSourceAttack() && !channel.getSummonedController(1).contains(channel.getSourceAttackController())){
                        playerList[1].takeDamage(channel.getSourceAttackController().getSummonedCard().getTotalAttack());
                        channel.getSourceAttackController().getSummonedCard().setHasAttacked(true);
                        channel.setSourceAttack(false);
                        player2_health_text.setText(Integer.toString(playerList[1].getHealth()));
                        if(playerList[1].getHealth() == 0){
                            createWinWindow("PLAYER 1");
                        }
                    }
                }
            });

            phase_button.setOnAction(e -> {
                if(channel.getPhase() == Phase.END && getCurrentPlayer().getHandCardList().size() == 5){
                    out.println("THROW A CARD!");
                } else{
                    switchPhase();
                    switch(channel.getPhase()){
                        case DRAW:
                            this.current_player = (this.current_player + 1) % 2;
                            channel.getSummonedController(0).forEach(controller -> {
                                controller.getSummonedCard().updateDuration();
                                controller.updateCard();
                            });
                            channel.getSummonedController(1).forEach(controller -> {
                                controller.getSummonedCard().updateDuration();
                                controller.updateCard();
                            });
                            getCurrentPlayer().resetMana();
                            updateUIText();
                            drawCard();
                            break;
                        case END:
                            channel.getSummonedController(this.current_player).forEach(controller -> {
                                controller.getSummonedCard().setHasSummoned(false);
                                controller.getSummonedCard().setHasAttacked(false);
                            });
                            getCurrentPlayer().increaseMana();
                            break;
                        default:
                            break;
                        }
                    }
                }
            );
        }
        catch (Exception e) {
            out.println("Error in AetherWarsController: ");
            e.printStackTrace();
        }
    }

    public void updateUIText(){
        mana_text.setText(getCurrentPlayer().getMana() + "/" + getCurrentPlayer().getInitialMana());
        deck_text.setText(getCurrentPlayer().getDeckSize() + "/" + getCurrentPlayer().getInitialDeckSize());
    }

    public void createWinWindow(String player){
        try {
            FXMLLoader winWindowLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/WinWindow.fxml"));
            winWindowLoader.setControllerFactory(c -> new WinWindowController(player));
            game_zone.getChildren().add(winWindowLoader.load());
        }catch (Exception e){
            out.println("Error in CreateWinWindow: " + e);
            e.printStackTrace();
        }
    }

    public boolean isSummonedZoneEmpty(List<SummonedCardController> controllerList){
        for(SummonedCardController controller : controllerList){
            if(!controller.getSummonedCard().isEmpty()){
                return false;
            }
        }
        return true;
    }

    public Player getCurrentPlayer(){
        return playerList[current_player];
    }

    public int getCurrentPlayerIDX(){
        return current_player;
    }

    public void addDeckCard(Card card){
        try {
            drawnCard.remove(card);
            getCurrentPlayer().addDeckCard(drawnCard);
            updateUIText();
            getCurrentPlayer().addHandCard(card);
            List<Card> handCard = getCurrentPlayer().getHandCardList();
            for (Card c : handCard) {
                FXMLLoader handCardLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/HandCard.fxml"));
                handCardLoader.setControllerFactory(controller -> new HandCardController(channel));
                hand_card_box.getChildren().add(handCardLoader.load());
                HandCardController controller = handCardLoader.getController();
                controller.setCard(c);
                channel.addHandCardController(controller);
            }
            drawCardController.setVisible(false);
            this.switchPhase();
        } catch (Exception e) {
            out.println("Error in addDeckCard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void resetDescription(){
        description_pane.setOpacity(0);
        hp_bonus_label.setText("");
        attack_bonus_label.setText("");
        description_label.setText("");
        card_name_label.setText("");
        stats_label.setText("");
        description_image.setImage(null);
    }

    public void setSummonedDescription(SummonedCard summonedCard){
        File file = null;
        try {
            file = new File(getClass().getResource("../" + summonedCard.getImagePath()).toURI());
        }
        catch (Exception e) {
            out.println("Error loading image: " + e);
        };
        description_pane.setOpacity(1);
        description_image.setImage(new Image(file.toURI().toString()));
        description_label.setText(summonedCard.getCharacterCard().getDescription());
        card_name_label.setText(summonedCard.getCharacterCard().getName());
        out.println("BONUS ATTACK: " + summonedCard.getBonusAttack());
        if(summonedCard.getBonusAttack() > 0){
            attack_bonus_label.setStyle("-fx-text-fill: green");
            attack_bonus_label.setText("(+" + summonedCard.getBonusAttack() + ")");
        }else if (summonedCard.getBonusAttack() < 0){
            attack_bonus_label.setStyle("-fx-text-fill: red");
            attack_bonus_label.setText("(" + summonedCard.getBonusAttack() + ")");
        }
        if(summonedCard.getBonusHealth() > 0){
            hp_bonus_label.setStyle("-fx-text-fill: green");
            hp_bonus_label.setText("(+" + summonedCard.getBonusHealth() + ")");
        }else if (summonedCard.getBonusAttack() < 0){
            hp_bonus_label.setStyle("-fx-text-fill: red");
            hp_bonus_label.setText("(" + summonedCard.getBonusHealth() + ")");
        }
        stats_label.setText(
                "      HP: " + summonedCard.getCharacterCard().getHealth() + "\n" +
                "     ATK: " + summonedCard.getCharacterCard().getAttack() + "\n" +
                "     LVL: " + summonedCard.getLevel() + "\n" +
                "     EXP: " + summonedCard.getExp() + "\n" +
                "    TYPE: " + summonedCard.getCharacterCard().getCharacterType() + "\n\n" +
                        (summonedCard.getActiveSpells().size() > 0 ? "ACTIVE_SPELL: " + "\n" + summonedCard.toString() : "")
        );
    }

    public void setDescription(Card card){
        File file = null;
        try {
            file = new File(getClass().getResource("../" + card.getImagePath()).toURI());
        }
        catch (Exception e) {
            out.println("Error loading image: " + e);
        };
        description_pane.setOpacity(1);
        description_image.setImage(new Image(file.toURI().toString()));
        description_label.setText(card.getDescription());
        card_name_label.setText(card.getName());
        stats_label.setText(getStatusCard(card));
    }

    public String getStatusCard(Card card){
        String message = null;
        if(card instanceof CharacterCard){
            CharacterCard castCard = (CharacterCard) card;
            message  =
            "      HP: " + castCard.getHealth() + "\n" +
            "     ATK: " + castCard.getAttack() + "\n" +
            "  ATK_UP: " + castCard.getAttackUp() + "\n" +
            "   HP_UP: " + castCard.getHealthUp();
        }
        else if (card instanceof MorphSpellCard){
            MorphSpellCard castCard = (MorphSpellCard) card;
            message  =
            "TURN_INTO: " + (channel.getCardFromMap(castCard.getTargetID())).getName();
        }
        else if (card instanceof PotionSpellCard){
            PotionSpellCard castCard = (PotionSpellCard) card;
            message =
            "      HP: " + castCard.getHP() + "\n" +
            "     ATK: " + castCard.getAttack() + "\n" +
            "DURATION: " + (castCard.getDuration() == 0 ? "PERMANENT" : castCard.getDuration());
        }
        else if (card instanceof  SwapSpellCard){
            SwapSpellCard castCard = (SwapSpellCard) card;
            message =
            "DURATION: " + (castCard.getDuration() == 0 ? "PERMANENT" : castCard.getDuration());
        }
        return message;
    }

    public HBox getHandCardBox(){
        return hand_card_box;
    }

    public void drawCard(){
        drawCardController.setVisible(true);
        channel.getHandCardController().clear();
        hand_card_box.getChildren().clear();
        drawnCard = new ArrayList<>();
        drawnCard = playerList[current_player].draw();
        drawCardController.draw_card_box.getChildren().clear();
        drawnCard.forEach(c -> {
            try {
                FXMLLoader handCardLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/HandCard.fxml"));
                handCardLoader.setControllerFactory(controller -> new HandCardController(channel));
                drawCardController.draw_card_box.getChildren().add(handCardLoader.load());
                HandCardController controller = handCardLoader.getController();
                controller.setCard(c);
            }catch (Exception e){
                out.println("Error in DrawCard: " + e);
                e.printStackTrace();
            }
        });
    }

    public void switchPhase(){
        int prev_phase_idx = phase_idx - 1;
        if (prev_phase_idx < 0) prev_phase_idx = 3;
        channel.setPhase(phase[phase_idx]);
        phase_rectangle[phase_idx].setStyle("-fx-fill: #ff8e3d");
        phase_rectangle[prev_phase_idx].setStyle("-fx-fill: #ffffff");
        phase_idx = (phase_idx + 1) % 4;
    }

}