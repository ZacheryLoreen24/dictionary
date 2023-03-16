/* Zachery Loreen
CS 145
3/5/2023
This class behaves as a binary tree structure,
data represents question or guess from user, while
no represents the left child node, yes represents the right

 */
public class QuestionNode {
    private final String data;
    public QuestionNode yes; // child node left
    public QuestionNode no; // child node right

    /* constructor method QuestionNode which takes
     the parameters yes, no, data and sets
     their values */
    public QuestionNode(QuestionNode yes,
                        QuestionNode no,
                        String data) {
        this.yes = yes;
        this.no = no;
        this.data = data;
    } // end of QuestionNode

    /* constructor method */
    public QuestionNode(String data) {

        this(null, null, data);
    } // end of QuestionNode method

    /* accessor toString method which
     returns data as a string. */
    public String toString() {

        return data;
    } // end of toString method

    /* accessor isLeaf method if both yes, and no are null, then
     the node is a leaf node. */
    public boolean isLeaf() {

        return yes == null && no == null;
    } // end of isLeaf method
} // end of QuestionNode method