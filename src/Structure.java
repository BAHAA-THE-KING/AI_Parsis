import java.util.*;

import java.util.List;

public class Structure {

    public static String getSymbol(char player, int piece){

        if(player == 'h'){
            switch (piece){
                case 0: return "➀";
                case 1: return "➁";
                case 2: return "➂";
                case 3: return "➃";
            }
        }
        else {
            switch (piece){
                case 0: return "❶";
                case 1: return "❷";
                case 2: return "❸";
                case 3: return "❹";
            }
        }
        return "you entered a wrong piece number";
    }
    public static String[][] Board2array(Board b){
        String[][] arr = new String[21][21];
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {

                //playable cell
                if((i > 8 && i < 12) || (j > 8 && j < 12)){
                    if(j == 7 || j == 11)
                        arr[i][j] = " ___ ";
                    //starting cells
                    else if(j == 10 && i == 7)
                        arr[i][j] = " ⏫⏫ |";
                    else if(j == 10 && i == 13)
                        arr[i][j] = " ⏬⏬ |";
                    else
                        arr[i][j] = " __ |";
                }

                //non-playable = white spaces
                else
                    arr[i][j] = "     ";
                //horizontal line
                if(i == 8 || i == 12)
                    arr[i][j] = "—————";
                //vertical line
                if(j == 8 || j == 12)
                    arr[i][j] = "|";
            }
        }

        //Add safe places
        for (int i = 0; i < 8; i++) {
            Position pos = new Position(Board.safeCells[i]);
            fixPosition(pos);
            if(pos.x != 11 )
                arr[pos.y][pos.x] = "_><_|";
            else
                arr[pos.y][pos.x] = " _><_";
        }

        //Add Human pieces
        Map<Integer, String> pieces = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            int id = b.piecesHuman[i];

