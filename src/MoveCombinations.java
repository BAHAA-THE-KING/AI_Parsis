import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveCombinations {

    private static List<Map<Integer, List<Integer>>> generateMoveCombinations(List<Integer> pieces, List<Integer> moves) {
        List<Map<Integer, List<Integer>>> result = new ArrayList<>();
        generateCombinations(result, pieces, new ArrayList<>(), new ArrayList<>(moves));
        return result;
    }

    private static void generateCombinations(List<Map<Integer, List<Integer>>> result, List<Integer> pieces,
                                             List<Integer> currentCombination, List<Integer> remainingMoves) {
        if (remainingMoves.isEmpty()) {
            Map<Integer, List<Integer>> combination = new HashMap<>();
            for (int i = 0; i < pieces.size(); i++) {
                List<Integer> movesForPiece = new ArrayList<>();
                for (int j = 0; j < currentCombination.size(); j += 2) {
                    if (currentCombination.get(j).equals(pieces.get(i))) {
                        movesForPiece.add(currentCombination.get(j + 1));
                    }
                }
                combination.put(pieces.get(i), movesForPiece);
            }
            result.add(combination);
            return;
        }

        Integer move = remainingMoves.remove(0);

        for (Integer piece : pieces) {
            currentCombination.add(piece);
            currentCombination.add(move);
            generateCombinations(result, pieces, currentCombination, remainingMoves);
            currentCombination.remove(currentCombination.size() - 1);
            currentCombination.remove(currentCombination.size() - 1);
        }
        remainingMoves.add(0, move);
    }

    public static void main(String[] args) {
        List<Integer> pieces = Arrays.asList(0, 1, 2, 3);
        List<Integer> moves = generateMoves(2); // Replace 4 with the desired number of moves

        List<Map<Integer, List<Integer>>> combinations = generateMoveCombinations(pieces, moves);

        // Print the result
        for (int i = 0; i < combinations.size(); i++) {
            System.out.println("Case " + (i + 1) + ":");
            Map<Integer, List<Integer>> combination = combinations.get(i);
            for (Map.Entry<Integer, List<Integer>> entry : combination.entrySet()) {
                System.out.println(entry.getKey() + ": be moved by " + String.join(",", ""+entry.getValue()));
            }
            System.out.println();
        }
    }

    private static List<Integer> generateMoves(int numMoves) {
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < numMoves; i++) {
            moves.add(i);
        }
        return moves;
    }


    public static  List<Map<Integer, List<Integer>>> generate(int movesNum){
        List<Integer> pieces = Arrays.asList(0, 1, 2, 3);
        List<Integer> moves = generateMoves(movesNum);
        return generateMoveCombinations(pieces, moves);
    }
}
