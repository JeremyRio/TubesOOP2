package com.aetherwars.model.card;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SummonedCard {
    private int exp;
    private int level;
    private int bonusAttack;
    private int bonusHealth;
    private CharacterCard character;
    private List<SpellCard> activeSpells;
    private boolean hasAttacked;
    private boolean hasSummoned;

    public SummonedCard(CharacterCard character) {
        this.character = character;
        this.exp = 0;
        this.level = 1;
        this.bonusAttack = 0;
        this.bonusHealth = 0;
        this.activeSpells = new ArrayList<>();
    }

    public void setHasAttacked(boolean hasAttacked){
        this.hasAttacked = hasAttacked;
    }

    public void setHasSummoned(boolean hasSummoned){
        this.hasSummoned = hasSummoned;
    }

    public int getTotalAttack(){
        return character.getAttack() + bonusAttack;
    }

    public int getTotalHealth(){
        return character.getHealth() + bonusHealth;
    }

    public void setBonusAttack(int bonusAttack) {
        this.bonusAttack = bonusAttack;
    }

    public void setBonusHealth(int bonusHealth) {
        this.bonusHealth = bonusHealth;
    }

    public void checkLevelUp(){
        boolean islevelUp = true;
        while(islevelUp) {
            if (this.exp >= this.getExpToNextLevel()) {
                this.levelUp();
            }
            else{
                islevelUp = false;
            }
        }
    }

    public void levelUp(){
        this.exp -= getExpToNextLevel();
        this.level++;
        this.character.setAttack(this.character.getAttackUp() + this.character.getAttack());
        this.character.setHealth(this.character.getHealthUp() + this.character.getHealth());
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
}
