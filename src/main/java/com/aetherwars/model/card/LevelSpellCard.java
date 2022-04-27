package com.aetherwars.model.card;

public class LevelSpellCard extends SpellCard {
    private int level;
    public LevelSpellCard(int id, String name, String description, String IMAGE_PATH){
        super(id, name, description, IMAGE_PATH, SpellType.LVL, 0);
        this.level = (this.id == 401) ? 1 : -1;
    } 

    public LevelSpellCard(LevelSpellCard other){
        super(other.id, other.name, other.description, other.IMAGE_PATH, SpellType.LVL, other.mana);
        this.level = other.level;
    } 
    
    public int getLevelup(){
        return this.level;
    }

}
