package com.aetherwars;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import com.aetherwars.controller.BoardGameeController;
import com.aetherwars.model.card.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.aetherwars.util.CSVReader;

public class AetherWars extends Application {
  private static final String CHARACTER_CSV_FILE_PATH = "card/data/character.csv";
  private static final String SPELL_MORPH_CSV_FILE_PATH = "card/data/spell_morph.csv";
  private static final String SPELL_PTN_CSV_FILE_PATH = "card/data/spell_ptn.csv";
  private static final String SPELL_SWAP_CSV_FILE_PATH = "card/data/spell_swap.csv";
  private static final String PLAYER1_DECK = "card/data/player1_deck.csv";

  public void loadCards() throws IOException, URISyntaxException {
    File characterCSVFile = new File(getClass().getResource(CHARACTER_CSV_FILE_PATH).toURI());
    File morphCSVFile = new File(getClass().getResource(SPELL_MORPH_CSV_FILE_PATH).toURI());
    File ptnCSVFile = new File(getClass().getResource(SPELL_PTN_CSV_FILE_PATH).toURI());
    File swapCSVFile = new File(getClass().getResource(SPELL_SWAP_CSV_FILE_PATH).toURI());
    File player1DeckFile = new File(getClass().getResource(PLAYER1_DECK).toURI());

    CSVReader csvReader = new CSVReader("\t");
    csvReader.setSkipHeader(true);
    List<String[]> characterRows = csvReader.read(characterCSVFile);
    List<String[]> morphRows = csvReader.read(morphCSVFile);
    List<String[]> ptnRows = csvReader.read(ptnCSVFile);
    List<String[]> swapRows = csvReader.read(swapCSVFile);
    List<Integer> deck1List = csvReader.readInt(player1DeckFile);

    HashMap<Integer, Card> cardMap = new HashMap<>();

    for (String[] row : characterRows) {
      CharacterCard c = new CharacterCard(Integer.parseInt(row[0]), row[1], row[3], row[4],
          CharacterType.valueOf(row[2]), Integer.parseInt(row[5]), Integer.parseInt(row[8]),
          Integer.parseInt(row[6]), Integer.parseInt(row[9]));
      cardMap.put(c.getID(), c);
    }

    for (String[] row : morphRows) {
      MorphSpellCard m = new MorphSpellCard(Integer.parseInt(row[0]), row[1], row[2], row[3],
          Integer.parseInt(row[5]), Integer.parseInt(row[4]));
      cardMap.put(m.getID(), m);

    }

    for (String[] row : ptnRows) {
      PotionSpellCard p = new PotionSpellCard(Integer.parseInt(row[0]), row[1], row[2], row[3],
          Integer.parseInt(row[6]), Integer.parseInt(row[7]), Integer.parseInt(row[5]),
          Integer.parseInt(row[4]));
      cardMap.put(p.getID(), p);
    }

    for (String[] row : swapRows) {
      SpellCard s = new SpellCard(Integer.parseInt(row[0]), row[1], row[2], row[3], SpellType.SWAP,
          Integer.parseInt(row[5]), Integer.parseInt(row[4]));
      cardMap.put(s.getID(), s);
    }

    for (Map.Entry<Integer, Card> card : cardMap.entrySet()) {
      System.out.println(card.getValue().toString());
    }

    ArrayList<Card> deck1 = new ArrayList<>();
    for (Integer id : deck1List) {
      deck1.add(cardMap.get(id));
    }

  }

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader aetherWarsLoader = new FXMLLoader(getClass().getResource("view/AetherWars.fxml"));
    Parent root = aetherWarsLoader.load();
    stage.setTitle("Aether Wars");
    stage.setScene(new Scene(root));
    stage.setFullScreen(false);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
