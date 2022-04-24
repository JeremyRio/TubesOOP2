package com.aetherwars.controller;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.folder.GameChannel;
import com.aetherwars.model.game.Phase;
import com.aetherwars.model.game.Player;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AetherWarsController implements Initializable {

    @FXML
    AnchorPane player1_zone1, player1_zone2, player1_zone3, player1_zone4, player1_zone5;
    @FXML
    AnchorPane player2_zone1, player2_zone2, player2_zone3, player2_zone4, player2_zone5;

    @FXML
    AnchorPane game_zone;

    @FXML
    Button phase_button;

    @FXML
    Rectangle draw, plan, attack, end;

    private SummonedCardController[][] player_summonedCardController;
    private AnchorPane[][] player_board;
    private int current_player = 0;
    private int phase_idx = 1;
    private Player player1, player2;
    private Player[] playerList;
    private GameChannel channel;
    private Rectangle[] phase_rectangle;
    private Phase[] phase = new Phase[] {Phase.DRAW, Phase.PLAN, Phase.ATTACK, Phase.END};
    private DrawCardController drawCardController;
    private StackPane drawCardPane;
    private List<Card> drawnCard;

    public AetherWarsController(Player player1, Player player2, GameChannel channel){
        this.player1 = player1;
        this.player2 = player2;
        this.channel = channel;
    }

    public void initialize(URL Location, ResourceBundle resources) {
        try {
            player_summonedCardController = new SummonedCardController[2][];
            player_board = new AnchorPane[2][];
            player_board[0] = new AnchorPane[]{player1_zone1, player1_zone2, player1_zone3, player1_zone4, player1_zone5};
            player_board[1] = new AnchorPane[]{player2_zone1, player2_zone2, player2_zone3, player2_zone4, player2_zone5};
            playerList = new Player[]{player1, player2};
            player_summonedCardController[0] = new SummonedCardController[5];
            player_summonedCardController[1] = new SummonedCardController[5];
            phase_rectangle = new Rectangle[]{draw, plan, attack, end};

            AnchorPane summonedPane = null;
            StackPane drawCardPane = null;

            FXMLLoader drawCardLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/DrawCard.fxml"));
            drawCardLoader.setControllerFactory(c -> new DrawCardController(channel));
            drawCardPane = drawCardLoader.load();
            drawCardController = drawCardLoader.getController();
            game_zone.getChildren().add(drawCardPane);


            for(int i = 0; i < 5; i++){
                FXMLLoader summonedCardLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/SummonedCard.fxml"));
                summonedCardLoader.setControllerFactory(c -> new SummonedCardController(channel));
                summonedPane = summonedCardLoader.load();
                player_summonedCardController[0][i] = summonedCardLoader.getController();
                player_board[0][i].getChildren().add(summonedPane);
            }

            for(int i = 0; i < 5; i++){
                FXMLLoader summonedCardLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/SummonedCard.fxml"));
                summonedCardLoader.setControllerFactory(c -> new SummonedCardController(channel));
                summonedPane = summonedCardLoader.load();
                player_summonedCardController[0][i] = summonedCardLoader.getController();
                player_board[0][i].getChildren().add(summonedPane);
            }

            drawCard();

            phase_button.setOnAction(e -> {
                switchPhase();
                    }
            );
        }
        catch (Exception e) {
            System.out.println("Error in AetherWarsController: ");
            e.printStackTrace();
        }
    }

    public void addCard(Card card){
        try {
            drawnCard.remove(card);
            playerList[current_player].addCard(drawnCard);

            drawCardController.setVisible(false);
            this.switchPhase();
        } catch (Exception e) {
            System.out.println("Error in addDeckCard: " + e);
            e.printStackTrace();
        }
    }

    public void drawCard(){
        drawCardController.setVisible(true);
        drawnCard = new ArrayList<>();
        drawnCard = playerList[current_player].draw();
        HandCardController[] controller = drawCardController.getHandCardController();
        for(int i = 0; i < drawnCard.size(); i++){
            controller[i].setCard(drawnCard.get(i));
        }
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