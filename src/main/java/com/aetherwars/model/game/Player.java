package com.aetherwars.model.game;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.CharacterCard;

import java.util.ArrayList;
import java.util.List;


public class Player {
    private String name;
    private int health;
    private int mana;
    private List<Card> handCard;
    private List<Card> deckCard;

    public Player(String name, List<Card> deckCard){
        this.name = name;
        this.health = 80;
        this.mana = 0;
        this.handCard = new ArrayList<Card>();
        this.deckCard = deckCard;
    }

    public void draw(Card card){
        handCard.add(card);
    }

    public void discard(Card card){
        handCard.remove(card);
    }

    public void takeDamage(int damage){
        health -= damage;
    }

    public void heal(int heal){
        health += heal;
    }

    public void setMana(int mana){
        this.mana = mana;
    }
}
