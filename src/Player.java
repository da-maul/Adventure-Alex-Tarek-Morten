import java.util.ArrayList;

public class Player {
    private Room currentRoom;
    private ArrayList<Item> items = new ArrayList<>();

    //getters
    public Room getCurrentRoom() {return currentRoom;}
    public String roomName(){return currentRoom.getName();}
    public boolean roomIsLit(){return currentRoom.isLit();}

    //setters
    public void setStartingRoom(Room startingRoom) {currentRoom = startingRoom;}
    public void lightRoom(){currentRoom.setLit(true);}
    public void darkRoom(){currentRoom.setLit(false);}
    //descibes the room
    public void describeRoom(){
        IO.println(currentRoom.getDescription());
        //item related display
        if (currentRoom.getItems().size()==0){
            return;
        } else if (getCurrentRoom().getItems().size() == 1) {
            IO.println("There is a "+currentRoom.getItems().get(0)+" in here...");
        }
        else{
            IO.println("There are some things in here:");
            int index =0;
            for (Item item : currentRoom.getItems()){
                IO.println(item); //candidate for adding LONG NAME here
                index++;
            }
        }
    }
    public void describeInventory(){
        if (items.size() == 0){IO.println("You're not carrying anything!");}
        else if (items.size() == 1) {IO.println("You've just got a "+items.getFirst());}
        else {IO.println("You've got some stuff:");
            int index =0;
            for (Item item: items){
            IO.println(item); //candidate for adding LONG NAME here
            }
        }
    }
    //item related setters
    public void addItemSelf(Item item){items.add(item);}
    public void addItemRoom(Item item){currentRoom.addItem(item);}
    public void removeItemSelf(Item item){items.remove(item);}
    public void removeItemRoom(Item item){currentRoom.removeItem(item);}

    public boolean addItem(String itemname){
        Item item = itemRoomSearch(itemname);
        if (item == null){return false;}
        addItemSelf(item);
        removeItemRoom(item);
        return true;
    }
    public boolean removeItem(String itemName){
        Item item = itemSelfSearch(itemName);
        if (item == null){return false;}
        addItemRoom(item);
        removeItemSelf(item);
        return true;
    }


    //directional movement, formatted to match my deranged way of factoring code (Alex).
    public boolean moveNorth() {if (currentRoom == null) {return false;} return moveTo(currentRoom.getNorth());}
    public boolean moveEast() {if (currentRoom == null) {return false;} return moveTo(currentRoom.getEast());}
    public boolean moveSouth() {if (currentRoom == null) {return false;} return moveTo(currentRoom.getSouth());}
    public boolean moveWest() {if (currentRoom == null) {return false;} return moveTo(currentRoom.getWest());}
    //Isn't it nice and pretty how they're all on one line each? ^

    // base method for moving the player (directional or teleportation)
    private boolean moveTo(Room nextRoom) {
        if (nextRoom == null || nextRoom == currentRoom) {
            return false;}
        currentRoom = nextRoom;
        return true;
    }

    // Player version of Xyzzy spell, takes in an arraylist of the rooms in the Map.
    // Would technically be easier to put in the consoleUI, but I don't want the teachers to explode seeing
    // movement-related code OUTSIDE of the Player Class

    public boolean xyzzyP(ArrayList<Room> rooms, String target){
        int roomNumber;
        try { roomNumber = Integer.parseInt(target);}
        catch (NumberFormatException e) {
            return false;
        }
        //verifying that the room in question is actually in the array
        if (rooms == null || roomNumber < 1 || roomNumber > rooms.size())
        {return false;}
        Room destination = rooms.get(roomNumber-1);
        //This line is probably not needed but I'm keeping it in for good measure
        if (destination == null) {return false;}
        //initializing the move
        moveTo(destination); return true;
    }

    //item searching methods
    public Item itemSearch(ArrayList<Item> items, String itemName){
        if (itemName == null || items == null) {
            return null;
        }
        for (Item item : items){
            if (itemName.equalsIgnoreCase(item.getName())){
                return item;
            }
        }
        return null;
    }

    public Item itemSelfSearch(String itemName){
        return itemSearch(items, itemName);
    }
    public Item itemRoomSearch(String itemName){
        return itemSearch(currentRoom.getItems(), itemName);
    }
}