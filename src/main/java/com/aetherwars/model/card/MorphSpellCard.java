package com.aetherwars.model.card;

public class MorphSpellCard extends SpellCard {
    private int targetid;

    public MorphSpellCard(int id, String name, String description, String IMAGE_PATH, int mana, int targetid) {
        super(id, name, description, IMAGE_PATH, SpellType.MORPH, mana);
        this.targetid = targetid;
    }

    public int getTargetID() {
        return targetid;
    }
}
