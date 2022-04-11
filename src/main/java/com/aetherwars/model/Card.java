package com.aetherwars.model;

public class Card {
    protected int id;
    protected String name;
    protected String description;

    public Card(){
        this.id = 0;
        this.name = "";
        this.description = "";
    }

    public Card(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
