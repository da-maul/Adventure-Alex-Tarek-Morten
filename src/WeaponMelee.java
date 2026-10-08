public class WeaponMelee extends Weapon{

    public WeaponMelee(String shortName, String longName,String attackVerb, int damage){
        super(shortName,longName, attackVerb,damage);
    }

    @Override
    public boolean canUse() {return true;}

    @Override
    public int getAmmunition() {return -1;}

    @Override
    public AttackReturn attack() {
        //giving higher-up attack methods Lego™ pieces they need to build stuff
        String failTarget = "the air, like you just don't care...";
        return new AttackReturn(canUse(),attackVerb,shortName,failTarget,damage);
    }
}
