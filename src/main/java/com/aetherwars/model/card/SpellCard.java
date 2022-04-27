package com.aetherwars.model.card;

public class SpellCard extends Card {
    protected SpellType spellType;
    protected int duration;

    public SpellCard(){
        super();
        this.mana = 0;
        this.duration = 0;
    }

    public SpellCard(int id, String name, String description, String IMAGE_PATH, SpellType spellType, int mana) {
        super(id, name, mana, description, CardType.SPELL, IMAGE_PATH);
        this.spellType = spellType;
        this.mana = mana;
        this.duration = 0;
    }

    public SpellCard(int id, String name, String description, String IMAGE_PATH, SpellType spellType, int mana, int duration) {
        super(id, name, mana, description, CardType.SPELL, IMAGE_PATH);
        this.spellType = spellType;
        this.mana = mana;
        this.duration = duration;
    }

    public SpellType getSpellType(){
        return this.spellType;
    }

    public int getDuration(){
        return this.duration;
    }

    public void decreaseDuration(){
        this.duration--;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    public void setMana(int mana) {
        super.setMana(mana);
    }
}