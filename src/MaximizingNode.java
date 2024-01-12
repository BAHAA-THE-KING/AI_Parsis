import java.util.List;

public class MaximizingNode extends Node {
    List<Move> moveList;

    //This Node Will Get List<Move>, And Apply It On Every Pieces, Then Take The Max Evaluation Of Them
    public MaximizingNode(Node parent, Board board, List<Move> moveList) {
        super(parent, board);
        this.moveList = moveList;
    }

    public Pair<MaximizingNode, Float> getMaxEvaluation() {
        //Apply Moves On All Pieces, Resulting Expecting Nodes
        //Return The Max Evaluation Of Children With The Current Node
    }
}
