package com.aetherwars.model;

public class SpellCard extends Card {
    protected SpellType spellType;
    protected int mana;
    protected int duration;

    public SpellCard(){
        super();
        this.mana = 0;
        this.duration = 0;
    }

    public SpellCard(int id, String name, String description, SpellType spellType, int mana) {
        super(id, name, description);
        this.spellType = spellType;
        this.mana = mana;
        this.duration = 0;
    }
    public SpellType getSpellType(){
        return this.spellType;
    }

    public int getMana(){
        return this.mana;
    }

    public int getDuration(){
        return this.duration;
    }

    public void decreaseDuration(){
        this.duration--;
    }

}

