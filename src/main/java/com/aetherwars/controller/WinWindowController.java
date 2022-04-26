package com.aetherwars.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class WinWindowController implements Initializable {
    @FXML
    Text player_text;

    private String player;

    public WinWindowController(String player){
        this.player = player;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        player_text.setText(player);
    }
}
