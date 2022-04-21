package com.aetherwars.model.card;

public class PotionSpellCard extends SpellCard {
    private int hp;
    private int attack;

    public PotionSpellCard(int id, String name, String description, String IMAGE_PATH, int mana, int duration, int hp, int attack) {
        super(id, name, description, IMAGE_PATH, SpellType.PTN, duration);
        this.hp = hp;
        this.attack = attack;
    }

    public int getHP(){
        return hp;
    }

    public int getAttack(){
        return attack;
    }
}
