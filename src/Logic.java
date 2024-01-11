
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Logic {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board initialBoard = new Board();
        Node root = new Node(null, initialBoard);
        Structure.print(initialBoard);
        humanTurn(root , scanner);


    }

    public static void humanTurn(Node node , Scanner scanner) {
        System.out.println("It's your turn");
        List<Move> humanMoves = Structure.throwShells();
        while (!humanMoves.isEmpty()){
            Move humanSelectedMove;
            List<String> humanLifePieces = new ArrayList<>();
            do {
                System.out.println("Yor moves : " + humanMoves);
                System.out.print("Select move to play : ");
                int selectedMove = scanner.nextInt() - 1;
                humanSelectedMove = humanMoves.get(selectedMove);
                humanMoves.remove(selectedMove);
                humanLifePieces = new ArrayList<>();
                for(int i = 0 ; i < node.board.piecesHuman.length ; i++){
                    if(Structure.canMove(node.board , 'h' , i, humanSelectedMove)){
                        humanLifePieces.add(Structure.getSymbol('h' , i)) ;
                    }
                }
                if(humanLifePieces.isEmpty()) System.out.println("You don't have any pieces can move [ "+humanSelectedMove+" ]");
            }while (humanLifePieces.isEmpty());
            System.out.println("Your pieces can move [ "+humanSelectedMove+" ]: "+humanLifePieces);
            System.out.print("Choose piece to move : ");
            int selectedPiece = scanner.nextInt() - 1;
            node.board= Structure.applyMove(node.board , selectedPiece , humanSelectedMove , 'h');
            Structure.print(node.board);
        }
        System.out.println("Your turn end");
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
