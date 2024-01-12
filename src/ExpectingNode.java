public class ExpectingNode extends Node {
    //This Node Will Get List<Move>, And Apply It On Every Pieces, Then Take The Max Evaluation Of Them
    public ExpectingNode(Node parent, Board board) {
        super(parent, board);
    }

    public Pair<Node, Float> getAverageEvaluation() {
        //Apply Moves On All Pieces, Resulting Expecting Nodes
        //Return The Average Evaluation Of Children With The Current Node
        //Average Evaluation Is Multiplying Each Child's Evaluation With Its Probability
    }
}
