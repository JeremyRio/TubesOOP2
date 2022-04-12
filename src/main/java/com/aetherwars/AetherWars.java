package com.aetherwars;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.aetherwars.model.*;
import com.aetherwars.util.CSVReader;

public class AetherWars extends Application {
  private static final String CHARACTER_CSV_FILE_PATH = "card/data/character.csv";
  private static final String SPELL_MORPH_CSV_FILE_PATH = "card/data/spell_morph.csv";
  private static final String SPELL_PTN_CSV_FILE_PATH = "card/data/spell_ptn.csv";
  private static final String SPELL_SWAP_CSV_FILE_PATH = "card/data/spell_swap.csv";

  public void loadCards() throws IOException, URISyntaxException {
    File characterCSVFile = new File(getClass().getResource(CHARACTER_CSV_FILE_PATH).toURI());
    File morphCSVFile = new File(getClass().getResource(SPELL_MORPH_CSV_FILE_PATH).toURI());
    File ptnCSVFile = new File(getClass().getResource(SPELL_PTN_CSV_FILE_PATH).toURI());
    File swapCSVFile = new File(getClass().getResource(SPELL_SWAP_CSV_FILE_PATH).toURI());

    CSVReader csvReader = new CSVReader("\t");
    csvReader.setSkipHeader(true);
    List<String[]> characterRows = csvReader.read(characterCSVFile);
    List<String[]> morphRows = csvReader.read(morphCSVFile);
    List<String[]> ptnRows = csvReader.read(ptnCSVFile);
    List<String[]> swapRows = csvReader.read(swapCSVFile);
    HashMap<Integer, Card> cardMap = new HashMap<Integer, Card>();

    for (String[] row : characterRows) {
      CharacterCard c = new CharacterCard(Integer.parseInt(row[0]), row[1], row[3], CharacterType.valueOf(row[2]));
      cardMap.put(c.getID(), c);
    }

    for (String[] row : morphRows) {
      MorphSpellCard m = new MorphSpellCard(Integer.parseInt(row[0]), row[1], row[2], SpellType.MORPH, Integer.parseInt(row[5]), Integer.parseInt(row[4]));
      cardMap.put(m.getID(), m);
    }

    for (String[] row : ptnRows) {
      PotionSpellCard p = new PotionSpellCard(Integer.parseInt(row[0]), row[1], row[2], SpellType.PTN, Integer.parseInt(row[6]), Integer.parseInt(row[7]),Integer.parseInt(row[5]), Integer.parseInt(row[4]));
      cardMap.put(p.getID(), p);
    }

    for (String[] row : swapRows) {
      SpellCard s = new SpellCard(Integer.parseInt(row[0]), row[1], row[2], SpellType.SWAP, Integer.parseInt(row[5]), Integer.parseInt(row[4]));
    }

    for (Map.Entry<Integer, Card> card : cardMap.entrySet()){
      System.out.println(card.getValue().toString());
    }

    
  }

  @Override
  public void start(Stage stage) {
    Text text = new Text();
    text.setText("Loading...");
    text.setX(50);
    text.setY(50);

    Group root = new Group();
    root.getChildren().add(text);

    Scene scene = new Scene(root, 1280, 720);

    stage.setTitle("Minecraft: Aether Wars");
    stage.setScene(scene);
    stage.show();

    try {
      this.loadCards();
      text.setText("Minecraft: Aether Wars!");
    } catch (Exception e) {
      text.setText("Failed to load cards: " + e);
    }
  }

  public static void main(String[] args) {
    launch();
  }
}
