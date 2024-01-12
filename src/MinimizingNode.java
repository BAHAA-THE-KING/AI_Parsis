import java.util.List;

public class MinimizingNode extends Node {
    List<Move> moveList;

    //This Node Will Get List<Move>, And Apply It On Every Pieces, Then Take The Min Evaluation Of Them
    public MinimizingNode(Node parent, Board board, List<Move> moveList) {
        super(parent, board);
        this.moveList = moveList;
    }

    public Pair<MinimizingNode, Float> getMinEvaluation() {
        //Apply Moves On All Pieces, Resulting Expecting Nodes
        //Return The Min Evaluation Of Children With The Current Node
    }
}
