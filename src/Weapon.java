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

    //attack passes info from the Weapon to higher up methods in an AttackReturn record
    public abstract AttackReturn attack();

    @Override
    public String toString() {
        return longName;
    }
}

