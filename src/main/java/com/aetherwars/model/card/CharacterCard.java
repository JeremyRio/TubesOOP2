package com.aetherwars.model.card;

public class CharacterCard extends Card {
  private int attack;
  private int health;
  private int attackUp;
  private int hpUp;
  private int bonusAttack;
  private int attackUp;
  private int healthUp;
  private int bonusHealth;
  private CharacterType characterType;

  public CharacterCard() {
    super();
    this.characterType = CharacterType.OVERWORLD;
  }

  public CharacterCard(int id, String name, String description, String IMAGE_PATH, CharacterType element, int attack, int attackUp, int health, int healthUp) {
    super(id, name, description, CardType.CHARACTER, IMAGE_PATH);
    this.characterType = element;
    this.attack = attack;
    this.attackUp = attackUp;
    this.health = health;
    this.healthUp = healthUp;
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

  public void setAttack(int attack) { this.attack = attack; }
  
  public void setHealth(int health) { this.health = health; }

  public void setBonusHealth(int bonusHealth) {
    this.bonusHealth = bonusHealth;
  }

  public CharacterType getCharacterType(){
    return this.characterType;
  }

  public int getAttack() { return this.attack; }

  public int getHealth() { return this.health; }

  public int getAttackUp() { return this.attackUp; }

  public int getHpUp() { return this.hpUp; }

}
