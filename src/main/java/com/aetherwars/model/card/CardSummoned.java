package com.aetherwars.model.card;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;

public class CardSummoned {
    private AnchorPane pane;
    private ImageView image;
    private CharacterCard characterCard;
    private ArrayList<SpellCard> spells;
    private int level;

    public CardSummoned(AnchorPane pane, ImageView image, CharacterCard characterCard){
        this.pane = pane;
        this.image = image;
        this.characterCard = characterCard;
        this.spells = new ArrayList<SpellCard>();
        this.level = 1;
    }



}
