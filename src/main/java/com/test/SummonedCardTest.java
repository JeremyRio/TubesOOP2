package com.test;


import org.junit.Before;
import org.junit.Test;
import com.aetherwars.model.card.*;
import com.aetherwars.model.game.CardDealer;

import static org.junit.Assert.*;

public class SummonedCardTest {
    private CardDealer allCards;
    private SummonedCard summonedCharacter;
    private SummonedCard summonedCharacterTarget;
    @Before
    public void start() {
        // test on 1 character attack	health	mana	attackup	healthup 10	2	4	1	1
        allCards = new CardDealer();
        summonedCharacter = new SummonedCard ((CharacterCard) allCards.getCardFromMap(1));
    }
    @Test
    public void testPotion() {
        // snmptn attack	hp	mana	duration -2	-2	2	1
        PotionSpellCard potion = (PotionSpellCard)allCards.getCardFromMap(117);
        SpellCard potionSpell = (SpellCard) potion;
        summonedCharacter.addActiveSpell(potionSpell);
        assertEquals(8, summonedCharacter.getTotalAttack());
        assertEquals(0, summonedCharacter.getTotalHealth(), 0);
        assertEquals(true, summonedCharacter.isDead());
    }
    @Test
    public void testSwap() {
        // duration	mana 3 3
        SwapSpellCard swap = (SwapSpellCard)allCards.getCardFromMap(203);
        SpellCard swapSpell = (SpellCard) swap;
        summonedCharacter.addActiveSpell(swapSpell);
        assertEquals(2, summonedCharacter.getTotalAttack());
        assertEquals(10, summonedCharacter.getTotalHealth(), 0);
        for (int i=0; i < 7; i++) {
            summonedCharacter.updateDuration();
        }
        assertEquals(10, summonedCharacter.getTotalAttack());
        assertEquals(2, summonedCharacter.getTotalHealth(), 0);
    }
    @Test
    public void testLevelup() {
        // duration	mana 3 3
        LevelSpellCard level = (LevelSpellCard)allCards.getCardFromMap(401);
        SpellCard levelSpellup = (SpellCard) level;
        summonedCharacter.addActiveSpell(levelSpellup);
        assertEquals(2, summonedCharacter.getLevel());
        assertEquals(0, summonedCharacter.getExp());
        assertEquals(11, summonedCharacter.getTotalAttack());
        assertEquals(3, summonedCharacter.getTotalHealth(),0);
        for (int i = 0; i < 12; i++) {
            summonedCharacter.addActiveSpell(levelSpellup);
        }
        assertEquals(19, summonedCharacter.getTotalAttack());
        assertEquals(11, summonedCharacter.getTotalHealth(),0);
    }
    @Test
    public void testLevelDown() {
        testLevelup();
        LevelSpellCard leveldown = (LevelSpellCard)allCards.getCardFromMap(402);
        SpellCard levelSpelldown = (SpellCard) leveldown;
        summonedCharacter.addActiveSpell(levelSpelldown);
        assertEquals(18, summonedCharacter.getTotalAttack());
        assertEquals(10, summonedCharacter.getTotalHealth(), 0);
        for (int i = 0; i < 12; i++) {
            summonedCharacter.addActiveSpell(levelSpelldown);
        }
        assertEquals(10, summonedCharacter.getTotalAttack());
        assertEquals(2, summonedCharacter.getTotalHealth(), 0);
    }
    @Test
    public void testMorph() {
        //targetid	mana 18	5 malinkundang id:305
        // attack	health	mana	attackup	healthup 1	25	8	0	5
        MorphSpellCard morph = (MorphSpellCard)allCards.getCardFromMap(305);
        SpellCard morphSpell = (SpellCard) morph;
        summonedCharacter.addActiveSpell(morphSpell);
        assertEquals(1, summonedCharacter.getTotalAttack());
        assertEquals(25, summonedCharacter.getTotalHealth(), 0);
        assertEquals(1,summonedCharacter.getLevel());
        assertEquals(0,summonedCharacter.getExp());
    }
    @Test
    public void testAttack() {
        // the victim id: 10 attack	health	mana	attackup	healthup 4	6	3	1	2 (end)
        // attacker type overworld attack	health	mana	attackup	healthup 10	2	4	1	1
        summonedCharacterTarget = new SummonedCard ((CharacterCard) allCards.getCardFromMap(10));
        summonedCharacter.Attack(summonedCharacterTarget);
        assertEquals(0, summonedCharacterTarget.getTotalHealth(),0);
        assertEquals(0,summonedCharacter.getTotalHealth(),0);
    }
    @Test
    public void test3timesStackPotionthenSwap() {
        // 106	Grabkeun	Discount for food purchases in Bandung	card/image/spell/potion/Grabkeun.png	0	2	1	2
        PotionSpellCard potion = (PotionSpellCard)allCards.getCardFromMap(106);
        SpellCard potionSpell = (SpellCard) potion;
        summonedCharacter.addActiveSpell(potionSpell);// 10 4
        summonedCharacter.addActiveSpell(potionSpell);// 10 6
        summonedCharacter.addActiveSpell(potionSpell);// 10 8
        assertEquals(10, summonedCharacter.getTotalAttack());
        assertEquals(8, summonedCharacter.getTotalHealth(), 0);
        SwapSpellCard swap = (SwapSpellCard)allCards.getCardFromMap(203);
        SpellCard swapSpell = (SpellCard) swap;
        summonedCharacter.addActiveSpell(swapSpell);
        assertEquals(8, summonedCharacter.getTotalAttack());
        assertEquals(10, summonedCharacter.getTotalHealth(), 0); //8 10 6 0
        for (int i = 0; i < 4; i++) {
            summonedCharacter.updateDuration();
        }
        assertEquals(2, summonedCharacter.getTotalAttack());
        assertEquals(10, summonedCharacter.getTotalHealth(), 0);
        summonedCharacter.updateDuration();
        summonedCharacter.updateDuration();
        assertEquals(10, summonedCharacter.getTotalAttack());
        assertEquals(2, summonedCharacter.getTotalHealth(), 0);
    }
}