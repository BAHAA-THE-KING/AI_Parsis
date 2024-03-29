import java.util.*;

import java.util.List;

public class Structure {

    public static String getSymbol(char player, int piece) {

        if (player == 'h') {
            switch (piece) {
                case 0:
                    return "➀";
                case 1:
                    return "➁";
                case 2:
                    return "➂";
                case 3:
                    return "➃";
            }
        } else {
            switch (piece) {
                case 0:
                    return "❶";
                case 1:
                    return "❷";
                case 2:
                    return "❸";
                case 3:
                    return "❹";
            }
        }
        return "you entered a wrong piece number";
    }

    public static String[][] Board2array(Board b) {
        String[][] arr = new String[21][21];
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {

                //playable cell
                if ((i > 8 && i < 12) || (j > 8 && j < 12)) {
                    if (j == 7 || j == 11) arr[i][j] = " ___ ";
                        //starting cells
                    else if (j == 10 && i == 7) arr[i][j] = " ⬆⬆ |";
                    else if (j == 10 && i == 13) arr[i][j] = " ⬇⬇ |";
                    else arr[i][j] = " __ |";
                }

                //non-playable = white spaces
                else arr[i][j] = "     ";
                //horizontal line
                if (i == 8 || i == 12) arr[i][j] = "—————";
                //vertical line
                if (j == 8 || j == 12) arr[i][j] = "|";
            }
        }

        //Add safe places
        for (int i = 0; i < 8; i++) {
            Position pos = new Position(Board.safeCells[i]);
            fixPosition(pos);
            if (pos.x != 11) arr[pos.y][pos.x] = "_><_|";
            else arr[pos.y][pos.x] = " _><_";
        }

        //Add Human pieces
        Map<Integer, String> pieces = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            int id = b.piecesHuman[i];

