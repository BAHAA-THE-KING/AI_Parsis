import java.util.ArrayList;
import java.util.List;

public class MaximizingNode extends Node {
    List<Move> moveList;

    //This Node Will Get List<Move>, And Apply It On Every Pieces, Then Take The Max Evaluation Of Them
    public MaximizingNode(Node parent, Board board, List<Move> moveList) {
        super(parent, board);
        this.moveList = moveList;
    }

    Pair<MaximizingNode, Double> getMaxEvaluation(int depth) {
        //Apply Moves On All Pieces, Resulting Expecting Nodes
        //Return The Max Evaluation Of Children With The Current Node
        if (depth == 0) return new Pair<>(this, Structure.evaluate(board));
        List<Pair<Node, Double>> children = new ArrayList<>();
        MoveCombinations.getPossiblePieceCombinations(moveList);
        for (List<List<Move>> pieces : MoveCombinations.combinations) {
            Board copyBoard = new Board(board);
            for (int j = 0; j < pieces.size(); j++) {
                List<Move> moves = pieces.get(j);
                List<Move> copyMove = new ArrayList<>(moves);
                for (Move move : moves) {
                    for (int i = 0; i < copyMove.size(); i++)
                        if (Structure.canMove(copyBoard, 'c', j, copyMove.get(i))) {
                            Structure.applyMove(copyBoard, j, copyMove.get(i), 'c');
                            copyMove.remove(i);
                            break;
                        }
                }
                if (copyMove.size() == 0) {
                    ExpectingNode expectingNode = new ExpectingNode(this, copyBoard);
                    children.add(expectingNode.getAverageEvaluation("min", depth - 1));
                }
            }
        }
        double max = Double.MIN_VALUE;
        for (var child : children) {
            max = Math.max(max, child.value);
        }
        return new Pair<>(this, max);
    }
}