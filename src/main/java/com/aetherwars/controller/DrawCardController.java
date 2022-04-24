package com.aetherwars.controller;


import com.aetherwars.model.folder.GameChannel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class DrawCardController implements  Initializable{

    @FXML
    StackPane drawCardPane;

    @FXML
    Pane draw_slot1, draw_slot2, draw_slot3;

    @FXML
    private HandCardController[] handCardController;
    private Pane[] draw_slots;
    private StackPane slotPane;

    private GameChannel channel;

    public DrawCardController(GameChannel channel){
        this.channel = channel;
    }

    public void  initialize(URL location, ResourceBundle resources){
        try {
            handCardController = new HandCardController[3];
            draw_slots = new Pane[]{draw_slot1, draw_slot2, draw_slot3};
            for (int i = 0; i < 3; i++) {
                FXMLLoader handCardLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/HandCard.fxml"));
                handCardLoader.setControllerFactory(c -> new HandCardController(channel));
                slotPane = handCardLoader.load();
                handCardController[i] = handCardLoader.getController();
                draw_slots[i].getChildren().add(slotPane);
            }
        }
        catch (Exception e){
            System.out.println("Error draw card controller: ");
            e.printStackTrace();
        }
    }

    public void setVisible(boolean visible){
        drawCardPane.setVisible(visible);
    }

    public HandCardController[] getHandCardController(){
        return handCardController;
    }

}
