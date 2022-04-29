package com.test;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.CharacterCard;
import com.aetherwars.model.card.SummonedCard;
import com.aetherwars.model.game.CardDealer;
import com.aetherwars.model.game.Player;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    private List<Card> deck;
    private Player testPlayer;

    @Before
    public void start() {
        CardDealer allCards = new CardDealer();
        deck = allCards.getPlayer1Deck();
        testPlayer = new Player("Player", deck, deck.size());
    }

    @Test
    public void MethodTest() {
        assertEquals(1, testPlayer.getHealth());
        assertEquals(100, testPlayer.getDeckSize());
        assertEquals(deck.size(), testPlayer.getInitialDeckSize());
        assertEquals(100, testPlayer.getInitialMana());

        testPlayer.heal(10);
        testPlayer.takeDamage(3);
        testPlayer.decreaseMana(99);
        List<Card> drawn = testPlayer.draw();
        testPlayer.addHandCard(drawn.remove(0));
        testPlayer.addDeckCard(drawn);

        assertEquals(1, testPlayer.getMana());
        assertEquals(8, testPlayer.getHealth());
        assertEquals(99, testPlayer.getDeckSize());
        assertEquals(1, testPlayer.getHandCardList().size());

        testPlayer.resetMana();
        assertEquals(100, testPlayer.getMana());
    }
}
