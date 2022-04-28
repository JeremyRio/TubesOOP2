package com.aetherwars.model.card;

public class SpellCard extends Card {
    protected SpellType spellType;
    protected int duration;

    // Default Constructor
    public SpellCard(){
        super();
        this.mana = 0;
        this.duration = 0;
    }

    // cctor
    public SpellCard(SpellCard otherCard) {
        super(otherCard.getID(), otherCard.getName(), otherCard.getMana(), otherCard.getDescription(), CardType.SPELL, otherCard.getImagePath());
        this.spellType = otherCard.getSpellType();
        this.mana = otherCard.getMana();
        this.duration = otherCard.getDuration();
    }
    
    // User-defined constructor, duration 0
    public SpellCard(int id, String name, String description, String IMAGE_PATH, SpellType spellType, int mana) {
        super(id, name, mana, description, CardType.SPELL, IMAGE_PATH);
        this.spellType = spellType;
        this.mana = mana;
        this.duration = 0;
    }

    // User-defined constructor
    public SpellCard(int id, String name, String description, String IMAGE_PATH, SpellType spellType, int mana, int duration) {
        super(id, name, mana, description, CardType.SPELL, IMAGE_PATH);
        this.spellType = spellType;
        this.mana = mana;
        this.duration = duration;
    }

    // GETTER
    public SpellType getSpellType(){
        return this.spellType;
    }

    public int getDuration(){
        return this.duration;
    }

    // Reduce duration by 1
    public void decreaseDuration(){
        if(duration > 0) {
            this.duration--;
        }
    }

    // SETTER
    public void setDuration(int duration){
        this.duration = duration;
    }

    public void setMana(int mana) {
        super.setMana(mana);
    }
}