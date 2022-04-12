package com.aetherwars.model;

public class Card {
    protected int id;
    protected String name;
    protected String description;
    protected CardType cardType;

    public Card(){
        this.id = 0;
        this.name = "";
        this.description = "";
        this.cardType = CardType.CHARACTER;
    }

    public Card(int id, String name, String description, CardType cardType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cardType = cardType;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public CardType getCardType() { return this.cardType; }

    @Override
    public String toString() {
        return "ID: " + this.id + "\nName: " + this.name + "\nDescription: " + this.description + "\nCard Type: " + this.cardType;
    }
}
