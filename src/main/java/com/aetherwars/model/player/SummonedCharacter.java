public class SummonedCharacter {
    private int exp;
    private int level;
    private CharacterCard character;
    private List<Pair<Integer,SpellCard>> spel;
    private int baseAtk;
    private int baseHp;
    private int bonusAtk;
    private int bonusHp;
    
    SummonedCharacter(CharacterCard c) {
        this.character = c;
        this.level = 1;
        this.exp = 0;
        this.baseAtk = this.character.baseAtk;
        this.baseHp = this.character.baseHp;
        this.bonusAtk = 0;
        this.bonusHp = 0;
        this.spel = new ArrayList<Pair<Integer,SpellCard>>();
    }

    SummonedCharacter(CharacterCard c, int level, int exp) {
        this.character = c;
        this.level = level;
        this.exp = exp;
        this.baseAtk = this.character.attack;
        this.baseHp = this.character.health;
        this.bonusAtk = 0;
        this.bonusHp = 0;
        this.spel = new ArrayList<Pair<Integer,SpellCard>>();
    }

    // getter
    public int getLevel() {
        return this.level;
    }
    public int getExp() {
        return this.exp;
    }

    // menambahkan spell ke dalam daftar spell aktif
    public void addSpell(SpellCard s) {
        this.spel.add(Pair.with(s.duration, s));
        // Efek
    }

    public void removeSpell() {
        for (int i=0; i<spel.size(); i++) {
            if (spel.get(i).getValue0() == 0) {
                spel.remove(i);
                // Efek
            }
        }
    }

    // mengembalikan daftar spell aktif
    public List<Spell> getActiveSpells() {
        return this.spel;
    }

    // meningkatkan level karakter sebanyak 1
    // mereset xp ke 0
    // meningkatkan baseAtk dan baseHp sebanyak attackUp dan healthUp
    public void levelUp() {
        if (this.exp >= (2*this.level - 1)) {
            this.exp -= (2*this.level - 1);
            this.level++;
            this.baseAtk = this.character.attackUp;
            this.baseHp = this.character.hpUp;
        } else {
            return;
        }
    }

    public boolean isDead() {
        if (this.baseHp + this.bonusHp <= 0) {
            return true;
        } else {
            return false;
        }
    }
}
