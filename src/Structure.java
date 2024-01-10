import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Structure {
    void print(Board board) {
        //TODO fill me
    }

    void getNextStates(Board board) {
        //TODO fill me
    }

    void applyMove(Board board) {
        //TODO fill me
    }

    void canMove(Board board) {

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
}
