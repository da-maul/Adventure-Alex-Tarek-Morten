public class Weapon extends Item {
    int ammunition = -1;

    // First constructor creates a ranged weapon, if ammo is not set as var 3: weapon is melee
    public Weapon(String shortName, String longName, int ammunition) {
        super(shortName, longName);
        this.ammunition = (ammunition);
    }

    public Weapon(String shortName, String longName) {
        super(shortName, longName);
        this.ammunition = -1;
    }

    public boolean canUse(){
        return ammunition != 0;
    }

    public boolean isRanged(){
        return ammunition > 0;
    }

    public int getAmmunition() {
        return ammunition;
    }

    public void modAmmunition(int ammoMod) {
        this.ammunition = ammunition += ammoMod;
    }
}

