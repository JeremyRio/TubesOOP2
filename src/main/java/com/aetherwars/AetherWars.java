package com.aetherwars;

import com.aetherwars.controller.AetherWarsController;
import com.aetherwars.model.card.CharacterCard;
import com.aetherwars.model.card.SummonedCard;
import com.aetherwars.model.game.*;
import com.aetherwars.model.game.CardDealer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AetherWars extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    try {
      CardDealer cardDealer = new CardDealer();
      Player player1 = new Player("Steve", cardDealer.getPlayer1Deck());
      Player player2 = new Player("Alex", cardDealer.getPlayer2Deck());

      FXMLLoader aetherWarsLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/AetherWars.fxml"));
      Parent root = aetherWarsLoader.load();
      AetherWarsController gameController = aetherWarsLoader.getController();
      gameController.player1_summonedCardController.get(0).setCard(new SummonedCard((CharacterCard) cardDealer.getCardMap().get(1)));


      stage.setTitle("Aether Wars");
      stage.setScene(new Scene(root));
      stage.setFullScreen(false);
      stage.show();
    }
    catch (Exception e) {
      System.out.println("Error in AetherWars: " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
