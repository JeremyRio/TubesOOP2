package com.aetherwars.model;

public class PotionSpellCard extends SpellCard {
    private int hp;
    private int attack;

    public PotionSpellCard(int id, String name, String description, SpellType type, int mana, int hp, int attack) {
        super(id, name, description, type, mana);
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
