public class Food extends Item{
    int healthChange;
    public Food(String shortName, String longName, int healthChange){
        super(shortName, longName);
        this.healthChange = (healthChange);
    }
}
