import java.util.ArrayList;

public class Map {

    ArrayList<Room> rooms = new ArrayList<>();
    Room startingRoom;
    String mapGreeting;

    //various setters required for initialization
    private void newRoom(String name){rooms.add(new Room(name));}
    private void roomAddDesc(int roomNumber,String description){rooms.get(roomNumber-1).setDescription(description);}
    private void roomAddItem(int roomNumber, String itemShortName, String itemLongName){rooms.get(roomNumber-1).addItem(itemShortName, itemLongName);}
    private void roomAddItem(int roomNumber, Item item){rooms.get(roomNumber-1).addItem(item);}
    private void roomAddMonster(int roomNumber, Monster monster){rooms.get(roomNumber-1).addMonster(monster);}

    private void roomSetDirs(int roomNumber, int northNum, int eastNum, int southNum, int westNum){
        //sets adjacent rooms by room number(int), first is the room whose neighbors are being set.
        //the following 4 are rooms by their number in NESW order, empty directions are replaced by "0"

        //initialize working variables
        Room workingRoom = rooms.get(roomNumber-1);
        Room north; Room east; Room south; Room west;
        //this could probably be more elegant as a loop but fuck that
        //anyways value of "0" is a standin for a null value so this doesn't throw an error when it's called.
        if (northNum == 0 || northNum > rooms.size()){ north = null;} else {north = rooms.get(northNum-1);}
        if (eastNum == 0 || eastNum > rooms.size()){east = null;} else {east = rooms.get(eastNum-1);}
        if (southNum == 0 || southNum > rooms.size()){south = null;} else {south = rooms.get(southNum-1);}
        if (westNum == 0 || westNum > rooms.size()){west = null;} else {west = rooms.get(westNum-1);}
        //assign rooms, Room setter functions can handle NULL by doing nothing.
        workingRoom.setNorth(north);
        workingRoom.setEast(east);
        workingRoom.setSouth(south);
        workingRoom.setWest(west);
    }
    public String roomDesc(int roomNumber){return rooms.get(roomNumber-1).getDescription();}

    //might be bad, should probably be replaced by specific methods
    public Room getRoom(int roomNumber){return rooms.get(roomNumber-1);}
    public ArrayList<Room> getRooms(){return rooms;}

    private void lightRoom(int roomNumber){
        //lights a room, rooms are dark by default
        rooms.get(roomNumber-1).setLit(true);
    }
    public String getGreeting(){return mapGreeting;}

    public void initMap1(){
        mapGreeting = """
                    
                You awaken with a start, a bump on the back of your head, your whole body feels bruised.
                You open your eyes and are dazzled by the sun, sitting up, you realize you're surrounded by walls
                on all sides. You're in a damp, cool room with walls of grey cobblestone, dark moss growing in
                the cracks. A dungeon... You rise to your feet and dust yourself off, determined to make your way
                out of here...
                """;

        newRoom("Entryway (Room 1)"); newRoom("Dry Corridor (Room 2)"); newRoom("Warehouse (Room 3)");
        newRoom("Armory (Room 4)"); newRoom("Treasury (Room 5)"); newRoom("Kitchen (Room 6)");
        newRoom("Barracks (Room 7)"); newRoom("Wet Corridor (Room 8)"); newRoom("Mess Hall (Room 9)");
        newRoom("Secret Room (Room 10)");
        //remember that setDirs index 1 is working room, 2-5 are NESW
        //all rooms must be initialized before neighbors can be defined
        roomSetDirs(1,0,2,4,0); roomSetDirs(2,0,3,0,1); roomSetDirs(3,0,0,6,2);
        roomSetDirs(4,1,0,7,0); roomSetDirs(5,0,0,8,0); roomSetDirs(6,3,0,9,0);
        roomSetDirs(7,4,8,0,0); roomSetDirs(8,5,9,0,7); roomSetDirs(9,6,0,0,8);
        roomSetDirs(10,0,0,0,0);
        //setting light levels
        lightRoom(1); lightRoom(2); lightRoom(4); lightRoom(10);
        // initializing valid items
        Food tarekBody = new Food("tarek", "Tarek's bloody corpse", -100);
        Food curry = new Food("curry", "some hot spicy curry", 10);
        //initializing valid weapons
        Weapon bow = new Weapon("bow", "a twangy recurve bow (and some arrows)", 30, 5);
        Weapon axe = new Weapon("axe", "a old rusty axe", 20);
        Weapon derringer = new Weapon("derringer", "a tiny derringer pistol",1,2);
        //initializing valid monsters
        Monster tarek = new Monster("tarek", "some guy named Tarek", 1);
        tarek.giveWeapon(derringer); tarek.giveItem(tarekBody);

        // setting room items
        roomAddItem(1,"lamp", "an old oil lamp"); roomAddItem(5,"gold", "a bag of gold");
        roomAddItem(1, curry);
        //setting room weapons
        roomAddItem(4, axe); roomAddItem(1, bow);
        //setting room monsters
        roomAddMonster(1,tarek);
        //Room descriptions
        roomAddDesc(1, """
                Light filters into this room from a hole in the ceiling, bathing it in a pale glow.
                There are two doors. One is to your EAST and another is to the SOUTH""");
        roomAddDesc(2,"""
                This dismal corridor is lit by a doorway to the WEST and grows darker as it heads to the EAST.""");
        roomAddDesc(3,"""
                This is a storage room, all manner of ruined barrels and crates are lined up in rows, making it difficult to navigate
                There is a doorway to the WEST which leaks a bit of light, and another to the SOUTH.""");
        roomAddDesc(4,"""
                Dimly lit and a little humid, this room is lined with racks of rusty weapons and trunks filled with ratty sets of armor.
                There is a door to the NORTH through which light leaks, and one to the SOUTH which looks moldy and damp.""");
        roomAddDesc(5,"""
                A Treasure room! or at least it used to be. There's empty chests and some scattered copper coins.
                Seems it was looted a long time ago...
                There's only the door to the SOUTH that you came in from.""");
        roomAddDesc(6,"""
                Bleugh... it smells awful in this disgusting kitchen, unclean tables and moldy pots of rotten foodstuffs are scattered around.
                Fresh air flows in from the NORTH, the rotten door to the SOUTH looks damaged by water.""");
        roomAddDesc(7,"""
                Waterlogged beds and mattresses float in this flooded room, you surmise it used to be a barracks.
                The door to the NORTH seems dryer... water seems to flow in from the EAST.""");
        roomAddDesc(8,"""
                There's a stream running through this corridor flowing from EAST to WEST
                to the NORTH there's a section of wall that juts out. You realize it's an open SECRET DOOR...""");
        roomAddDesc(9,"""
                Long tables and benches are staggered about the room haphazardly, with scattered pewter plates and utensils all about.
                An overturned pitcher on one of the tables endlessly spews forth water, which trickles in a stream to the WEST
                A foul odor sneaks in from the door to the NORTH, and the stream of water disappears into a corridor leading WEST.""");
        roomAddDesc(10,"""
                You appear on a platform of stone, floating in an endless expanse of stars. You feel a bit uneasy.
                You see ALEX, they wave to you. "Ah, I see you've found the secret room!" they say.
                There are no doors in any direction, the only way out is the same way you came in...""");

        startingRoom = rooms.getFirst();
    }
}
