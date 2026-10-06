import java.util.ArrayList;

public class Monster {
    String shortName;
    String longName;
    int HP = 10; //default value just so monsters don't instantly die.
    Room currentRoom;
    Weapon weapon;
    ArrayList<Item> deathItems= new ArrayList<>();

    //TODO optional strings
    //String examineText; (to be added later maybe)
    //String attackText; (IDK?)
    //String talkText; Would be really funny
    //String deathText; Shrug

    public Monster(String shortName, String longName, int HP){
        this.shortName = shortName; this.longName = longName;
        this.HP = HP;
    }

    //getters
    public boolean isDead(){return HP <= 0;}
    public String getShortName() {return shortName;}
    public String getLongName() {return longName;}
    public Weapon getWeapon() {return weapon;}
    public ArrayList<Item> getDeathItems() {return deathItems;}
    //setters
    public void modHP(int hpChange) {this.HP += hpChange;}
    public void setHP(int HP) {this.HP = HP;}
    public void setHome(Room home) {this.currentRoom = home;}
    public void giveWeapon(Weapon weapon) {this.weapon = weapon;}
    public void giveItems(ArrayList<Item> items) {this.deathItems = items;}
    public void giveItem(Item item) {this.deathItems.add(item);}

    @Override
    public String toString() {
        return longName;
    }
}
