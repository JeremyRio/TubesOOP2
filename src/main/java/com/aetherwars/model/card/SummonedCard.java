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
    private int summonedAttack;
    private CharacterCard character;
    private List<SpellCard> activeSpells;
    private boolean hasAttacked;
    private boolean hasSummoned;
    private boolean isEmpty;
    private boolean isSwap;

    public SummonedCard(){
        this.isEmpty = true;
    }

    public SummonedCard(CharacterCard character) {
        this.character = character;
        this.exp = 0;
        this.level = 1;
        this.activeSpells = new ArrayList<>();
        this.hasSummoned = true;
        this.hasAttacked = false;
        this.summonedHealth = character.getHealth();
        this.isEmpty = false;
        this.isSwap = false;
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

    public int getSummonedAttack(){
        return this.summonedAttack;
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
        int result = this.character.getAttack() + this.getBonusAttack();
        return result = Math.max(result, 0);
    }  

    public float getTotalHealth(){
        out.println("=================================");
        out.println("SUMMONED HEALTH: " + summonedHealth);
        out.println("BONUS HEALTH: " + getBonusHealth());
        out.println("=================================");
        return this.summonedHealth + this.getBonusHealth();
    }

    public int getBonusAttack(){
        return activeSpells.stream().filter(PotionSpellCard.class::isInstance).map(PotionSpellCard.class::cast).mapToInt(PotionSpellCard::getAttack).sum();
    }


    public boolean isDead(){
        return getTotalHealth() <= 0.0f;
    }

    public float getBonusHealth(){
        return (float) activeSpells.stream().filter(PotionSpellCard.class::isInstance).map(PotionSpellCard.class::cast).mapToDouble(PotionSpellCard::getHP).sum();
    }

    public void addExp(int exp){
        this.exp += exp;
        this.levelUp();
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
                }
        }
        return modifier;
    }

    public float takeAttack(float attack){
       List<PotionSpellCard> potionSPList = activeSpells.stream().filter(PotionSpellCard.class::isInstance).map(PotionSpellCard.class::cast).collect(Collectors.toList());
       out.print(potionSPList);
        for (PotionSpellCard potionSP: potionSPList){
            if(potionSP.getHP() > 0.0f){
                if(attack > potionSP.getHP()){
                    attack -= potionSP.getHP();
                    potionSP.setHP(0.0f);
                }else{
                    potionSP.setHP(potionSP.getHP() - attack);
                    attack = 0;
                    return attack;
                }
            }
        }
        return attack;
    }

    public void Attack(SummonedCard targetCard){
        float newSourceHealth = this.getSummonedHealth();
        float newTargetHealth = targetCard.getSummonedHealth();

        float modifierSource = this.getAttackModifier(targetCard);
        float modifierTarget = targetCard.getAttackModifier(this);

        out.println("SOURCE: ");
        this.getTotalHealth();
        out.println("TARGET: ");
        targetCard.getTotalHealth();

        float sourceAttack = modifierSource * (float) this.getTotalAttack();
        out.println("======================================");
        out.println("SOURCE ATTACK BEFORE: " + sourceAttack);
        sourceAttack = targetCard.takeAttack(sourceAttack);
        out.println("SOURCE ATTACK AFTER: " + sourceAttack);
        out.println("NEW TARGET HEALTH: " + newTargetHealth);
        out.println("=======================================");
        if(newTargetHealth > sourceAttack){
            newTargetHealth -= sourceAttack;
        }else{
            newTargetHealth = 0.0f;
        }
        targetCard.setSummonedHealth(newTargetHealth);

        float targetAttack = modifierTarget * (float) targetCard.getTotalAttack();
        out.println("=======================================");
        out.println("TARGET ATTACK BEFORE: " + targetAttack);
        targetAttack = this.takeAttack(targetAttack);
        out.println("TARGET ATTACK AFTER: " + targetAttack);
        out.println("SOURCE TARGET HEALTH: " + newSourceHealth);
        out.println("=========================================");
        if(newSourceHealth > targetAttack){
            newSourceHealth -= targetAttack;
        }else{
            newSourceHealth = 0.0f;
        }
        this.setSummonedHealth(newSourceHealth);
        out.println("SOURCE: ");
        this.getTotalHealth();
        out.println("TARGET: ");
        targetCard.getTotalHealth();
    }

    public void levelUp(){
        if(this.level < 10) {
            while (this.exp >= getExpToNextLevel()) {
                this.exp -= getExpToNextLevel();
                this.level++;
                this.character.setHealth((float) this.character.getHealth() + this.character.getHealthUp());
                this.summonedHealth = this.character.getHealth();
                this.character.setAttack(this.character.getAttack() + this.character.getAttackUp());
                this.summonedAttack = this.character.getAttackUp();
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
    public void addActiveSpell(SpellCard spellCard) {
        spellCard.setDuration(spellCard.getDuration() * 2);
        out.println("DURATION: " + spellCard.getDuration());
        if(spellCard.getDuration() == 0){
            out.println("PASS MORPH");
            spellCard.setDuration(-1);
        }
        out.println("DURATION: " + spellCard.getDuration());
        switch (spellCard.getSpellType()){
            case PTN:
                PotionSpellCard potionSC = new PotionSpellCard((PotionSpellCard) spellCard);
                activeSpells.add(potionSC);
                break;
            case LVL:
                LevelSpellCard levelSC = new LevelSpellCard((LevelSpellCard) spellCard);
                levelSC.setMana((int) Math.ceil((float) this.getLevel() / 2));
                leveling(levelSC.getLevelup());
                activeSpells.add(levelSC);
                break;
            case MORPH:
                out.println("WAK WAO");
                MorphSpellCard morphSC = new MorphSpellCard((MorphSpellCard) spellCard);
                out.println("LIHAT NIH");
                this.character = new  CharacterCard(morphSC.getMorphedCharacter());
                this.exp = 0;
                this.level = 1;
                this.activeSpells = new ArrayList<>();
                this.hasSummoned = false;
                this.hasAttacked = false;
                this.summonedHealth = character.getHealth();
                this.isSwap = false;
                activeSpells.add(morphSC);
                break;
            case SWAP:
                SwapSpellCard swapSC = new SwapSpellCard((SwapSpellCard) spellCard);

                if(!this.isSwap) {
                    this.isSwap = true;
                    this.swapFunction();
                }
                activeSpells.add(swapSC);
                break;
            default:
        }
    }

    // Mengurangi durasi pada list activeSpell
    public void updateDuration() {
        if(!this.isEmpty && activeSpells.size() > 0) {
            activeSpells.forEach(SpellCard::decreaseDuration);
            List<SpellCard> usedSpells = activeSpells.stream().filter(sp -> sp.getDuration() == 0).collect(Collectors.toList());
            usedSpells.forEach(sp -> {
                activeSpells.remove(sp);
            });
            List<SwapSpellCard> swapSCList = activeSpells.stream().filter(SwapSpellCard.class::isInstance).map(SwapSpellCard.class::cast).collect(Collectors.toList());
            if (swapSCList.isEmpty() && this.isSwap) {
                this.isSwap = false;
                this.swapFunction();
            }
        }
    }

    public void swapFunction(){
        int temp = (int) this.summonedHealth;
        this.summonedHealth = (float) this.summonedAttack;
        this.summonedAttack = temp;
        activeSpells.stream().filter(PotionSpellCard.class::isInstance).map(PotionSpellCard.class::cast).forEach(potionSP -> {
            int tempHP = (int) potionSP.getHP();
            potionSP.setHP((float) potionSP.getAttack());
            potionSP.setAttack(tempHP);
        });
    }

    // Menghapuskan active spells bisa digunakan kalau sudah mati karakter
    public void clearActiveSpells() {
        this.activeSpells.clear();
    }

    public void leveling(int up) {
        if (up == 1) {
            if(this.level == 10){
                this.setSummonedHealth(this.character.getHealth());
            }else{
                this.exp += this.getExpToNextLevel();
                this.levelUp();
            }
        }
        else {
            this.level--;
            this.character.setAttack(this.character.getAttack() - this.character.getAttackUp());
            this.summonedAttack = this.character.getAttack();
            this.character.setHealth(this.character.getHealth() - this.character.getHealthUp());
            if(this.summonedHealth > this.character.getHealth()){
                this.summonedHealth = this.character.getHealth();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        this.activeSpells.stream().forEach(sc -> {
            message.append(sc.getName()).append(" (").append((sc.getDuration() == -1 ? "PERM" : (int) Math.ceil((float) sc.getDuration() / 2))).append(")").append("\n");
        });
        return message.toString();
    }
}