            //if the piece is out of the board
            if(id < 0){
                arr[17+(i/2)][15+(i%2)] = "  " + getSymbol('h', i) + "  ";
            }
            //the piece is on board
            else{

                int newCell = Board.pathHuman[id];

                //piece alone on cell
                if(!pieces.containsKey(newCell)){

                    Position pos = new Position(newCell);
                    fixPosition(pos);
                    StringBuilder str;
                    if(pos.x == 7 || pos.x == 11)
                        str = new StringBuilder(" ___ ");
                    else
                        str = new StringBuilder(" __ |");

                    str.replace(i,i+1, getSymbol('h', i));
                    pieces.put(newCell, str.toString());
                }
                //piece with other pieces on cell
                else {
                    String old = pieces.get(newCell);
                    String symb = getSymbol('h', i);

                    StringBuilder str = new StringBuilder(old);
                    str.replace(i,i+1, symb);

                    pieces.replace(newCell, str.toString());
                }
            }
        }

        //Add Computer Pieces
        for (int i = 0; i < 4; i++) {
            int id = b.piecesComputer[i];

            //if the piece is out of the board
            if(id < 0){
                arr[3+(i/2)][15+(i%2)] = "  " + getSymbol('c', i) + "  ";
            }
            //the piece is on board
            else {
                int newCell = Board.pathComputer[id];

                //piece alone on cell
                if(!pieces.containsKey(newCell)){
                    Position pos = new Position(newCell);
                    fixPosition(pos);
                    StringBuilder str;
                    if(pos.x == 7 || pos.x == 11)
                        str = new StringBuilder(" ___ ");
                    else
                        str = new StringBuilder(" __ |");

                    str.replace(i,i+1, getSymbol('c', i));
                    pieces.put(newCell, str.toString());
                }
                //piece with other pieces on cell
                else {
                    String old = pieces.get(newCell);
                    String symb = getSymbol('c', i);

                    StringBuilder str = new StringBuilder(old);
                    str.replace(i,i+1, symb);

                    pieces.replace(newCell, str.toString());
                }
            }
        }

        //Draw pieces on Board
        for(Map.Entry<Integer, String> entry : pieces.entrySet()){
            Position p = new Position(entry.getKey());

            fixPosition(p);
            arr[p.y][p.x] = entry.getValue();
        }



        return arr;
    }

    //fix the position for Board Print
    static void fixPosition (Position p){
        if(p.y >= 11 )
            p.y+=2;
        else if(p.y >= 8)
            p.y++;

        if(p.x >= 11)
            p.x+=2;
        else if(p.x >= 8)
            p.x++;
    }

    static void print(Board board) {

        String arr[][] = Structure.Board2array(board);

        System.out.println("\n" +
                "                                         " +
                "_______________");
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
        System.out.println("                                         " +
                "———————————————");
    }

    static List<Node> getNextStates(Node node, List<Move> moves, char player) {
        Board board = node.board;
        int[]pieces = player=='h'?board.piecesHuman:board.piecesComputer;
        List<Node> newNodes=new ArrayList<>();
        List<Map<Integer,List<Integer>>> combinations = MoveCombinations.generate(moves.size());

        for(int i=0;i<combinations.size();i++){
            Board copyBoard = new Board(board);
            //iterate through combinations
            Map<Integer,List<Integer>> combinationMap=combinations.get(i);
            boolean validMove=true;
            for(int pieceIndex=0;pieceIndex<4&&validMove;pieceIndex++){
                //iterate through pieces
                List<Integer> pieceMoves = combinationMap.get(pieceIndex);
                for(int pieceMove:pieceMoves){
                    Move pickedMove = moves.get(pieceMove);
                    //check if move is applicable,if not skip this combination
                    if(canMove(copyBoard,player,pieceIndex,pickedMove)){
                        //apply the move on the piece on the board
                        applyMove(copyBoard,pieceIndex,pickedMove,player);
                    }else {
                        validMove=false;
                    }
                    if(!validMove)break;
                }
            }
            if(validMove){
                newNodes.add(new Node(node,copyBoard));
            }
        }
//
//        for(Move move:moves){
//            for(int pieceIndex=0;pieceIndex<4;pieceIndex++){
//                int piece = pieces[pieceIndex];
//                if(!move.isKhal() && piece==-1){
//                    //piece not in map, and the move is not a khal
//                    continue;
//                }
//                if(canMove(board,player,pieceIndex,move)){
//                    Board copyBoard=applyMove(board,pieceIndex,move,player);
//                    newNodes.add(new Node(node,copyBoard));
//                }
//            }
//        }


        return newNodes;
    }

    static void applyMove(Board board, int pieceIndex, Move move, char player) {
        if (player == 'c') {
            board.piecesComputer[pieceIndex] += move.steps;
            int computer = Board.pathComputer[board.piecesComputer[pieceIndex]];
            for (int i = 0; i < 4; i++) {
                int human = Board.pathHuman[board.piecesHuman[i]];
                if(human == computer)
                    board.piecesHuman[i] = -1;
            }
        } else {
            board.piecesHuman[pieceIndex] += move.steps;
            int human = Board.pathHuman[board.piecesHuman[pieceIndex]];
            for (int i = 0; i < 4; i++) {

                int pathIndex = board.piecesComputer[i];
                if(pathIndex < 0)
                    continue;

                int computer = Board.pathComputer[pathIndex];
                if(human == computer)
                    board.piecesComputer[i] = -1;
            }
        }
    }
    static boolean canMove(Board board,char player,int pieceIndex,Move move){
        int[] pathEnemy;
        int[] pathPlayer;
        int[] PlayerPieces = (player == Board.C) ? board.piecesComputer : board.piecesHuman;

        int pathIndex = PlayerPieces[pieceIndex];
        int nextPathIndex = pathIndex + move.steps;

        //if the piece is out of the board and the move is not a khal
        if(pathIndex < 0 && !move.isKhal()){
            return false;
        }
        //check if move is bigger than remaining steps
        if(nextPathIndex>83){
            //out of bounds
            return false;
        }

        //check if the next cell is a safe cell with enemy piece on it
        pathEnemy = (player == Board.H) ? Board.pathComputer : Board.pathHuman;
        pathPlayer = (player == Board.H) ? Board.pathHuman : Board.pathComputer;
        int[] EnemyPieces = (player == Board.H) ? board.piecesComputer : board.piecesHuman;

        int block_id = pathPlayer[nextPathIndex];
        if (board.isSafe(block_id)) {
            for (int piece : EnemyPieces) {
                if (piece >= 0 && pathEnemy[piece] == block_id)
                    return false;
            }
        }

        //move is valid
        return true;
    }
    static List<Move> throwShells() {
        List<Move> moves = new ArrayList<>();
        int ones = 0;
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            float possible = random.nextFloat();
            if (possible <= 0.4) {
                ones++;
            }
        }
        if (ones == 1) {
            moves.add(new Move(1));
            moves.add(new Move(10));
        } else if (ones == 2) {
            moves.add(new Move(2));
        } else if (ones == 3) {
            moves.add(new Move(3));
        } else if (ones == 4) {
            moves.add(new Move(4));
        } else if (ones == 5) {
            moves.add(new Move(1));
            moves.add(new Move(25));
        } else if (ones == 0) {
            moves.add(new Move(6));
        } else if (ones == 6) {
            moves.add(new Move(12));
        }

