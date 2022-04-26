package com.aetherwars.controller;


import com.aetherwars.model.event.GameChannel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class DrawCardController implements  Initializable{

    @FXML
    StackPane draw_card_pane;

    @FXML
    HBox draw_card_box;

    private GameChannel channel;

    public DrawCardController(GameChannel channel){
        this.channel = channel;
    }

    public void  initialize(URL location, ResourceBundle resources){

    }

    public void setVisible(boolean visible){
        draw_card_pane.setVisible(visible);
    }



}
