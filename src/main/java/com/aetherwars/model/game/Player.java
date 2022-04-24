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
        this.deckCard = deckCard;
    }

    public void addCard(List<Card> cards){
        cards.forEach(c ->{
            int min = 0;
            int max = deckCard.size() - 1;
            int random = (int)Math.floor(Math.random()*(max-min+1)+min);
            deckCard.add(random, c);
        });
    }

    public List<Card> draw() {
        deckCard.forEach(c -> c.toString());
        List<Card> drawnCard = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            drawnCard.add(deckCard.remove(0));
        }
        drawnCard.forEach(c -> System.out.println(c.toString()));
        return drawnCard;
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
