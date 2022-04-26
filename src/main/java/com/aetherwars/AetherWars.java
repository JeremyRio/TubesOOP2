package com.aetherwars;

import com.aetherwars.controller.AetherWarsController;
import com.aetherwars.model.event.GameChannel;
import com.aetherwars.model.game.*;
import com.aetherwars.model.game.CardDealer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static java.lang.System.out;

public class AetherWars extends Application {

  @Override
  public void start(Stage stage) throws Exception {

    try {
      CardDealer cardDealer = new CardDealer();
      Player player1 = new Player("Steve", cardDealer.getPlayer1Deck(), cardDealer.getPlayer1Deck().size());
      Player player2 = new Player("Alex", cardDealer.getPlayer2Deck(), cardDealer.getPlayer2Deck().size());

      GameChannel gameChannel = new GameChannel();
      FXMLLoader aetherWarsLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/AetherWars.fxml"));
      out.println("PASS MAIN");
      aetherWarsLoader.setControllerFactory(c -> new AetherWarsController(player1, player2, gameChannel));
      Parent root = aetherWarsLoader.load();
      out.println("END MAIN");

      AetherWarsController mainController = aetherWarsLoader.getController();
      gameChannel.setMainController(mainController);

      stage.setTitle("Aether Wars");
      stage.setScene(new Scene(root));
      stage.setFullScreen(false);
      stage.show();
    }
    catch (Exception e) {
      out.println("Error in AetherWars: ");
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
