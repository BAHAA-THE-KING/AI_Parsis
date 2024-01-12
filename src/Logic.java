
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Logic {
    public static void main(String[] args) {
        Board initialBoard = new Board();
        Node root = new Node(null, initialBoard);
        Structure.print(initialBoard);
        humanTurn(root);


        System.out.println("Initial...");
        Structure.print(initialBoard);
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(10,"ten",1));
        moves.add(new Move(1,"khal",1));
        List<Node> nextStates = Structure.getNextStates(root,moves,'c');
        for(Node node:nextStates){
            System.out.println("--------------------------------------------------");
            Structure.print(node.board);
        }
    }

    static Scanner scanner=new Scanner(System.in);

    public static void humanTurn(Node node) {
        System.out.println("It's your turn");
        List<Move> humanMoves = Structure.throwShells();
//        List<Move> humanMoves = new ArrayList<>();
//        humanMoves.add(new Move(2 , "dua" , 1));
//        humanMoves.add(new Move(3 , "three" , 1));

        while (!humanMoves.isEmpty()){

            //Choosing a move
            System.out.println("Yor moves :");
            int k = 1;
            for(Move mv : humanMoves){
                System.out.println(k + " - [" + mv + "]");
                k++;
            }
            System.out.print("Select a move to play : ");

            int selectedMove = scanner.nextInt() - 1;
            Move humanSelectedMove = humanMoves.get(selectedMove);
            humanMoves.remove(selectedMove);

            //checking valid pieces
            List<Integer> validPieces = new ArrayList<>();

            for(int i = 0 ; i < node.board.piecesHuman.length ; i++){
                if(Structure.canMove(node.board , 'h' , i, humanSelectedMove)){
                    validPieces.add(i) ;
                }
            }

            //if no valid pieces
            if(validPieces.isEmpty()){
                System.out.println("you can't move any piece with the selected move");
                continue;
            };

            //choosing a piece
            System.out.println("The pieces you can move with [ "+humanSelectedMove+" ] :");
            k = 1;
            for(Integer piece : validPieces){
                System.out.println(k + " - [" + Structure.getSymbol('h', piece) + "]");
                k++;
            }
            System.out.print("Choose a piece to move : ");

            int selectedPiece = scanner.nextInt() - 1;
            Structure.applyMove(node.board , validPieces.get(selectedPiece) , humanSelectedMove , 'h');

            Structure.print(node.board);

        }

        System.out.println("Your turn end");
        computerTurn(node);
    }

    public static void computerTurn(Node node) {
        List<Move> moves = Structure.throwShells();
        Node nextNode = new MaximizingNode(node, node.board, moves).getMaxEvaluation().key;
        Structure.print(nextNode.board);
        humanTurn(nextNode);
    }
}
