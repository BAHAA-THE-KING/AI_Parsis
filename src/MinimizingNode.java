import java.util.ArrayList;
import java.util.List;

public class MinimizingNode extends Node {
    List<Move> moveList;

    //This Node Will Get List<Move>, And Apply It On Every Pieces, Then Take The Min Evaluation Of Them
    public MinimizingNode(Node parent, Board board, List<Move> moveList) {
        super(parent, board);
        this.moveList = moveList;
    }

    Pair<MinimizingNode, Double> getMinEvaluation() {
        //Apply Moves On All Pieces, Resulting Expecting Nodes
        //Return The Min Evaluation Of Children With The Current Node
        List<Pair<Node, Double>> children = new ArrayList<>();
        //TODO Use Abd's Function To Move All Available Moves On All Available Pieces
        for (Move move : moveList) {
            for (int i = 0; i < 4; i++) {
                Board copyBoard = new Board(board);
                Structure.applyMove(copyBoard, i, move, 'h');
                ExpectingNode expectingNode = new ExpectingNode(this, copyBoard);
                children.add(expectingNode.getAverageEvaluation("max"));
            }
        }
        double min = Double.MAX_VALUE;
        for (var child : children) {
            min = Math.min(min, child.value);
        }
        return new Pair<>(this, min);
    }
}
