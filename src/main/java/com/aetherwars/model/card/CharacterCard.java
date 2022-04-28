package com.aetherwars.model.card;

public class CharacterCard extends Card {
  private int attack;
  private float health;
  private int attackUp;
  private int healthUp;
  private CharacterType characterType;


  // cctor
  public CharacterCard(CharacterCard otherCard){
    super(otherCard.getID(), otherCard.getName(), otherCard.getMana(), otherCard.getDescription(), CardType.CHARACTER, otherCard.getImagePath());
    this.attack = otherCard.getAttack();
    this.health = otherCard.getHealth();
    this.attackUp = otherCard.getAttackUp();
    this.healthUp = otherCard.getHealthUp();
    this.characterType = otherCard.getCharacterType();
  }

  // Default Constructor
  public CharacterCard() {
    super();
    this.characterType = CharacterType.OVERWORLD;
  }

  // User-defined constructor
  public CharacterCard(int id, String name, int mana, String description, String IMAGE_PATH, CharacterType element, int attack, int attackUp, float health, int healthUp) {
    super(id, name, mana, description, CardType.CHARACTER, IMAGE_PATH);
    this.characterType = element;
    this.attack = attack;
    this.attackUp = attackUp;
    this.health = health;
    this.healthUp = healthUp;
  }

  // SETTER
  public void setAttack(int attack) { this.attack = attack; }
  
  public void setHealth(float health) { this.health = health; }

  // GETTER
  public CharacterType getCharacterType(){
    return this.characterType;
  }

  public int getAttack() { return this.attack; }

  public float getHealth() { return this.health; }

  public int getAttackUp() { return this.attackUp; }

  public int getHealthUp() { return this.healthUp; }

}
