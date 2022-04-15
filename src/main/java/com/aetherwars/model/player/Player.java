package com.aetherwars.model.player;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.CharacterCard;
import com.aetherwars.model.card.SpellCard;

import java.util.ArrayList;
import java.util.List;


public class Player {
    private String name;
    private int health;
    private int mana;
    private ArrayList<Card> handCard;
    private List<SummonedCharacter> characterBoard;
    // private List<ArrayList<SpellCard>> spellBoard;

    public Player(String name){
        this.name = name;
        this.health = 80;
        this.mana = 0;
    }

    public void draw(Card card){
        handCard.add(card);
    }

    public void discard(Card card){
        handCard.remove(card);
    }

    public void playChar(CharacterCard card, int position){
        SummonedCharacter summon = new SummonedCharacter(card);
        characterBoard.set(position, summon);
        discard(card);
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