            //if the piece is out of the board
            if (id < 0) {
                arr[17 + (i / 2)][15 + (i % 2)] = "  " + getSymbol('h', i) + "  ";
            }
            //the piece is on board
            else {

                int newCell = Board.pathHuman[id];

                //piece alone on cell
                if (!pieces.containsKey(newCell)) {

                    Position pos = new Position(newCell);
                    fixPosition(pos);
                    StringBuilder str;
                    if (pos.x == 7 || pos.x == 11) str = new StringBuilder(" ___ ");
                    else str = new StringBuilder(" __ |");

                    str.replace(i, i + 1, getSymbol('h', i));
                    pieces.put(newCell, str.toString());
                }
                //piece with other pieces on cell
                else {
                    String old = pieces.get(newCell);
                    String symb = getSymbol('h', i);

                    StringBuilder str = new StringBuilder(old);
                    str.replace(i, i + 1, symb);

                    pieces.replace(newCell, str.toString());
                }
            }
        }

        //Add Computer Pieces
        for (int i = 0; i < 4; i++) {
            int id = b.piecesComputer[i];

            //if the piece is out of the board
            if (id < 0) {
                arr[3 + (i / 2)][15 + (i % 2)] = "  " + getSymbol('c', i) + "  ";
            }
            //the piece is on board
            else {
                int newCell = Board.pathComputer[id];

                //piece alone on cell
                if (!pieces.containsKey(newCell)) {
                    Position pos = new Position(newCell);
                    fixPosition(pos);
                    StringBuilder str;
                    if (pos.x == 7 || pos.x == 11) str = new StringBuilder(" ___ ");
                    else str = new StringBuilder(" __ |");

                    str.replace(i, i + 1, getSymbol('c', i));
                    pieces.put(newCell, str.toString());
                }
                //piece with other pieces on cell
                else {
                    String old = pieces.get(newCell);
                    String symb = getSymbol('c', i);

                    StringBuilder str = new StringBuilder(old);
                    str.replace(i, i + 1, symb);

                    pieces.replace(newCell, str.toString());
                }
            }
        }

        //Draw pieces on Board
        for (Map.Entry<Integer, String> entry : pieces.entrySet()) {
            Position p = new Position(entry.getKey());

            fixPosition(p);
            arr[p.y][p.x] = entry.getValue();
        }


        return arr;
    }

    //fix the position for Board Print
    static void fixPosition(Position p) {
        if (p.y >= 11) p.y += 2;
        else if (p.y >= 8) p.y++;

        if (p.x >= 11) p.x += 2;
        else if (p.x >= 8) p.x++;
    }

    static void print(Board board) {

        String[][] arr = Structure.Board2array(board);

        System.out.println("\n" + "                                         " + "_______________");
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
        System.out.println("                                         " + "———————————————");
    }


    static void applyMove(Board board, int pieceIndex, Move move, char player) {
        if (player == 'c') {
            board.piecesComputer[pieceIndex] += move.steps;
            //kill enemy piece if in the same index
            int computer = Board.pathComputer[board.piecesComputer[pieceIndex]];
            for (int i = 0; i < 4; i++) {
                int pathIndex = board.piecesHuman[i];
                if (pathIndex < 0) continue;
                int human = Board.pathHuman[pathIndex];
                if (human == computer) board.piecesHuman[i] = -1;
            }
        } else {
            board.piecesHuman[pieceIndex] += move.steps;
            //kill enemy piece if in the same index
            int human = Board.pathHuman[board.piecesHuman[pieceIndex]];
            for (int i = 0; i < 4; i++) {
                int pathIndex = board.piecesComputer[i];
                if (pathIndex < 0) continue;
                int computer = Board.pathComputer[pathIndex];
                if (human == computer) board.piecesComputer[i] = -1;
            }
        }
    }

    static boolean outOfBounds(Board board, char player, int pieceIndex, Move move) {
        int[] PlayerPieces = (player == Board.C) ? board.piecesComputer : board.piecesHuman;
        int pathIndex = PlayerPieces[pieceIndex];
        int nextPathIndex = pathIndex + move.steps;
        //check if move is bigger than remaining steps
        //out of bounds
        return nextPathIndex > 83;
    }

    static boolean canMove(Board board, char player, int pieceIndex, Move move) {
        int[] pathEnemy;
        int[] pathPlayer;
        int[] PlayerPieces = (player == Board.C) ? board.piecesComputer : board.piecesHuman;

        int pathIndex = PlayerPieces[pieceIndex];
        int nextPathIndex = pathIndex + move.steps;

        //if the piece is out of the board and the move is not a khal
        if (pathIndex < 0 && !move.isKhal()) {
            return false;
        }
        //check if move is bigger than remaining steps
        if (nextPathIndex > 83) {
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
                if (piece >= 0 && pathEnemy[piece] == block_id) return false;
            }
        }

        //move is valid
        return true;
    }

    static List<Move> throwShells(int count) {
        if (count <= 0) return new ArrayList<>();
        List<Move> moves = new ArrayList<>();
        int ones = 0;
        for (int i = 0; i < 6; i++) {
            double possible = Math.random();
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

        if (moves.get(moves.size() - 1).steps == 6 || moves.get(moves.size() - 1).steps == 12 || moves.get(moves.size() - 1).steps == 10 || moves.get(moves.size() - 1).steps == 25) {
            count--;
            moves.addAll(throwShells(count));
        }
        return moves;
    }

    static boolean isFinal(Node node) {
        //Tested and working
        return isWinner('c', node) || isWinner('h', node);
    }

    static boolean isWinner(char player, Node node) {
        //Tested and working
        Board board = node.board;
        int[] pieces = player == 'h' ? board.piecesHuman : board.piecesComputer;
        int[] path = player == 'h' ? Board.pathHuman : Board.pathComputer;
        int finishLineId = player == 'h' ? 199 : 161;
        for (int piece : pieces) {
            if (piece == -1 || path[piece] != finishLineId) {
                return false;
            }
        }
        return true;
    }

    static double evaluate(Board board, char player) {
        float value = 0;
        //Steps Moved
        for (int posIndex : board.piecesComputer) {
            if (posIndex == -1) {
                value -= 10;
            } else {
                value += posIndex + 1;
                //is it in a safe place?
                if (board.isSafe(posIndex)) value += 10;
            }
        }
        for (int posIndex : board.piecesHuman) {
            if (posIndex == -1) {
                value += 10;
            } else {
                value -= posIndex + 1;
                //is it in a safe place?
                if (board.isSafe(posIndex)) value -= 10;
            }
        }
        //Is Someone Behind You ?
        for (int posIndexC : board.piecesComputer) {
            for (int posIndexH : board.piecesHuman) {
                if (posIndexC == -1 || posIndexH == -1) continue;
                int diff = posIndexH - posIndexC;
                if (diff == 1 || diff == 2 || diff == 3 || diff == 4 || diff == 6 || diff == 10 || diff == 11 || diff == 12 || diff == 25 || diff == 26)
                    value += 15;
                else if (diff == -1 || diff == -2 || diff == -3 || diff == -4 || diff == -6 || diff == -10 || diff == -11 || diff == -12 || diff == -25 || diff == -26)
                    value -= 15;
            }
        }
        if (player == 'h') value = -value;
        return value;
    }
}
