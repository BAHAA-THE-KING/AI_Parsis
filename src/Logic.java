import java.util.List;

public class Logic {
    public static void main(String[] args) {
        Board initialBoard = new Board();
        Node root = new Node(null, initialBoard);
        humanTurn(root);
        System.out.println(Structure.canMove(initialBoard,'c',0,new Move(24,"Duaq",0.9)));
//        Structure.Board2array(initialBoard);
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
