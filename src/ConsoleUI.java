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
    //only for the purpose of describing the room or not for move/non-move inputs;
    boolean playerMoved = true;

    //main loop happens here
    public void run(Map map){
        initialize(map);
        while (true){
            if (checkIfDead()){return;}
            if (playerMoved){
                //describes the room before input is accepted if player didn´t move.
                IO.println("You are in the "+ roomName());
                if (roomIsLit()) {describeRoom();}
                else {describeDarkRoom();}
            }
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
                    case "EAT" -> eat(command.target());
                    case "EQUIP" -> equip(command.target());
                    case "ATTACK" -> attack(command.target());
                    case "INVENTORY" -> describeInventory();
                    case "HEALTH" -> sayhealth();
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
        tryInput = true; playerMoved=false;
    }

    private void castDark() {
        player.darkRoom();
        playerTeleported = true; spellWasCast = true; tryInput = false; playerMoved=false;
    }

    private void castLight() {
        player.lightRoom();
        playerTeleported = true; spellWasCast = true; tryInput = false; playerMoved=false;
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

    private void describeInventory(){player.describeInventory(); playerMoved=false;}

    private ArrayList<Command> inputParse(){
        //this method is such a monster
        //basically tokenizes the input string, ignores the first row of tokens
        //looks for valid "command" words, and then once it finds one, makes a new 'command' record object
        //behaviors depends on if the command does or does not have a target.
        //targetless commands simply make a command object with a blank target
        //commands with targets will make new command objects with every following word as target
        //until a new command is found, after which, that command makes new commands with targets
        //allows the player to input "eat curry attack tarek" all on one line, both commands will be executed
        //hypothetically also allows attack tarek tarek to attack tarek twice
        String input = IO.readln("What do you do?").toUpperCase();
        String[] words = input.split(" ");
        ArrayList<Command> commandsFound = new ArrayList<>();
        String currentCommand = null;
        for (String word : words){
            switch (word) {
                // Ignorer ord som "GO", "ME", "ON", "OFF"
                case "GO", "ME", "ON", "OFF", "THE" -> {}
                // Movement
                case "NORTH", "N" -> commandsFound.add(new Command("NORTH", ""));
                case "EAST", "E" -> commandsFound.add(new Command("EAST", ""));
                case "SOUTH", "S" -> commandsFound.add(new Command("SOUTH", ""));
                case "WEST", "W", "WEAST" -> commandsFound.add(new Command("WEST", ""));
                // Magie / Lys
                case "LIGHT", "TURN ON LIGHT", "L" -> commandsFound.add(new Command("LIGHT", ""));
                case "DARKNESS", "TURN OFF LIGHT", "DK" -> commandsFound.add(new Command("DARKNESS", ""));
                // Handlinger der KRÆVER et target (f.eks. TAKE LAMP)
                case "X", "XYZZY" -> currentCommand = "XYZZY";
                case "TAKE", "T", "GRAB" -> currentCommand = "TAKE";
                case "DROP", "D", "REMOVE" -> currentCommand = "DROP";
                case "EAT", "NOM", "CONSUME", "SCARF", "NIBBLE", "INHALE" -> currentCommand = "EAT";
                case "EQUIP", "BRANDISH" ,"WIELD" -> currentCommand = "EQUIP";
                case "ATTACK","HIT","SWING","SLASH","SHOOT","FIRE","THRUST","KILL", "A" -> currentCommand = "ATTACK";
                case "INVENTORY", "INV", "I", "STUFF" -> {
                    commandsFound.add(new Command("INVENTORY", ""));
                    currentCommand = null;
                }
                case "HEALTH", "HP", "STATUS" -> commandsFound.add(new Command("HEALTH",""));
                case "HELP", "H" -> {
                    commandsFound.add(new Command("HELP", ""));
                    currentCommand = null;
                }
                case "EXIT", "EX", "QUIT" -> {
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
            tryInput = false; playerMoved=true; return true;}
        else {playerFailedToMove = true; playerMoved=false; return false;}
    }
    public boolean moveEast(){
        if (player.moveEast()){
            tryInput = false; playerMoved=true; return true;}
        else {playerFailedToMove = true; playerMoved=false; return false;}
    }
    public boolean moveSouth(){
        if (player.moveSouth()){
            tryInput = false; playerMoved=true; return true;}
        else {playerFailedToMove = true; playerMoved=false; return false;}
    }
    public boolean moveWest(){
        if (player.moveWest()){
            tryInput = false; playerMoved=true; return true;}
        else {playerFailedToMove = true; playerMoved=false; return false;}
    }
    public boolean xyzzy(String target){
        playerTeleported = true; playerMoved=true; tryInput = false;
        //unfortunately no other way to write this line
        return player.xyzzyP(map.getRooms(),target);
    }

    public void take(String desiredItem){
        if (!player.addItem(desiredItem)){
            IO.println("There's nothing like that here...");
        }
        else {IO.println("You take the "+ desiredItem);}
        playerMoved=false;
    }
    public void drop(String desiredItem){
        if (!player.removeItem(desiredItem)){
            IO.println("You don't have anything like that...");
        }
        else {IO.println("You drop the "+ desiredItem);}
        playerMoved=false;
    }
    public void equip(String desiredWeapon){player.equip(desiredWeapon); playerMoved=false;}

    public void eat(String desiredFood){player.eat(desiredFood); sayhealth(); playerMoved=false;}

    public void attack(String target){
        //player needs to be armed before anything else
        if (!player.isArmed()){IO.println("You don't have a weapon!");playerMoved = false; return;}
        //AttackReturns contain a lot of info about the weapon.
        //occurs to me that I probably could have just passed through the WEAPON itself
        //but like, IDK, I already wrote this, and it'll work... so...
        AttackReturn pAttackReturn = player.attack();

        //unpacking our AttackReturn into more readable variables
        boolean canAttack = pAttackReturn.canUse();
        String verb = pAttackReturn.attackVerb();
        String weapon = pAttackReturn.weaponName();
        String failTarget = pAttackReturn.failTarget();
        int damage = pAttackReturn.damage();

        Monster monsterTarget = player.monsterSearch(target);
        String monsterName = monsterTarget.shortName;
        if (!canAttack){
            IO.println("Your "+weapon+" is out of ammo!");
        }
        else if (monsterTarget == null){
            IO.println("You "+verb+" your "+weapon+" at "+failTarget);
        }
        else {
            IO.println("You "+verb+" your "+weapon+" at "+monsterName+", dealing "+damage+" damage!");
            monsterTarget.modHP(-damage);
        }
        if (monsterTarget.isDead()){
            IO.println("The "+monsterName+" crumples to the flood in a heap, dead...");
            if (monsterTarget.hasItems()){
                IO.println("It drops some items on the floor:");
                for (Item item : monsterTarget.getDeathItems()){
                    IO.println(item);
                }
                monsterTarget.dropItems();
            }
        }
        else {
            //Monster's turn to attack
            AttackReturn mAttackReturn = monsterTarget.attack();
            canAttack = mAttackReturn.canUse();
            verb = mAttackReturn.attackVerb();
            weapon = mAttackReturn.weaponName();
            failTarget = mAttackReturn.failTarget();
            damage = mAttackReturn.damage();

            if (!canAttack){
                IO.println("The "+monsterName+" is defenceless!");
            }
            else {
                IO.println("The "+monsterName+" "+verb+"s its "+weapon+" at you! dealing "+damage+" damage!!!");
                player.modHealth(-damage);
            }
        }
         playerMoved = false;
    }
    public void sayhealth(){IO.println("Current Health: " +player.getHealth()); playerMoved=false;}
    public boolean checkIfDead(){
        if (player.getHealth() <= 0){
            IO.println("You have died, RIP Bozo"); return true;
        }
        else {return false;}
    }
}
