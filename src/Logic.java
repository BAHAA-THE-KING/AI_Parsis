import java.util.List;
import java.util.Scanner;

public class Logic {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board initialBoard = new Board();
        Node root = new Node(null, initialBoard);
        humanTurn(root);
        Structure.Board2array(initialBoard);
        List humanMoves = Structure.throwShells();
        System.out.println("Yor moves : " + humanMoves);
        System.out.println("\nSelect move to play : ");
        int humanSelectedMove = scanner.nextInt();

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
