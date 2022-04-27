package com.aetherwars.model.card;
import java.lang.Math;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class SummonedCard {
    private int exp;
    private int level;
    private float summonedHealth;
    private int bonusAttack;
    private float bonusHealth;
    private CharacterCard character;
    private List<SpellCard> activeSpells;
    private boolean hasAttacked;
    private boolean hasSummoned;
    private boolean isEmpty;
    private boolean swapActivated; 

    public SummonedCard(){
        this.isEmpty = true;
    }

    public SummonedCard(CharacterCard character) {
        this.character = character;
        this.exp = 0;
        this.level = 1;
        this.bonusAttack = 0;
        this.bonusHealth = 0.0f;
        this.activeSpells = new ArrayList<>();
        this.hasSummoned = true;
        this.hasAttacked = false;
        this.summonedHealth = character.getHealth();
        this.isEmpty = false;
        this.swapActivated = false;
    }

    public void setHasAttacked(boolean hasAttacked){
        this.hasAttacked = hasAttacked;
    }

    public void setHasSummoned(boolean hasSummoned){
        this.hasSummoned = hasSummoned;
    }

    public void setEmpty(boolean isEmpty){
        this.isEmpty = isEmpty;
    }

    public boolean hasSummoned(){
        return this.hasSummoned;
    }

    public void setSummonedHealth(float health){
        this.summonedHealth = health;
    }

    public float getSummonedHealth(){
        return this.summonedHealth;
    }

    public CharacterCard getCharacterCard(){
        return this.character;
    }

    public boolean hasAttacked(){
        return this.hasAttacked;
    }

    public boolean isEmpty(){
        return this.isEmpty;
    }

    public int getTotalAttack(){
        int result = this.character.getAttack() + this.bonusAttack;
        return result = (result < 0) ? 0 : result;
    }  

    public float getTotalHealth(){
        return this.summonedHealth + this.bonusHealth;
    }

    public int getBonusAttack(){
        return this.bonusAttack;
    }

    public void setBonusAttack(int bonusAttack) {
        this.bonusAttack = bonusAttack;
    }

    public void setBonusHealth(float bonusHealth) {
        this.bonusHealth = bonusHealth;
    }

    public boolean isDead(){
        return getTotalHealth() <= 0.0f;
    }

    public float getBonusHealth(){
        return this.bonusHealth;
    }

    public void addExp(int exp){
        this.exp += exp;
        this.levelUp();
    }

    public void setAttack(int attack){
        this.character.setAttack(attack);
    }

    public List<SpellCard> getActiveSpells(){
        return this.activeSpells;
    }

    public float getAttackModifier(SummonedCard target){
        float modifier = 1;
        CharacterType targetType = target.getCharacterCard().getCharacterType();
        switch(this.character.getCharacterType()){
            case OVERWORLD:
                switch (targetType){
                    case NETHER:
                        modifier = 0.5f;
                        break;
                    case END:
                        modifier = 2;
                        break;
                    default:
                        break;
                }
                break;
            case NETHER:
                switch (targetType){
                    case OVERWORLD:
                        modifier = 2;
                        break;
                    case END:
                        modifier = 0.5f;
                        break;
                    default:
                        break;
                }
                break;
            default:
                switch (targetType){
                    case OVERWORLD:
                        modifier = 0.5f;
                        break;
                    case NETHER:
                        modifier = 2;
                        break;
                    default:
                        break;
                }
        }
        return modifier;
    }


    public void Attack(SummonedCard targetCard){
        float newSourceBonusHealth = this.bonusHealth;
        float newSourceHealth = this.getSummonedHealth();
        float newTargetBonusHealth = targetCard.getBonusHealth();
        float newTargetHealth = targetCard.getSummonedHealth();

        float modifierSource = this.getAttackModifier(targetCard);
        float modifierTarget = targetCard.getAttackModifier(this);

        float sourceAttack = modifierSource * (float) this.getTotalAttack();
        out.println(sourceAttack);
        if(sourceAttack > newTargetBonusHealth){
            sourceAttack -= newTargetBonusHealth;
            newTargetBonusHealth = 0.0f;
            newTargetHealth -= sourceAttack;
        }else{
            newTargetBonusHealth -= sourceAttack;
        }

        targetCard.setSummonedHealth(newTargetHealth);
        targetCard.setBonusHealth(newTargetBonusHealth);


        float targetAttack = modifierTarget * (float) targetCard.getTotalAttack();
        out.println(targetAttack);
        if(targetAttack > newSourceBonusHealth){
            targetAttack -= newSourceBonusHealth;
            newSourceBonusHealth = 0.0f;
            newSourceHealth -= targetAttack;
        }else{
            newSourceBonusHealth -= targetAttack;
        }

        this.setSummonedHealth(newSourceHealth);
        this.bonusHealth = newSourceBonusHealth;
    }

    public void levelUp(){
        if(this.level < 10) {
            if (this.exp >= getExpToNextLevel()) {
                this.exp -= getExpToNextLevel();
                this.level++;
                this.character.setAttack(this.character.getAttack() + this.character.getAttackUp());
                this.character.setHealth(this.character.getHealth() + (float) this.character.getHealthUp());
                this.setSummonedHealth(this.character.getHealth());
            }
        }
    }

    public int getExpToNextLevel(){
        return (this.level * 2) - 1;
    }

    public int getExp() {
        return this.exp;
    }

    public int getLevel() {
        return this.level;
    }

    public String getImagePath(){
        return this.character.getImagePath();
    }

    // Spells
    public void addActiveSpell(SpellCard other) {
        if (other.getDuration() != 0 && !swapActivated){
            if (other.getSpellType() == SpellType.PTN) this.activeSpells.add(new PotionSpellCard((PotionSpellCard)other));
            else if  (other.getSpellType() == SpellType.LVL) this.activeSpells.add(new LevelSpellCard((LevelSpellCard) other));
            else if (other.getSpellType() == SpellType.MORPH) this.activeSpells.add(new MorphSpellCard((MorphSpellCard) other));
            else if (other.getSpellType() == SpellType.SWAP) this.activeSpells.add(new SwapSpellCard((SwapSpellCard) other));
        }
        if (other instanceof PotionSpellCard) {
            PotionSpellCard temp = (PotionSpellCard) other;
            System.out.println("duration potion: " + other.getDuration());
            this.bonusAttack += temp.getAttack();
            this.bonusHealth += temp.getHP();
        }
        else if (other instanceof LevelSpellCard){
            LevelSpellCard temp = (LevelSpellCard) other;
            if ((this.level + temp.getLevelup() > 0) && (this.level + temp.getLevelup() <= 10)) {
                temp.setMana((int) Math.ceil((float)getLevel()/2));
                leveling(temp.getLevelup());
            }
        }
        else if (other instanceof MorphSpellCard){
            MorphSpellCard temp = (MorphSpellCard) other;
            this.character = temp.getMorphedCharacter();
            this.exp = 0;
            this.level = 1;
            this.bonusAttack = 0;
            this.bonusHealth = 0.0f;
            this.activeSpells = new ArrayList<>();
            this.hasSummoned = true;
            this.hasAttacked = false;
            this.summonedHealth = character.getHealth();
            this.swapActivated = false;
        }
        else if (other instanceof SwapSpellCard) {
            if (swapActivated) {
                this.addDuration(other);
            }
            else {
                this.swapActivated = true;
                int tempAttack = this.character.getAttack();
                int tempBonusAttack = this.bonusAttack;
                this.character.setAttack( (int) this.character.getHealth());
                this.setSummonedHealth(tempAttack);
                this.bonusAttack = (int) this.bonusHealth;
                this.bonusHealth = tempBonusAttack;
            }
        }
    }

    // Mengurangi durasi pada list activeSpell
    public void updateDuration() {
        if (this.activeSpells == null) {
            return;
        }
        else if (!this.activeSpells.isEmpty()){
            for (SpellCard activeSpell: activeSpells) {
                activeSpell.decreaseDuration();
                // System.out.println(activeSpell.getName() +" duration: " +  activeSpell.getDuration());
                if (activeSpell.getDuration() == (-1)) {
                    revertSpell(activeSpell);
                }
            }
            activeSpells.removeIf(a -> a.getDuration() == (-1));
        }
    }

    // Membalikkan efek dari spell
    public void revertSpell(SpellCard other) {
        if (other instanceof PotionSpellCard) {
            PotionSpellCard temp = (PotionSpellCard) other;
            this.bonusHealth -=  temp.getHP();
            this.bonusAttack -= temp.getAttack();
        }
        else if (other instanceof SwapSpellCard) {
            int tempAttack = this.character.getAttack();
            int tempBonusAttack = this.bonusAttack;
            this.character.setAttack( (int) this.character.getHealth());
            this.setSummonedHealth(tempAttack);
            this.bonusAttack = (int) this.bonusHealth;
            this.bonusHealth = tempBonusAttack;
        }
    }

    // Menambahkan durasi pada spell card swap
    public void addDuration(SpellCard other) {
        for (SpellCard activeSpell: activeSpells) {
            if (activeSpell.getSpellType() == SpellType.SWAP) {
                activeSpell.setDuration(activeSpell.getDuration() + other.getDuration());
                break;
            }
        }
    }

    // Menghapuskan active spells bisa digunakan kalau sudah mati karakter
    public void clearActiveSpells() {
        this.activeSpells.clear();
    }

    public void leveling(int up) {
        if (up == 1) {
            this.level++;
            this.character.setAttack(this.character.getAttack() + this.character.getAttackUp());
            this.character.setHealth(this.character.getHealth() + (float) this.character.getHealthUp());
        }
        else {
            this.level--;
            this.character.setAttack(this.character.getAttack() - this.character.getAttackUp());
            this.character.setHealth(this.character.getHealth() - (float) this.character.getHealthUp());
        }
        this.setSummonedHealth(this.character.getHealth());
    }

    @Override
    public String toString() {
        return this.activeSpells.stream().map(c -> c.getName()).collect(Collectors.joining(","));
    }
}
