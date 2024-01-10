import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import java.util.List;

public class Structure {
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
            if (id == -1) continue;
            Position pos = new Position(id);
            chars[pos.y][pos.x] = 'C';
        }
        for (int id : board.piecesHuman) {
            if (id == -1) continue;
            Position pos = new Position(id);
            chars[pos.y][pos.x] = 'H';
        }
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                System.out.print((chars[i][j] == '\u0000') ? ' ' : chars[i][j]);
            }
            System.out.println("");
        }
    }

    static List<Node> getNextStates(Node node, List<Move> moves, char player) {
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

    static Board applyMove(Board board, int pieceIndex, Move move, char player) {
        Board copyBoard = new Board(board);
        if (player == 'c') {
            copyBoard.piecesComputer[pieceIndex] += move.steps;
            for (int i = 0; i < 4; i++) {
                if (copyBoard.piecesHuman[i] == copyBoard.piecesComputer[pieceIndex]) {
                    copyBoard.piecesHuman[i] = -1;
                }
            }
        } else {
            copyBoard.piecesHuman[pieceIndex] += move.steps;
            for (int i = 0; i < 4; i++) {
                if (copyBoard.piecesComputer[i] == copyBoard.piecesHuman[pieceIndex]) {
                    copyBoard.piecesComputer[i] = -1;
                }
            }
        }
        return copyBoard;
    }
    static boolean canMove(Board board,char player,int pieceIndex,Move move){
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

    static boolean isFinal(Node node){
        return isWinner('c',node)||isWinner('h',node);
    }

    static boolean isWinner(char player,Node node){
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
