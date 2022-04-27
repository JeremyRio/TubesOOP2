package com.aetherwars.model.card;

public class SwapSpellCard extends SpellCard{
   public SwapSpellCard(int id, String name, String description, String IMAGE_PATH, int mana, int duration){
      super(id, name, description, IMAGE_PATH, SpellType.SWAP, mana, duration);
   }

   public SwapSpellCard(SwapSpellCard other){
      super(other.id, other.name, other.description, other.IMAGE_PATH, SpellType.SWAP, other.mana, other.duration);
   }
}
