import java.util.ArrayList;
import java.util.List;

public class MinimizingNode extends Node {
    List<Move> moveList;

    //This Node Will Get List<Move>, And Apply It On Every Pieces, Then Take The Min Evaluation Of Them
    public MinimizingNode(Node parent, Board board, List<Move> moveList) {
        super(parent, board);
        this.moveList = moveList;
    }

    Pair<MinimizingNode, Double> getMinEvaluation(int depth) {
        //Apply Moves On All Pieces, Resulting Expecting Nodes
        //Return The Min Evaluation Of Children With The Current Node
        if (depth==0)return new Pair<>(this, Structure.evaluate(board));
        List<Pair<Node, Double>> children = new ArrayList<>();
        MoveCombinations.getPossiblePieceCombinations(moveList);
        for (List<List<Move>> pieces : MoveCombinations.combinations) {
            Board copyBoard = new Board(board);
            for (int j = 0; j < pieces.size(); j++) {
                List<Move> moves = pieces.get(j);
                List<Move> copyMove = new ArrayList<>(moves);
                for (Move move : moves) {
                    for (int i = 0; i < copyMove.size(); i++)
                        if (Structure.canMove(copyBoard, 'h', j, copyMove.get(i))) {
                            Structure.applyMove(copyBoard, j, copyMove.get(i), 'h');
                            copyMove.remove(i);
                            break;
                        }
                }
                if (copyMove.size() == 0) {
                    ExpectingNode expectingNode = new ExpectingNode(this, copyBoard);
                    children.add(expectingNode.getAverageEvaluation("max", depth-1));
                }
            }
        }

        double min = Double.MAX_VALUE;
        for (var child : children) {
            min = Math.min(min, child.value);
        }
        return new Pair<>(this, min);
    }
}
