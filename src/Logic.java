import java.util.ArrayList;
import java.util.List;

public class Logic {
    public static void main(String[] args) {
        Board initialBoard = new Board();
        Node root = new Node(null, initialBoard);
        humanTurn(root);
        System.out.println("Initial...");
        Structure.Board2array(initialBoard);
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(10,"ten",1));
        moves.add(new Move(1,"khal",1));
        List<Node> nextStates = Structure.getNextStates(root,moves,'c');
        for(Node node:nextStates){
            System.out.println("--------------------------------------------------");
            Structure.Board2array(node.board);
        }
    }

    public static void humanTurn(Node node) {
        //TODO fill me
        //computerTurn(node);
    }

    public static void computerTurn(Node node) {
        //TODO fill me
        //expectiMiniMax();
        //humanTurn(node);
    }

    public static void expectiMiniMax() {
    }
}
