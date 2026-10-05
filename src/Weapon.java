public class Weapon extends Item {
    int ammunition = -1;
    int damage = 1;

    // First constructor creates a ranged weapon, if ammo is not set as var 4: weapon is melee
    public Weapon(String shortName, String longName,int damage, int ammunition) {
        super(shortName, longName);
        this.damage = damage;
        this.ammunition = (ammunition);
    }
    public Weapon(String shortName, String longName, int damage) {
        super(shortName, longName);
        this.damage = damage;
        this.ammunition = -1;
    }

    //getters
    public boolean canUse(){return ammunition != 0;}
    public boolean isRanged(){return ammunition > 0;}
    public int getAmmunition() {return ammunition;}
    //setters
    public void modAmmunition(int ammoMod) {this.ammunition += ammoMod;}
    public void setAmmunition(int ammunition) {this.ammunition = ammunition;}
}

