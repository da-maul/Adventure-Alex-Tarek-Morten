import java.util.ArrayList;

public class Room {
    private final String name;
    private String description;
    private Room north;
    private Room east;
    private Room south;
    private Room west;
    private boolean isLit = true;
    private ArrayList<Item> items = new ArrayList<>();

    //Constructor is basic because most of a room is created in the "roomsInit" method in the Adventure class
    public Room(String name){
        this.name = name;
    }

    //setters
    public void setDescription(String description) {this.description = description;}
    public void setNorth(Room north) {
        if(north != null){this.north = north;}}
    public void setEast(Room east) {
        if(east != null){this.east = east;}}
    public void setSouth(Room south) {
        if(south != null){this.south = south;}}
    public void setWest(Room west) {
        if(west != null){this.west = west;}}
    public void setLit(boolean lit) {
        this.isLit = lit;}
    //item related setters
    public void addItem(Item item){items.add(item);}
    public void addItem(String itemName){addItem(new Item(itemName));}
    public void removeItem(Item item){items.remove(item);}

    //getters
    public String getName() {return name;}
    public String getDescription() {return description;}
    public boolean isLit() {return isLit;}
    public ArrayList<Item> getItems() {return items;}

    //directional getters return this room if no room exists in that direction
    public Room getNorth() {
        if (north != null){return north;}
        else {return this;}
        }
    public Room getEast() {
        if (east != null){return east;}
        else {return this;}
    }
    public Room getSouth() {
        if (south != null){return south;}
        else {return this;}
    }
    public Room getWest() {
        if (west != null){return west;}
        else {return this;}
    }
}
