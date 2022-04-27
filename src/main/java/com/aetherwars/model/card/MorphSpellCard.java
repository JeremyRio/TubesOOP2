package com.aetherwars.model.card;
import com.aetherwars.model.game.CardDealer;

public class MorphSpellCard extends SpellCard {
    private int targetid;
    private CharacterCard targetedCharacter;

    public MorphSpellCard(int id, String name, String description, String IMAGE_PATH, int mana, int targetid, Card targetedCharacter) {
        super(id, name, description, IMAGE_PATH, SpellType.MORPH, mana);
        this.targetid = targetid;
        this.targetedCharacter = new CharacterCard((CharacterCard)targetedCharacter);
    }

    public MorphSpellCard(MorphSpellCard other) {
        super(other.id, other.name, other.description, other.IMAGE_PATH, SpellType.MORPH, other.mana);
        this.targetid = other.targetid;
        this.targetedCharacter = new CharacterCard((CharacterCard) other.getMorphedCharacter());
        this.duration = other.getDuration();
    }

    public int getTargetID() {
        return targetid;
    }

    public CharacterCard getMorphedCharacter() {
        return this.targetedCharacter;
    }

    public void setMana(int mana) {
        super.setMana(mana);
    }
}
