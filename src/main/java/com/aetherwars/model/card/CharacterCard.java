package com.aetherwars.model.card;

public class CharacterCard extends Card {
  private int attack;
  private int health;
  private int bonusAttack;
  private int bonusHealth;
  private CharacterType characterType;

  public CharacterCard() {
    super();
    this.characterType = CharacterType.OVERWORLD;
  }

  public CharacterCard(int id, String name, String description, CharacterType element) {
    super(id, name, description, CardType.CHARACTER);
    this.characterType = element;
  }

  public int getTotalAttack(){
    return attack + bonusAttack;
  }

  public int getTotalHealth(){
    return health + bonusHealth;
  }

  public void setBonusAttack(int bonusAttack) {
    this.bonusAttack = bonusAttack;
  }

  public void setBonusHealth(int bonusHealth) {
    this.bonusHealth = bonusHealth;
  }

  public CharacterType getCharacterType(){
    return this.characterType;
  }


}
