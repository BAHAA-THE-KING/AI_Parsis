import java.util.*;

import java.util.List;

public class Structure {

    private static String getSymbol(char player, int piece){

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
                    if((i == 7 || i == 11) && (j == 7 || j == 11))
                        arr[i][j] = "     ";
                    else if(i == 7 || i == 11)
                        arr[i][j] = "    |";
                    else if(j == 7 || j == 11)
                        arr[i][j] = " ___ ";
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

        //Add Human pieces
        Map<Integer, String> pieces = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            int id = b.piecesHuman[i];
            int newCell = Board.pathHuman[id];
            if(!pieces.containsKey(newCell)){
                StringBuilder str = new StringBuilder("____|");
                str.replace(i,i+1, getSymbol('h', i));
                pieces.put(newCell, str.toString());
            }
            else {
                String old = pieces.get(newCell);
                String symb = getSymbol('h', i);

                StringBuilder str = new StringBuilder(old);
                str.replace(i,i+1, symb);

                pieces.replace(newCell, str.toString());
            }
        }

        //Add Computer Pieces
        for (int i = 0; i < 4; i++) {
            int id = b.piecesComputer[i];
            int newCell = Board.pathComputer[id];
            if(!pieces.containsKey(newCell)){
                StringBuilder str = new StringBuilder("____|");
                str.replace(i,i+1, getSymbol('c', i));
                pieces.put(newCell, str.toString());
            }
            else {
                String old = pieces.get(newCell);
                String symb = getSymbol('c', i);

                StringBuilder str = new StringBuilder(old);
                str.replace(i,i+1, symb);

                pieces.replace(newCell, str.toString());
            }
        }

        //Draw pieces on Board
        for(Map.Entry<Integer, String> entry : pieces.entrySet()){
            Position p = new Position(entry.getKey());
            if(p.y >= 12 )
                p.y+=2;
            else if(p.y >= 8)
                p.y++;

            if(p.x >= 12)
                p.x+=2;
            else if(p.x >= 8)
                p.x++;

            arr[p.y][p.x] = entry.getValue();
        }


        System.out.println("\n");
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
        return arr;
    }

    static void print(Board board) {
        char[][] chars = new char[19][19];
        for (int id : Board.pathComputer) {
            Position pos = new Position(id);
            chars[pos.y][pos.x] = 'o';
        }
        for (int id : Board.pathHuman) {
            Position pos = new Position(id);
            chars[pos.y][pos.x] = 'o';
        }
        for (int id : Board.safeCells) {
            Position pos = new Position(id);
            chars[pos.y][pos.x] = 'X';
        }
        for (int id : board.piecesComputer) {
            Position pos = new Position(id);
            if(pos.y > 0 && pos.x > 0)
                chars[pos.y][pos.x] = 'C';
        }
        for (int id : board.piecesHuman) {
            Position pos = new Position(id);
            if(pos.y > 0 && pos.x > 0)
                chars[pos.y][pos.x] = 'H';
        }
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                System.out.print((chars[i][j] == '\u0000') ? ' ' : chars[i][j]);
            }
            System.out.println("");
        }
    }

    List<Node> getNextStates(Node node, List<Move> moves, char player) {
        Board board = node.board;
        int[]pieces = player=='h'?board.piecesHuman:board.piecesComputer;
        List<Node> newNodes=new ArrayList<>();
        for(Move move:moves){
            for(int pieceIndex=0;pieceIndex<4;pieceIndex++){
                int piece = pieces[pieceIndex];
                if(!move.isKhal() && piece==-1){
                    //piece not in map, and the move is not a khal
                    continue;
                }
                if(canMove(board,player,pieceIndex,move)){
                    Board copyBoard=applyMove(board,pieceIndex,move,player);
                    newNodes.add(new Node(node,copyBoard));
                }
            }
        }
        for(Move move:moves.reversed()){
            for(int pieceIndex=0;pieceIndex<4;pieceIndex++){
                int piece = pieces[pieceIndex];
                if(!move.isKhal() && piece==-1){
                    //piece not in map, and the move is not a khal
                    continue;
                }
                if(canMove(board,player,pieceIndex,move)){
                    Board copyBoard=applyMove(board,pieceIndex,move,player);
                    newNodes.add(new Node(node,copyBoard));
                }
            }
        }
        return newNodes;
    }

     public static Board applyMove(Board board,int pieceIndex,Move move,char player) {
        Board copyBoard = new Board(board);
        if(player=='c'){
            copyBoard.piecesComputer[pieceIndex]+=move.steps;
        }else{
            copyBoard.piecesHuman[pieceIndex]+=move.steps;
        }
        return copyBoard;
    }
    public boolean canMove(Board board,char player,int pieceIndex,Move move){
        int[] path = (player==Board.H)?Board.pathComputer:Board.pathHuman;
        int[] pieces = (player==Board.H)?board.piecesComputer:board.piecesHuman;
        int pathIndex= pieces[pieceIndex];
        int nextPathIndex=pathIndex+move.steps;
        if(nextPathIndex>83){
            //out of bounds
            return false;
        }
        int block_id = path[nextPathIndex];
        if(board.isSafe(block_id)){
            for(int piece:pieces){
                if(path[piece]==block_id)
                    return false;
            }
        }
        return true;
    }
    public static List<Move> throwShells() {
        List<Move> moves = new ArrayList<>();
        int ones = 0;
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int possible = random.nextInt(100) + 1;
            if (possible <= 40) {
                ones++;
            }
        }
        if (ones == 1) {
            moves.add(new Move(10, "dest", 1));
            moves.add(new Move(1, "khal", 1));
        } else if (ones == 2) {
            moves.add(new Move(2, "dua", 1));
        } else if (ones == 3) {
            moves.add(new Move(3, "three", 1));
        } else if (ones == 4) {
            moves.add(new Move(4, "four", 1));
        } else if (ones == 5) {
            moves.add(new Move(25, "bnj", 1));
            moves.add(new Move(1, "khal", 1));
        } else if (ones == 0) {
            moves.add(new Move(6, "shaka", 1));
        } else if (ones == 6) {
            moves.add(new Move(12, "bara", 1));
        }
        return moves;

    }

    public static boolean isFinal(Node node){
        return isWinner('c',node)||isWinner('h',node);
    }

    public static boolean isWinner(char player,Node node){
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
}
