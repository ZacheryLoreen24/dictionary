/* Zachery Loreen
CS&145 3/5/2023
Lab #6
This program is a game of 20 questions which the user can
load in files and play the game using them, create a new file,
or alter an old game by playing. When the program guesses the user
object correctly the user will be prompt to play again. If the
program guesses incorrectly it will ask for the users object,
and a question to distinguish it and whether the answer is
true or false. Essentially making the program smarter with
each play through (as long as you save)
 */
import java.io.*;
import java.util.*;

public class QuestionTree {
    private QuestionNode root;
    private final UserInterface userI;
    private int games;
    private int wins;

    /* Constructor which creates a
    new instance of the QuestionTree class
    with the root node being computer
     */
    public QuestionTree(UserInterface ui) {
        root = new QuestionNode("computer");
        games = 0;
        wins = 0;
        userI = ui;

    } // QuestionTree method ends

    /* the save method saves the current game
    to a file named based on the PrintStream output
    it calls the private save method. */
    public void save(PrintStream output) {

        save(output, root);
    } // end of save method

    /* the private save method is a recursive method
    that saves the current tree as a new file. If the current node
    is a leaf node print "A: node". If it's nota leaf node then print
    "Q: node" and recursively call itself until all yes nodes are found
    then recursively call itself until all no nodes are found
    */
    private void save(PrintStream out, QuestionNode node) {
        if (node.isLeaf()) { // if leaf node print A: "node"
            out.print("A:" + node);
        } else { // else if it's not a leaf node print Q: "node"
            out.println("Q:" + node);

            save(out, node.yes);

            out.println();

            save(out, node.no);
        } // end of if else statement
    } // end of save method

    /* play method calls the playNode private method
    and increments the number of games played
     */
    public void play() {
        root = playNode(root);
        games++;
    } // end of play method

    /* playNode is a recursive method which uses a leaf
    node as the base case, if it is leaf node and the user
    says the object is correct, the program wins and increments
    the wins counter. If not it will learn about the object
    by calling learnNode. If the node isn't a leaf,
    it will ask the user a question expecting a yes no answer.
    And then recursively call itself based on the answer
     */
    private QuestionNode playNode(QuestionNode node) {
        if (node.isLeaf()) {
            userI.print("Would your object happen to be " + node + "? ");
            boolean guessedAnswer = userI.nextBoolean();

            if (guessedAnswer) {
                userI.println("I win!");
                ++wins;
            } else {
                node = learnNode(node);
            } // end of if else statement
        } else {
            userI.print(node.toString() + " ");
            boolean userResponse = userI.nextBoolean();

            if (userResponse) {
                node.yes = playNode(node.yes);
            } else {
                node.no = playNode(node.no);
            } // end of if else statement
        } // end of if else statement

        return node;
    } // end of playNode method

    /* the load method accesses a previously played and saved game
    inputted by the user. It then calls the loadNode method. */
    public void load(Scanner input) {

        root = loadNode (input);
    } // end of load method

    /* learnNode this method which is called from the playNode method
    if the program is unable to guess the object the user is thinking of.
    It then asks the user for the object their thinking of, then a question
    which could be used to guess the object correctly in the future. And
    if the question is true/false for the object. If the
    answer is no a newNode will be created for as the no child, if yes then
    a newNode will be created as the yes child.
    */
    private QuestionNode learnNode(QuestionNode node) {
        userI.print("I lose. What is your object? ");

        QuestionNode newNode = new QuestionNode (userI.nextLine());

        userI.print("Type a yes/no question to distinguish " +
                "your item from " + node + ":");

        String question = userI.nextLine();

        userI.print("And what is the answer for your object?");

        if (userI.nextBoolean()) {
            return new QuestionNode(newNode, node, question);
        } else {
            return new QuestionNode(node, newNode, question);
        }
    } // end of private learnedNode method

    /* The loadNode methods purpose is to load a previous game from a file
    it takes input from the user and if that index 0 of hasNext is = A, then it
    is a leaf node holding the name of the object, thus a new QuestionNode
    will be created holding the answer. If index 0 is not = A then it's not a leaf
    and a new QuestionNode will be created and call itself recursively, creating a new
    QuestionNode object with the question and childs nodes
     */
    private QuestionNode loadNode(Scanner input) {
        QuestionNode node = null;

        if (input.hasNext()) {
            String[] data = input.nextLine().split(":", 2);
            if (data[0].equals("A")) {
                node = new QuestionNode(data[1]);
            } else {
                node = new QuestionNode(loadNode(input), loadNode(input), data[1]);
            } // end of if else statement
        } // end of if statement

        return node;
    } // end of loadedNode private method

    /* the accessor method totalGames
   returns the total games played */
    public int totalGames() {

        return games;
    } // end of totalGames method

    /* the gamesWon accessor method
     returns the number of games won */
    public int gamesWon() {

        return wins;
    } // end of gamesWon method
} // end of QuestionTree class
