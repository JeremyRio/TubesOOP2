package com.aetherwars.model.card;

public class CharacterCard extends Card {
  private int attack;
  private float health;
  private int attackUp;
  private int healthUp;
  private CharacterType characterType;


  public CharacterCard(CharacterCard otherCard){
    super(otherCard.getID(), otherCard.getName(), otherCard.getDescription(), CardType.CHARACTER, otherCard.getImagePath());
    this.attack = otherCard.getAttack();
    this.health = otherCard.getHealth();
    this.attackUp = otherCard.getAttackUp();
    this.healthUp = otherCard.getHealthUp();
    this.characterType = otherCard.getCharacterType();
  }

  public CharacterCard() {
    super();
    this.characterType = CharacterType.OVERWORLD;
  }

  public CharacterCard(int id, String name, String description, String IMAGE_PATH, CharacterType element, int attack, int attackUp, float health, int healthUp) {
    super(id, name, description, CardType.CHARACTER, IMAGE_PATH);
    this.characterType = element;
    this.attack = attack;
    this.attackUp = attackUp;
    this.health = health;
    this.healthUp = healthUp;
  }

  public void setAttack(int attack) { this.attack = attack; }
  
  public void setHealth(float health) { this.health = health; }

  public CharacterType getCharacterType(){
    return this.characterType;
  }

  public int getAttack() { return this.attack; }

  public float getHealth() { return this.health; }

  public int getAttackUp() { return this.attackUp; }

  public int getHealthUp() { return this.healthUp; }

}
