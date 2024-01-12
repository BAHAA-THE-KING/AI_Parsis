import java.util.ArrayList;
import java.util.List;

public class MaximizingNode extends Node {
    List<Move> moveList;

    //This Node Will Get List<Move>, And Apply It On Every Pieces, Then Take The Max Evaluation Of Them
    public MaximizingNode(Node parent, Board board, List<Move> moveList) {
        super(parent, board);
        this.moveList = moveList;
    }

    Pair<MaximizingNode, Double> getMaxEvaluation() {
        //Apply Moves On All Pieces, Resulting Expecting Nodes
        //Return The Max Evaluation Of Children With The Current Node
        List<Pair<Node, Double>> children = new ArrayList<>();
        //TODO Use Abd's Function To Move All Available Moves On All Available Pieces
        for (Move move : moveList) {
            for (int i = 0; i < 4; i++) {
                Board copyBoard = new Board(board);
                Structure.applyMove(copyBoard, i, move, 'h');
                ExpectingNode expectingNode = new ExpectingNode(this, copyBoard);
                children.add(expectingNode.getAverageEvaluation("min"));
            }
        }
        double max = Double.MIN_VALUE;
        for (var child : children) {
            max = Math.max(max, child.value);
        }
        return new Pair<>(this, max);
    }
}
