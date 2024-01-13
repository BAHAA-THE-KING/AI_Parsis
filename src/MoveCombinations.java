import java.util.*;

public class MoveCombinations {

    static HashMap<String, Pair<List<Move>, Double>> allMoves = new HashMap<>();

    static {
        int[] numbers = {2, 3, 4, 0, 1, 5, 6};
        generateAllMovesCombinations(new ArrayList<>(), numbers, false, 10);
        System.out.println(allMoves.size());
    }

    static void generateAllMovesCombinations(List<Integer> current, int[] numbers, boolean finished, int count) {
        if (count == 0 || finished) {
            List<Integer> res = new ArrayList<>(current);
            res.sort(Comparator.comparingInt(o -> o));
            List<Move> listAsMoves = new ArrayList<>();
            for (Integer num : res) {
                listAsMoves.add(new Move(num));
            }
            //Calculate This Probability (Instead Of 1.0)
            allMoves.put(res.toString(), new Pair<>(listAsMoves, 1.0));
            return;
        }

        for (int i = 0; i < numbers.length; i++) {
            int number = numbers[i];
            current.add(number);
            generateAllMovesCombinations(current, numbers, i < 3, count - 1);
            current.remove(current.size() - 1);
        }
    }

    static List<Map<Integer, List<Integer>>> generateMoveCombinations(List<Integer> pieces, List<Integer> moves) {
        List<Map<Integer, List<Integer>>> result = new ArrayList<>();
        generateCombinations(result, pieces, new ArrayList<>(), new ArrayList<>(moves));
        return result;
    }

    static void generateCombinations(List<Map<Integer, List<Integer>>> result, List<Integer> pieces, List<Integer> currentCombination, List<Integer> remainingMoves) {
        if (remainingMoves.isEmpty()) {
            Map<Integer, List<Integer>> combination = new HashMap<>();
            for (Integer piece : pieces) {
                List<Integer> movesForPiece = new ArrayList<>();
                for (int j = 0; j < currentCombination.size(); j += 2) {
                    if (currentCombination.get(j).equals(piece)) {
                        movesForPiece.add(currentCombination.get(j + 1));
                    }
                }
                combination.put(piece, movesForPiece);
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
        List<Integer> moves = generateMoves(10); // Replace 4 with the desired number of moves

        List<Map<Integer, List<Integer>>> combinations = generateMoveCombinations(pieces, moves);

        // Print the result
        for (int i = 0; i < combinations.size(); i++) {
            System.out.println("Case " + (i + 1) + ":");
            Map<Integer, List<Integer>> combination = combinations.get(i);
            for (Map.Entry<Integer, List<Integer>> entry : combination.entrySet()) {
                System.out.println(entry.getKey() + ": be moved by " + String.join(",", "" + entry.getValue()));
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


    public static List<Map<Integer, List<Integer>>> generate(int movesNum) {
        List<Integer> pieces = Arrays.asList(0, 1, 2, 3);
        List<Integer> moves = generateMoves(movesNum);
        return generateMoveCombinations(pieces, moves);
    }
}
