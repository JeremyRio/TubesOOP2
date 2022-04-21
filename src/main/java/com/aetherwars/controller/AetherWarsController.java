package com.aetherwars.controller;

import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;

public class AetherWarsController  {

    @FXML private AnchorPane player1_zone1, player1_zone2, player1_zone3, player1_zone4, player1_zone5;
    @FXML private AnchorPane player2_zone1, player2_zone2, player2_zone3, player2_zone4, player2_zone5;


    AnchorPane[] player1_board = new AnchorPane[5];
    AnchorPane[] player2_board = new AnchorPane[5];

    public void initialize(){

    }
}