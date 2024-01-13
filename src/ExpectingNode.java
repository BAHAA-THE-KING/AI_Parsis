import java.util.ArrayList;
import java.util.List;

public class ExpectingNode extends Node {
    //This Node Will Get List<Move>, And Apply It On Every Pieces, Then Take The Max Evaluation Of Them
    public ExpectingNode(Node parent, Board board) {
        super(parent, board);
    }

    Pair<Node, Double> getAverageEvaluation(String behavior) {
        //Apply Moves On All Pieces, Resulting Expecting Nodes
        //Return The Average Evaluation Of Children With The Current Node
        //Average Evaluation Is Multiplying Each Child's Evaluation With Its Probability
        List<Pair<List<Move>, Double>> availableMoves = new ArrayList<>(MoveCombinations.allMoves.values());
        Pair<Node, Double> avg = new Pair<>(this, 0.0);
        if (behavior.equals("max")) {
            List<MaximizingNode> children = new ArrayList<>();
            for (var move : availableMoves) {
                children.add(new MaximizingNode(parent, board, move.key));
            }
            for (int i = 0; i < children.size(); i++) {
                MaximizingNode child = children.get(i);
                avg.value += availableMoves.get(i).value * child.getMaxEvaluation().value;
            }
        } else {
            List<MinimizingNode> children = new ArrayList<>();
            for (var move : availableMoves) {
                children.add(new MinimizingNode(parent, board, move.key));
            }
            for (int i = 0; i < children.size(); i++) {
                MinimizingNode child = children.get(i);
                avg.value += availableMoves.get(i).value * child.getMinEvaluation().value;
            }
        }
        return avg;
    }
}