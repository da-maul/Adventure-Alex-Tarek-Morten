public class WeaponRanged extends Weapon{

    int ammunition = 0;

    public WeaponRanged(String shortName, String longName, String attackVerb, int damage, int ammunition){
        super(shortName,longName, attackVerb, damage);
        this.ammunition = ammunition+1;
    }

    public void modAmmo(int ammoChange) {this.ammunition += ammoChange;}
    public void setAmmo(int ammunition) {this.ammunition = ammunition;}

    @Override
    public boolean canUse() {return ammunition > 0;}

    @Override
    public int getAmmunition() {return ammunition;}

    @Override
    public AttackReturn attack() {
        //giving higher-up attack methods Lego™ pieces they need to build stuff
        String failTarget = "nothing in particular, wasting a shot.";
        if (ammunition > 0){ammunition--;}
        return new AttackReturn(canUse(),attackVerb,shortName,failTarget,damage);

    }
}
