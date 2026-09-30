public class Item {
    String shortName;
    String longName;

    public Item(String shortName, String longName){this.shortName = shortName; this.longName=longName;}

    public String getLongName() {return longName;}
    public String getShortName() {return shortName;}

    @Override
    public String toString() {
        return longName;
    }
}
