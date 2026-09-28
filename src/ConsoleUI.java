public class ConsoleUI {
    // The methods in this class are a little disorganized
    // feel free to rearrange them if it bothers you.

    //variable declaration
    Player player = new Player();
    Map map;
    // commands is displayed when HELP action is called, probably should be an Enum
    String[] commands = {"NORTH","EAST","SOUTH","WEST","LIGHT","DARKNESS","XYZZY","TAKE","DROP","INVENTORY","HELP","EXIT"};
    // A little ugly but these checks need to be here so they work in run()
    // as well as other methods so they can be set in a clean way
    boolean tryInput = true; // <- might not be needed?
    boolean playerTeleported = false;
    boolean spellWasCast = false;
    boolean playerFailedToMove = false;

    //main loop happens here
    public void run(Map map){
        initialize(map);
        while (true){
            //describes the room before input is accepted
            IO.println("You are in the "+ roomName());
            if (roomIsLit()) {describeRoom();}
            else {describeDarkRoom();}
            //input loop plays until player makes a valid move
            tryInput = true;
            while (tryInput) {
                resetLoopBools();
                switch (promptPlayer()) {
                    case "NORTH" -> moveNorth();
                    case "EAST" -> moveEast();
                    case "SOUTH" -> moveSouth();
                    case "WEST" -> moveWest();
                    case "XYZZY" -> xyzzy();
                    case "LIGHT" -> castLight();
                    case "DARKNESS" -> castDark();
                    case "TAKE" -> take();
                    case "DROP" -> drop();
                    case "INVENTORY" -> describeInventory();
                    case "HELP" -> help();
                    case "EXIT" -> {return;}
                }
                //if the player cast light or darkness
                if (spellWasCast){showCorrectSpellText();}
                //if they did not move but had a valid input
                else if (playerTeleported) {break;}
                //if they tried to move to an invalid location.
                else if (playerFailedToMove) {
                    IO.println("You get confused and bump into a wall...");
                }
            }
        }
    }

    //I hate this method but it makes things look real neat...
    private String roomName() {return player.roomName();}

    private void showCorrectSpellText() {
        if (player.roomIsLit()) {IO.println("You cast a spell of light on this room!\n");}
        else {IO.println("You cast a spell and cloak this room in darkness...\n");}
    }

    private void help() {
        //commands can be found at top of class
        IO.print("Commands are: ");
        for (String command :commands){IO.print(command+", ");}
        IO.println(); //just to add some white space after
        tryInput = true;
    }

    private void castDark() {
        player.darkRoom();
        playerTeleported = true; spellWasCast = true; tryInput = false;
    }

    private void castLight() {
        player.lightRoom();
        playerTeleported = true; spellWasCast = true; tryInput = false;
    }

    private void resetLoopBools() {
        //gets called every input loop to clean up booleans used for logic
        tryInput = true; playerTeleported = false;
        spellWasCast = false; playerFailedToMove = false;
    }

    private static void describeDarkRoom() {IO.println("You strain your eyes, it's too dark to see. You need some LIGHT");}

    public void initialize(Map map){
        this.map = map;
        //basic admin stuff to make sure everything is working together at the start.
        IO.println(map.getGreeting());
        //handshake between the Player and the Map, sets the first room for the Player
        player.setStartingRoom(map.getRoom(1));
    }

    public boolean roomIsLit(){return player.roomIsLit();}

    private void describeRoom() {player.describeRoom();}

    private void describeInventory(){player.describeInventory();}

    private String promptPlayer(){
        // method takes input and boils them down
        String input = IO.readln("What do you do?");
        // correct inputs do not need to be changed and are handled by "default"
        input = input.toUpperCase();
        switch (input){
            case "GO NORTH", "N" -> input = "NORTH";
            case "GO EAST", "E" -> input = "EAST";
            case "GO SOUTH", "S" -> input = "SOUTH";
            case "GO WEST", "W" -> input = "WEST";
            case "CAST LIGHT", "TURN ON LIGHT", "L" -> input = "LIGHT";
            case "CAST DARKNESS", "TURN OFF LIGHT", "D" -> input = "DARKNESS";
            case "X" -> input = "XYZZY";
            case "H", "HELP ME", "UH", "UM", "" -> input = "HELP";
            default -> {return input;}
        }
        return input;
    }

    public boolean moveNorth(){
        if (player.moveNorth()){
            tryInput = false; return true;}
        else {playerFailedToMove = true; return false;}
    }
    public boolean moveEast(){
        if (player.moveEast()){
            tryInput = false; return true;}
        else {playerFailedToMove = true; return false;}
    }
    public boolean moveSouth(){
        if (player.moveSouth()){
            tryInput = false; return true;}
        else {playerFailedToMove = true; return false;}
    }
    public boolean moveWest(){
        if (player.moveWest()){
            tryInput = false; return true;}
        else {playerFailedToMove = true; return false;}
    }

    public boolean xyzzy(){
        playerTeleported = true; tryInput = false;
        //unfortunately no other way to write this line
        return player.xyzzyP(map.getRooms());
    }

    public void take(){
        String desiredItem = IO.readln("What do you want to take?");
        if (!player.addItem(desiredItem)){
            IO.println("There's nothing like that here...");
        }
        else {IO.println("You take the "+ desiredItem);}
    }
    public void drop(){
        String desiredItem = IO.readln("What do you want to drop?");
        if (!player.removeItem(desiredItem)){
            IO.println("You don't have anything like that...");
        }
        else {IO.println("You drop the "+ desiredItem);}
    }

}
