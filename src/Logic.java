import java.util.List;

public class Logic {
    public static void main(String[] args) {
        Board initialBoard = new Board();
        Node root = new Node(null, initialBoard);
        humanTurn(root);
        List<Move> moves = Structure.throwShells();
        System.out.println(moves);
        Structure.print(initialBoard);
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
