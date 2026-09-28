void main() {
    //creates a new "Adventure" object and executes main program
    Adventure adventure = new Adventure();

    adventure.run();

}

//comment for Tarek/Morten

// I'm not in class today, because I have a doctor's appointment, but I do know how to finish the last bit
// of the assignment that we need. I'll write things out to you in a list of steps.
// First is to make a new Record class called "Command" that has two strings in it "command" and "target"
// Then make a list of valid commands (a lot like the one at the top of consoleUI) (the array named commands)
// Then in ConsoleUI make a method called "inputParse()" which uses String.split() to split the input from the
// player into different individual strings, using " " (space) as a seperator. Then iterate through and check
// which of the resulting strings match commands stored in the command list.
// once it's identified a command, it'll look to the next word in the array and mark that as the 'target'
// after that, make a new Command record using the command and the target, put it in an array to be executed
// in the main logic loop (in the run() method of ConsoleUI) until all commands are done,then prompt the player
//again. This will allow the player to pick up and drop multiple items at the same time
// (take coins tarek) becomes two Command records of "take, coins" and "take, tarek" which are then executed before
// the loop asks for player input again.
// to break it into steps:
// make a new Record Command, with String command and String target
// make a new method Parser that uses String.split() to split the input, and then turn it into Commands
// Put the Commands in an array and then only allow player input after all commands are executed.

// invalid words like "go" or other items that aren't in that particular room or inventory will just be ignored
// by the existing take() and drop() commands

//Good luck, and, if you can't totally figure it out by the end of class, just push what you made to a new branch
// and I'll try to fix it before I go to bed, just remember to turn in the project beforehand if we need to.
// Worse comes to worse our multi-line input should be "okay" enough to pass?