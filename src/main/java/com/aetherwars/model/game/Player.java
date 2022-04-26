package com.aetherwars.model.game;

import com.aetherwars.model.card.Card;

import java.util.ArrayList;
import java.util.List;


public class Player {
    private String name;
    private int health;
    private int mana;
    private List<Card> handCard;
    private List<Card> deckCard;
    private int initialDeckSize;
    private int initialMana;


    public Player(String name, List<Card> deckCard, int initialDeckSize){
        handCard = new ArrayList<>();
        this.name = name;
        this.health = 1;
        this.mana = 1;
        this.initialMana = 1;
        this.initialDeckSize = initialDeckSize;
        this.deckCard = deckCard;
    }

    public void addHandCard(Card card){
        handCard.add(card);
    }


    public void addDeckCard(List<Card> cards){
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
        for(int i = 0; i < 3 && !deckCard.isEmpty(); i++){
            drawnCard.add(deckCard.remove(0));
        }
        drawnCard.forEach(c -> System.out.println(c.toString()));
        return drawnCard;
    }

    public List<Card> getHandCardList(){
        return handCard;
    }

    public int getHealth(){
        return health;
    }

    public void takeDamage(int damage){
        if(damage > health) {
            this.health = 0;
        }else{
            this.health -= damage;
        }
    }

    public void decreaseMana(int mana){
        this.mana -= mana;
    }

    public void increaseMana(){
        if(this.initialMana < 10) {
            this.initialMana += 1;
        }
    }

    public void resetMana(){
        this.mana = this.initialMana;
    }


    public int getDeckSize(){
        return this.deckCard.size();
    }

    public int getInitialDeckSize() {
        return this.initialDeckSize;
    }

    public int getInitialMana(){
        return this.initialMana;
    }

    public int getMana(){
        return this.mana;
    }

    public void heal(int heal){
        this.health += heal;
    }

    public void setMana(int mana){
        this.mana = mana;
    }
}
