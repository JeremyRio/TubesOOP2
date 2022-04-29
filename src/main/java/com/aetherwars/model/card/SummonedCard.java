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

    // Default Constructor
    public SummonedCard(){
        this.isEmpty = true;
    }

    // Constructor
    public SummonedCard(CharacterCard character) {
        this.character = character;
        this.exp = 0;
        this.level = 1;
        this.activeSpells = new ArrayList<>();
        this.hasSummoned = true;
        this.hasAttacked = false;
        this.summonedHealth = character.getHealth();
        this.summonedAttack = character.getAttack();
        this.isEmpty = false;
        this.isSwap = false;
    }

    // SETTER
    public void setHasAttacked(boolean hasAttacked){
        this.hasAttacked = hasAttacked;
    }

    public void setHasSummoned(boolean hasSummoned){
        this.hasSummoned = hasSummoned;
    }

    public void setEmpty(boolean isEmpty){
        this.isEmpty = isEmpty;
    }

    public void setSummonedHealth(float health){
        this.summonedHealth = health;
    }

    // GETTER
    public boolean hasSummoned(){
        return this.hasSummoned;
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
        int result = this.summonedAttack + this.getBonusAttack();
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

    // Check if SummonedCard is dead
    public boolean isDead(){
        return getTotalHealth() <= 0.0f;
    }

    // Returns bonus health of SummonedCard
    public float getBonusHealth(){
        return (float) activeSpells.stream().filter(PotionSpellCard.class::isInstance).map(PotionSpellCard.class::cast).mapToDouble(PotionSpellCard::getHP).sum();
    }

    // Adds Exp to SummonedCard
    public void addExp(int exp){
        if(this.level < 10) {
            this.exp += exp;
            this.levelUp();
        }
    }

    // Returns list of active spells
    public List<SpellCard> getActiveSpells(){
        return this.activeSpells;
    }

    // Returns AttackModifier from SummonedCard to its target
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

    // Returns damage taken after reduction from potion
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

    // Procedure to call when SummonedCard attacks another
    public void Attack(SummonedCard targetCard){

        // Gets health and modifier information for both SummonedCard
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

    // Levels up SummonedCard and updates its stats
    public void levelUp(){
        while (this.exp >= getExpToNextLevel()) {
            this.exp -= getExpToNextLevel();
            this.level++;
            this.character.setHealth((float) this.character.getHealth() + this.character.getHealthUp());
            this.summonedHealth = this.character.getHealth();
            this.character.setAttack(this.character.getAttack() + this.character.getAttackUp());
            this.summonedAttack = this.character.getAttack();
        }
    }

    // GETTER
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

    // Adds active spells
    public void addActiveSpell(SpellCard spellCard) {
        switch (spellCard.getSpellType()) {
            case PTN:
                PotionSpellCard potionSC = new PotionSpellCard((PotionSpellCard) spellCard);
                potionSC.setDuration(spellCard.getDuration() * 2);
                activeSpells.add(0, potionSC);
                break;
            case LVL:
                LevelSpellCard levelSC = new LevelSpellCard((LevelSpellCard) spellCard);
                levelSC.setDuration(spellCard.getDuration() * 2);
                levelSC.setMana((int) Math.ceil((float) this.getLevel() / 2));
                leveling(levelSC.getLevelup());
                activeSpells.add(0, levelSC);
                break;
            case MORPH:
                MorphSpellCard morphSC = new MorphSpellCard((MorphSpellCard) spellCard);
                morphSC.setDuration(spellCard.getDuration() * 2);
                this.character = new CharacterCard(morphSC.getMorphedCharacter());
                this.exp = 0;
                this.level = 1;
                this.activeSpells = new ArrayList<>();
                this.hasSummoned = false;
                this.hasAttacked = false;
                this.summonedHealth = character.getHealth();
                this.summonedAttack = character.getAttack();
                this.isSwap = false;
                activeSpells.add(0, morphSC);
                break;
            case SWAP:
                SwapSpellCard swapSC = new SwapSpellCard((SwapSpellCard) spellCard);
                swapSC.setDuration(spellCard.getDuration() * 2);
                if (this.isSwap) {
                    SwapSpellCard swapInList = activeSpells.stream().filter(SwapSpellCard.class::isInstance).map(SwapSpellCard.class::cast).findFirst().get();
                    swapInList.setDuration(swapInList.getDuration() + swapSC.getDuration());
                }else{
                    swapFunction();
                    this.isSwap = true;
                    activeSpells.add(0, swapSC);
                }
                break;
            default:
        }
    }

    // Mengurangi durasi pada list activeSpell
    public void updateDuration() {
        if(!this.isEmpty && activeSpells.size() > 0) {
            List<SpellCard> usedSpells = activeSpells.stream().filter(sp -> sp.getDuration() == 1).collect(Collectors.toList());
            usedSpells.forEach(sp -> {
                activeSpells.remove(sp);
            });
            List<SwapSpellCard> swapSCList = activeSpells.stream().filter(SwapSpellCard.class::isInstance).map(SwapSpellCard.class::cast).collect(Collectors.toList());
            if (swapSCList.isEmpty() && this.isSwap) {
                this.isSwap = false;
                this.swapFunction();
            }
            activeSpells.forEach(SpellCard::decreaseDuration);
        }
    }

    // Swaps SummonedCard's attack and health
    public void swapFunction(){
        // System.out.println("Summoned health before: "+ this.summonedHealth);
        int temp = (int) this.summonedHealth;
        // System.out.println("Summoned attac before: " + this.summonedAttack);
        this.summonedHealth = (float) this.summonedAttack;
        this.summonedAttack = temp;
        temp = (int) this.getCharacterCard().getHealth();
        this.character.setHealth((float) this.character.getAttack());
        this.character.setAttack(temp);
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

    // Updates SummonedCard's level and stats
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
            if(this.level == 1){
                this.setSummonedHealth(this.character.getHealth());
            }else{
                this.level--;
                this.character.setAttack(this.character.getAttack() - this.character.getAttackUp());
                this.summonedAttack = this.character.getAttack();
                this.character.setHealth(this.character.getHealth() - this.character.getHealthUp());
                if(this.summonedHealth > this.character.getHealth()){
                    this.summonedHealth = this.character.getHealth();
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        this.activeSpells.forEach(sc -> {
            out.println("LOGIC DURATION: " + sc.getDuration());
            out.println("GUI DURATION: " + (int) Math.ceil((float) sc.getDuration() / 2));
            message.append(sc.getName()).append(" (").append((sc.getDuration() == 0 ? "PERM" : (int) Math.ceil((float) sc.getDuration() / 2))).append(")").append("\n");
        });
        return message.toString();
    }
}
