package com.aetherwars.model.card;

public abstract class Card {
    protected int id;
    protected String name;
    protected String description;
    protected String IMAGE_PATH;
    protected int mana;
    protected CardType cardType;

    public Card(){
        this.id = 0;
        this.name = "";
        this.description = "";
        this.cardType = CardType.CHARACTER;
        this.mana = 0;
    }

    public Card(int id, String name, int mana, String description, CardType cardType, String IMAGE_PATH) {
        this.id = id;
        this.name = name;
        this.mana = mana;
        this.description = description;
        this.cardType = cardType;
        this.IMAGE_PATH = IMAGE_PATH;
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

    public int getMana(){
        return this.mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public String getImagePath() { return this.IMAGE_PATH; }

    @Override
    public String toString() {
        return "ID: " + this.id + "\nName: " + this.name + "\nDescription: " + this.description + "\nCard Type: " + this.cardType;
    }
}
