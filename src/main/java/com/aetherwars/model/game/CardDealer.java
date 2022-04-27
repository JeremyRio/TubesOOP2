package com.aetherwars.model.game;

import com.aetherwars.model.card.*;
import com.aetherwars.util.CSVReader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardDealer {
    private static final String CHARACTER_CSV_FILE_PATH = "../../card/data/character.csv";
    private static final String SPELL_MORPH_CSV_FILE_PATH = "../../card/data/spell_morph.csv";
    private static final String SPELL_PTN_CSV_FILE_PATH = "../../card/data/spell_ptn.csv";
    private static final String SPELL_SWAP_CSV_FILE_PATH = "../../card/data/spell_swap.csv";
    private static final String SPELL_LEVEL_CSV_FILE_PATH = "../../card/data/spell_level.csv";
    private static final String PLAYER1_DECK = "../../card/data/player1_deck.csv";
    private static final String PLAYER2_DECK = "../../card/data/player2_deck.csv";
    private HashMap<Integer, Card> cardMap;
    ArrayList<Card> deck1;
    ArrayList<Card> deck2;


    public CardDealer() {
        try {
            this.loadCards();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void loadCards() throws IOException, URISyntaxException {
        File characterCSVFile = new File(getClass().getResource(CHARACTER_CSV_FILE_PATH).toURI());
        File morphCSVFile = new File(getClass().getResource(SPELL_MORPH_CSV_FILE_PATH).toURI());
        File ptnCSVFile = new File(getClass().getResource(SPELL_PTN_CSV_FILE_PATH).toURI());
        File swapCSVFile = new File(getClass().getResource(SPELL_SWAP_CSV_FILE_PATH).toURI());
        File levelCSVFile = new File(getClass().getResource(SPELL_LEVEL_CSV_FILE_PATH).toURI());
        File player1DeckFile = new File(getClass().getResource(PLAYER1_DECK).toURI());
        File player2DeckFile = new File(getClass().getResource(PLAYER2_DECK).toURI());

        CSVReader csvReader = new CSVReader("\t");
        csvReader.setSkipHeader(true);

        this.cardMap = new HashMap<>();
        this.deck1 = new ArrayList<>();
        this.deck2 = new ArrayList<>();
        List<String[]> characterRows = csvReader.read(characterCSVFile);
        List<String[]> morphRows = csvReader.read(morphCSVFile);
        List<String[]> ptnRows = csvReader.read(ptnCSVFile);
        List<String[]> swapRows = csvReader.read(swapCSVFile);
        List<String[]> levelRows = csvReader.read(levelCSVFile);
        List<Integer> deck1List = csvReader.readInt(player1DeckFile);
        List<Integer> deck2List = csvReader.readInt(player2DeckFile);


        for (String[] row : characterRows) {
            CharacterCard c = new CharacterCard(Integer.parseInt(row[0]), row[1], Integer.parseInt(row[7]), row[3], row[4],
                    CharacterType.valueOf(row[2]), Integer.parseInt(row[5]), Integer.parseInt(row[8]),
                    Integer.parseInt(row[6]), Integer.parseInt(row[9]));
            this.cardMap.put(c.getID(), c);
        }

        for (String[] row : morphRows) {
            MorphSpellCard m = new MorphSpellCard(Integer.parseInt(row[0]), row[1], row[2], row[3],
                    Integer.parseInt(row[5]), Integer.parseInt(row[4]), cardMap.get(Integer.parseInt(row[4])));
            this.cardMap.put(m.getID(), m);

        }

        for (String[] row : ptnRows) {
            PotionSpellCard p = new PotionSpellCard(Integer.parseInt(row[0]), row[1], row[2], row[3],
                    Integer.parseInt(row[6]), Integer.parseInt(row[7]), Integer.parseInt(row[5]),
                    Integer.parseInt(row[4]));
            this.cardMap.put(p.getID(), p);
        }

        for (String[] row : swapRows) {
            SpellCard s = new SwapSpellCard(Integer.parseInt(row[0]), row[1], row[2], row[3],
                    Integer.parseInt(row[5]), Integer.parseInt(row[4]));
            this.cardMap.put(s.getID(), s);
        }

        for (String[] row : levelRows) {
            LevelSpellCard l = new LevelSpellCard(Integer.parseInt(row[0]), row[1], row[2], row[3]);
            this.cardMap.put(l.getID(), l);
        }

//                for (Map.Entry<Integer, Card> card : this.cardMap.entrySet()) {
//                    System.out.println(card.getValue().toString());
//                }


        deck1List.forEach(id -> this.deck1.add(this.cardMap.get(id)));
        deck2List.forEach(id -> this.deck2.add(this.cardMap.get(id)));

    }

    public Card getCardFromMap(int targetID){
        return this.cardMap.get(targetID);
    }

    public List<Card> getPlayer1Deck(){
        return deck1;
    }

    public List<Card> getPlayer2Deck(){
        return deck2;
    }

    public HashMap<Integer, Card> getCardMap() {
        return cardMap;
    }
}
