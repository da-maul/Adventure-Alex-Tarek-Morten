import java.util.ArrayList;

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

            // Get all commands from one line of input
            ArrayList<Command> commands = inputParse();
            // Execute every command before asking for new input
            for (Command command : commands) {
                resetLoopBools();
                switch (command.command()) {
                    case "NORTH" -> moveNorth();
                    case "EAST" -> moveEast();
                    case "SOUTH" -> moveSouth();
                    case "WEST" -> moveWest();
                    case "XYZZY" -> xyzzy(command.target());
                    case "LIGHT" -> castLight();
                    case "DARKNESS" -> castDark();
                    case "TAKE" -> take(command.target());
                    case "DROP" -> drop(command.target());
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

    private ArrayList<Command> inputParse(){
        String input = IO.readln("What do you do?").toUpperCase();
        String [] words = input.split(" ");
        ArrayList<Command> commandsFound = new ArrayList<>();
        String currentCommand = null;
        for (String word : words){
            switch (word) {
                // Ignorer ord som "GO", "ME", "ON", "OFF"
                case "GO", "ME", "ON", "OFF" -> {}
                // Movement
                case "GO NORTH", "N" -> commandsFound.add(new Command("NORTH", ""));
                case "GO EAST", "E" -> commandsFound.add(new Command("EAST", ""));
                case "GO SOUTH", "S" -> commandsFound.add(new Command("SOUTH", ""));
                case "GO WEST", "W" -> commandsFound.add(new Command("WEST", ""));
                // Magie / Lys
                case "CAST LIGHT", "TURN ON LIGHT", "L" -> commandsFound.add(new Command("LIGHT", ""));
                case "CAST DARKNESS", "TURN OFF LIGHT", "D" -> commandsFound.add(new Command("DARKNESS", ""));
                // Handlinger der KRÆVER et target (f.eks. TAKE LAMP)
                case "X", "XYZZY" -> currentCommand = "XYZZY";
                case "TAKE", "T" -> currentCommand = "TAKE";
                case "DROP", "d" -> currentCommand = "DROP";

                case "INVENTORY" -> {
                    commandsFound.add(new Command("INVENTORY", ""));
                    currentCommand = null;
                }
                case "HELP", "HELP ME", "H" -> {
                    commandsFound.add(new Command("HELP", ""));
                    currentCommand = null;
                }
                case "EXIT" -> {
                    commandsFound.add(new Command("EXIT", ""));
                    currentCommand = null;
                }
                default -> {
                    // Hvis ordet ikke er en kommando,
                    // er det muligvis et target.
                    if (currentCommand != null) {
                        commandsFound.add(new Command(currentCommand, word));
                    }
                }
            }
        }
        return commandsFound;
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

    public boolean xyzzy(String target){
        playerTeleported = true; tryInput = false;
        //unfortunately no other way to write this line
        return player.xyzzyP(map.getRooms(),target);
    }

    public void take(String desiredItem){
        if (!player.addItem(desiredItem)){
            IO.println("There's nothing like that here...");
        }
        else {IO.println("You take the "+ desiredItem);}
    }
    public void drop(String desiredItem){
        if (!player.removeItem(desiredItem)){
            IO.println("You don't have anything like that...");
        }
        else {IO.println("You drop the "+ desiredItem);}
    }

}