//        P(X=k)=(kn)×pk×(1−p)n−k
//
//        Probability of getting all zeros (000000):
//        P(X=6)=(66)×(0.6)6×(0.4)0P(X=6)=(66)×(0.6)6×(0.4)0 = 0.046656
//
//        Probability of getting five zeros and one (000001):
//        P(X=5)=(65)×(0.6)5×(0.4)1P(X=5)=(56)×(0.6)5×(0.4)1 = 0.186624
//
//        Probability of getting four zeros and two ones (000011):
//        P(X=4)=(64)×(0.6)4×(0.4)2P(X=4)=(46)×(0.6)4×(0.4)2 = 0.31104
//
//        Probability of getting three zeros and three ones (000111):
//        P(X=3)=c(6,3)×(0.6)3×(0.4)3P(X=3)=(36)×(0.6)3×(0.4)3 = 0.27648
//
//        Probability of getting two zeros and four ones (001111):
//        P(X=2)=(62)×(0.6)2×(0.4)4P(X=2)=(26)×(0.6)2×(0.4)4 = 0.13824
//
//        Probability of getting one zero and five ones (01111):
//        P(X=1)=(61)×(0.6)1×(0.4)5P(X=1)=(16)×(0.6)1×(0.4)5 = 0.036864
//
//        Probability of getting all ones (111111):
//        P(X=0)=(60)×(0.6)0×(0.4)6P(X=0)=(06)×(0.6)0×(0.4)6 = 0.004096

        return moves;

    }

    static boolean isFinal(Node node){
        //Tested and working
        return isWinner('c',node)||isWinner('h',node);
    }

    static boolean isWinner(char player,Node node){
        //Tested and working
        Board board = node.board;
        int[]pieces=player=='h'?board.piecesHuman:board.piecesComputer;
        int[]path=player=='h'?Board.pathHuman:Board.pathComputer;
        int finishLineId=player=='h'?199:161;
        for(int piece:pieces){
            if(path[piece]!=finishLineId){
                return false;
            }
        }
        return true;
    }

    static double evaluate(Board board) {
        float value = 0;
        //Steps Moved
        for (int posIndex : board.piecesComputer) {
            if (posIndex == -1) value -= 10;
            else value += posIndex + 1;
        }
        for (int posIndex : board.piecesHuman) {
            if (posIndex == -1) value += 10;
            else value -= posIndex + 1;
        }
        //Is Someone Behind You ?
        for (int posIndexC : board.piecesComputer) {
            for (int posIndexH : board.piecesHuman) {
                if (posIndexC == -1 || posIndexH == -1) continue;
                int diff = posIndexH - posIndexC;
                if (diff == 1 || diff == 2 || diff == 3 || diff == 4 || diff == 6 || diff == 10 || diff == 11 || diff == 12 || diff == 25 || diff == 26)
                    value += 10;
                else if (diff == -1 || diff == -2 || diff == -3 || diff == -4 || diff == -6 || diff == -10 || diff == -11 || diff == -12 || diff == -25 || diff == -26)
                    value -= 10;

            }
        }
        return value;
    }
}
