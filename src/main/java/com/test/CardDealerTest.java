package com.test;

import org.junit.Before;
import org.junit.Test;
import com.aetherwars.model.card.*;
import com.aetherwars.model.game.CardDealer;

import java.util.logging.Level;

import static org.junit.Assert.*;
public class CardDealerTest {
    CardDealer testAllCards = new CardDealer();

    @Test
    public void testLoadCardsObject() {
        for (int i = 1; i <= 18; i++) {
            assertTrue(testAllCards.getCardFromMap(i) instanceof CharacterCard);
            assertTrue(testAllCards.getCardFromMap(i) instanceof Card);
        }
        for (int i = 401; i <= 402; i++ ) {
            assertTrue(testAllCards.getCardFromMap(i) instanceof LevelSpellCard);
            assertTrue(testAllCards.getCardFromMap(i) instanceof SpellCard);
            assertTrue(testAllCards.getCardFromMap(i) instanceof Card);
        }
        for (int i = 301; i <= 306; i++ ) {
            assertTrue(testAllCards.getCardFromMap(i) instanceof MorphSpellCard);
        }
        for (int i = 101; i <= 118; i++ ) {
            assertTrue(testAllCards.getCardFromMap(i) instanceof PotionSpellCard);
        }
        for (int i = 201; i <= 201; i++ ) {
            assertTrue(testAllCards.getCardFromMap(i) instanceof SwapSpellCard);
        }
    }
    @Test
    public void testReturnCharacterCorrect() {
        Card temp = testAllCards.getCardFromMap(1);
        CharacterCard thisCharacter = (CharacterCard) temp;
        assertEquals(1, temp.getID());
        assertEquals("Creeper", temp.getName());
        assertEquals("A creeper is a common hostile mob that silently approaches players and explodes.", temp.getDescription());
        assertEquals(1, thisCharacter.getAttackUp());
        assertEquals(1, thisCharacter.getHealthUp());
    }
    @Test
    public void testReturnSpellCorrect() {
        Card temp = testAllCards.getCardFromMap(104);
        PotionSpellCard thisPotion = (PotionSpellCard) temp;
        assertEquals(104, temp.getID());
        assertEquals("Divine Wind", temp.getName());
        assertEquals("Sacrifice for The Descendants of The Sun", temp.getDescription());
        assertEquals(0, thisPotion.getAttack());
        assertEquals(-4, thisPotion.getHP(), 0);
        assertEquals(2, thisPotion.getMana());
        assertEquals(0, thisPotion.getDuration());
    }
}