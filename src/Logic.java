
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


//        System.out.println("Initial...");
//        Structure.print(initialBoard);
//        List<Move> moves = new ArrayList<>();
//        moves.add(new Move(10,"ten",1));
//        moves.add(new Move(1,"khal",1));
//        List<Node> nextStates = Structure.getNextStates(root,moves,'c');
//        for(Node node:nextStates){
//            System.out.println("--------------------------------------------------");
//            Structure.print(node.board);
//        }
    }

    public static void humanTurn(Node node , Scanner scanner) {
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
            if(selectedMove >= humanMoves.size()){
                System.out.println("wrong input, try again.");
                continue;
            }

            Move humanSelectedMove = humanMoves.get(selectedMove);

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
            if(selectedPiece >= validPieces.size()){
                System.out.println("wrong input, try again.");
                continue;
            }

            humanMoves.remove(selectedMove);
            Structure.applyMove(node.board , validPieces.get(selectedPiece) , humanSelectedMove , 'h');

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
