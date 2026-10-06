public abstract class Weapon extends Item {
    int damage = 1;
    String attackVerb;


    public Weapon(String shortName, String longName, String attackVerb, int damage) {
        super(shortName, longName);
        this.attackVerb = attackVerb;
        this.damage = damage;
    }

    //getters
    public abstract boolean canUse();
    public abstract int getAmmunition();

    public abstract int attack(String target);

    @Override
    public String toString() {
        return longName;
    }
}

