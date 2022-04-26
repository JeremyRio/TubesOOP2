package com.aetherwars.model.card;

public class LevelSpellCard extends SpellCard {
    public LevelSpellCard(int id, String name, String description, String IMAGE_PATH){
        super(id, name, description, IMAGE_PATH, SpellType.LVL, 0);
     }    
}
