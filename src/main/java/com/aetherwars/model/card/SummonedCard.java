package com.aetherwars.model.card;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

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
        this.isEmpty = true;
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
        return this.character.getAttack() + this.bonusAttack;
    }

    public float getTotalHealth(){
        return this.summonedHealth + this.bonusHealth;
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

}
