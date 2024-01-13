import java.math.BigInteger;
import java.util.*;

public class MoveCombinations {

    static HashMap<String, Pair<List<Move>, Double>> allMoves = new HashMap<>();

    static {
        int[] numbers = {2, 3, 4, 0, 1, 5, 6};
//        generateAllMovesCombinations(new ArrayList<>(), numbers, false, 10);
    }

    static void generateAllMovesCombinations(List<Integer> current, int[] numbers, boolean finished, int count) {
        if (count == 0 || finished) {
            List<Integer> res = new ArrayList<>(current);
            res.sort(Comparator.comparingInt(o -> o));
            List<Move> listAsMoves = new ArrayList<>();
            for (Integer num : res) {
                listAsMoves.add(new Move(num));
            }
            //TODO Calculate This Probability (Instead Of 1.0)
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

    static List<List<List<Move>>> combinations = new ArrayList<>();
    static List<List<Integer>> temp = new ArrayList<>();
    static Set<String> set = new HashSet<>();

    static void getPossiblePieceCombinations(List<Move> moves) {
        combinations = new ArrayList<>();
        temp = new ArrayList<>();
        set.clear();

        HashMap<Integer, Integer> movesCount = new HashMap<>();
        for (Move move : moves) {
            if (!movesCount.containsKey(move.steps)) movesCount.put(move.steps, 0);
            movesCount.put(move.steps, movesCount.get(move.steps) + 1);
        }
        boolean flag = true;
        for (Map.Entry<Integer, Integer> entry : movesCount.entrySet()) {
            if (entry.getValue() == 0) continue;
            temp = new ArrayList<>();
            set.clear();
            List<Integer> result = new ArrayList<>();
            result.add(0);
            result.add(0);
            result.add(0);
            result.add(0);
            generateCombinationsForOneMove(result, 4, entry.getValue());
            merge(entry.getKey(), flag);
            flag = false;
        }
    }

    static void merge(int defaultValue, boolean isInitial) {
        List<List<List<Move>>> copyCombinations = new ArrayList<>();
        for (List<Integer> comb : temp) {
            List<List<Move>> moveTemp = new ArrayList<>();
            for (Integer num : comb) {
                List<Move> moves = new ArrayList<>();
                for (int j = 0; j < num; j++) {
                    moves.add(new Move(defaultValue));
                }
                moveTemp.add(moves);
            }
            if (isInitial) {
                copyCombinations.add(moveTemp);
                continue;
            }
            for (List<List<Move>> pieces : combinations) {
                List<List<Move>> copyPieces = new ArrayList<>();
                for (int i = 0; i < pieces.size(); i++) {
                    List<Move> copyMoves = new ArrayList<>(pieces.get(i));
                    copyMoves.addAll(moveTemp.get(i));
                    copyPieces.add(copyMoves);
                }
                copyCombinations.add(copyPieces);
            }
        }
        combinations = copyCombinations;
    }


    static void generateCombinationsForOneMove(List<Integer> result, int pieceNum, int count) {
        if (count == 0) {
            if (!set.contains(result.toString())) {
                set.add(result.toString());
                temp.add(result);
            }
            return;
        }
        for (int i = 0; i < pieceNum; i++) {
            List<Integer> copyResult = new ArrayList<>(result);
            copyResult.set(i, copyResult.get(i) + 1);
            generateCombinationsForOneMove(copyResult, pieceNum, count - 1);
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
//        List<Integer> pieces = Arrays.asList(0, 1, 2, 3);
//        List<Integer> moves = generateMoves(10); // Replace 4 with the desired number of moves
//
//        List<Map<Integer, List<Integer>>> combinations = generateMoveCombinations(pieces, moves);
//
//        // Print the result
//        for (int i = 0; i < combinations.size(); i++) {
//            System.out.println("Case " + (i + 1) + ":");
//            Map<Integer, List<Integer>> combination = combinations.get(i);
//            for (Map.Entry<Integer, List<Integer>> entry : combination.entrySet()) {
//                System.out.println(entry.getKey() + ": be moved by " + String.join(",", "" + entry.getValue()));
//            }
//            System.out.println();
//        }
//        List<Move> moves = new ArrayList<>();
//        moves.add(new Move(1));
//        moves.add(new Move(1));
//        moves.add(new Move(1));
//        moves.add(new Move(1));
//        moves.add(new Move(1));
//        moves.add(new Move(1));
//        moves.add(new Move(1));
//        moves.add(new Move(1));
//        moves.add(new Move(1));
//        moves.add(new Move(1));
//        getPossiblePieceCombinations(moves);
//        System.out.println(combinations.size());
//        System.out.println(calcCombinations(10, 4)); // Example usage
    }

    static List<Integer> generateMoves(int numMoves) {
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < numMoves; i++) {
            moves.add(i);
        }
        return moves;
    }


    static List<Map<Integer, List<Integer>>> generate(int movesNum) {
        List<Integer> pieces = Arrays.asList(0, 1, 2, 3);
        List<Integer> moves = generateMoves(movesNum);
        return generateMoveCombinations(pieces, moves);
    }

    public static BigInteger calcCombinations(int n, int m) {
        BigInteger numerator = factorial(n + m - 1);
        BigInteger denominator = factorial(m - 1).multiply(factorial(n));
        return numerator.divide(denominator);
    }

    private static BigInteger factorial(int num) {
        BigInteger fact = BigInteger.ONE;
        for (int i = 2; i <= num; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }
}
