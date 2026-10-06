public class WeaponRanged extends Weapon{

    int ammunition = 0;

    public WeaponRanged(String shortName, String longName, String attackVerb, int damage, int ammunition){
        super(shortName,longName, attackVerb, damage);
        this.ammunition = ammunition;
    }

    public void modAmmo(int ammoChange) {this.ammunition += ammoChange;}
    public void setAmmo(int ammunition) {this.ammunition = ammunition;}

    @Override
    public boolean canUse() {return ammunition > 0;}

    @Override
    public int getAmmunition() {return ammunition;}

    @Override
    public int attack(String target) {
        if (target == null){target = "nothing in particular, wasting a shot.";}
        if (canUse()){
            IO.println("You "+attackVerb+" your "+shortName+" at "+target);
            return damage;
        }
        else {IO.println("Your "+shortName+" is out of ammo!");return 0;}
    }
}
