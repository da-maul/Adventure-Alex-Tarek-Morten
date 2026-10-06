public class WeaponMelee extends Weapon{

    public WeaponMelee(String shortName, String longName,String attackVerb, int damage){
        super(shortName,longName, attackVerb,damage);
    }

    @Override
    public boolean canUse() {return true;}

    @Override
    public int getAmmunition() {return -1;}

    @Override
    public int attack(String target) {
        if (target == null){target = "the air, like you just don't care...";}
        IO.println("You "+attackVerb+" your "+shortName+" at "+target+"!");
        return -damage;
    }
}
