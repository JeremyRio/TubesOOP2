package com.aetherwars.model;

public class MorphSpellCard extends SpellCard {
    private int targetid;

    public MorphSpellCard(int id, String name, String description, SpellType type, int mana, int targetid) {
        super(id, name, description, type, mana);
        this.targetid = targetid;
    }

    public int getTargetID() {
        return targetid;
    }
}
