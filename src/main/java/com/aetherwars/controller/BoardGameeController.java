package com.aetherwars.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardGameeController implements Initializable {
    @FXML
    private Label draw_label;
    @FXML
    private Label plan_label;
    @FXML
    private Label attack_label;
    @FXML
    private Label end_label;
    @FXML
    private Button next_phase;
    @FXML
    private VBox hovered_card_view;
    @FXML
    private Label description_hovered_card;
    @FXML
    private Pane panePlayer1;
    @FXML
    private Pane panePlayer2;

    AnchorPane[] playerDeck;

    // if needed, insert parameters
    public BoardGameeController() {
        this.playerDeck = new AnchorPane[3];
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Initialling player 1 Deck
            FXMLLoader player1Deck = new FXMLLoader(getClass().getResource("../views/deckPlayer1.fxml"));
            playerDeck[1] = player1Deck.load();
            this.panePlayer1.getChildren().add(playerDeck[1]);

            // Initializing player 2 Deck
            FXMLLoader player2Deck = new FXMLLoader(getClass().getResource("../views/deckPlayer2.fxml"));
            playerDeck[2] = player2Deck.load();
            this.panePlayer2.getChildren().add(playerDeck[2]);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
