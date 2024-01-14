import java.util.ArrayList;
import java.util.List;

public class MaximizingNode extends Node {
    List<Move> moveList;

    //This Node Will Get List<Move>, And Apply It On Every Pieces, Then Take The Max Evaluation Of Them
    public MaximizingNode(Node parent, Board board, List<Move> moveList) {
        super(parent, board);
        this.moveList = moveList;
    }

    Pair<Node, Double> getMaxEvaluation(int depth) {
        //Apply Moves On All Pieces, Resulting Expecting Nodes
        //Return The Max Evaluation Of Children With The Current Node
        if (Structure.isFinal(this)){
            if (Structure.isWinner('c', this)){
                return new Pair<>(this, Double.MAX_VALUE);
            }else{
                return new Pair<>(this, -Double.MAX_VALUE);
            }
        }
        if (depth == 0) return new Pair<>(this, Structure.evaluate(board));
//        Board ccopyBoard = new Board(board);
//        Structure.applyMove(ccopyBoard, 1, new Move(1), 'c');
//        Structure.applyMove(ccopyBoard, 1, new Move(10), 'c');
//
//        return new Pair<>(new Node(this, ccopyBoard), 1d);

        List<Pair<Node, Double>> children = new ArrayList<>();
        MoveCombinations.getPossiblePieceCombinations(moveList);
        for (List<List<Move>> pieces : MoveCombinations.combinations) {
            Board copyBoard = new Board(board);
            boolean smthWrong = false;
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
                if (copyMove.size() != 0) {
                    smthWrong = true;
                    break;
                }
            }
            if (!smthWrong) {
                ExpectingNode expectingNode = new ExpectingNode(this, copyBoard);
                Pair<Node, Double> pair = expectingNode.getAverageEvaluation("min", depth - 1);
                children.add(new Pair<>(expectingNode, pair.value));
            }
        }
        Pair<Node, Double> max = new Pair<>(this, -Double.MAX_VALUE);
        System.out.println(-Double.MAX_VALUE);
        for (Pair<Node, Double> child : children) {
            System.out.println(max.value);
            System.out.println(child.value);
            if (max.value < child.value) {
                max = child;
            }
        }
        return max;
    }
}
