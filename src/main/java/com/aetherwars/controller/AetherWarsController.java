package com.aetherwars.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AetherWarsController implements Initializable {

    @FXML
    AnchorPane player1_zone1, player1_zone2, player1_zone3, player1_zone4, player1_zone5;
    @FXML
    AnchorPane player2_zone1, player2_zone2, player2_zone3, player2_zone4, player2_zone5;

    public List<SummonedCardController> player1_summonedCardController;
    public List<SummonedCardController> player2_summonedCardController;
    public List<AnchorPane> player1_board;
    public List<AnchorPane> player2_board;



    public void initialize(URL Location, ResourceBundle resources) {
        try {
            player1_board = new ArrayList<AnchorPane>(Arrays.asList(player1_zone1, player1_zone2, player1_zone3, player1_zone4, player1_zone5));
            player2_board = new ArrayList<AnchorPane>(Arrays.asList(player2_zone1, player2_zone2, player2_zone3, player2_zone4, player2_zone5));
            player1_summonedCardController = new ArrayList<SummonedCardController>();
            player2_summonedCardController = new ArrayList<SummonedCardController>();

            AnchorPane summonedPane = null;

            for(int i = 0; i < 5; i++){
                FXMLLoader summonedCardLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/SummonedCard.fxml"));
                summonedCardLoader.setControllerFactory(c -> new SummonedCardController());
                summonedPane = summonedCardLoader.load();
                player1_summonedCardController.add(summonedCardLoader.getController());
                player1_board.get(i).getChildren().add(summonedPane);
            }
            player1_board.forEach(zone -> zone.setOpacity(1));
            player2_board.forEach(zone -> zone.setOpacity(1));
        }
        catch (Exception e) {
            System.out.println("Error in AetherWarsController: " + e);
        }
    }
}