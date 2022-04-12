package com.aetherwars.model;

import java.util.ArrayList;
import java.util.List;


public class Player {
    private String name;
    private int health;
    private int mana;
    private ArrayList<Card> handCard;
    private List<CharacterCard> characterBoard;
    private List<ArrayList<SpellCard>> spellBoard;

    public Player(String name){
        this.name = name;
        this.health = 80;
        this.mana = 0;
    }

}
