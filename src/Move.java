public class Move {
    int steps;
    String name;
    double prob;

    public Move(int steps, String name, double prob) {
        this.steps = steps;
        this.name = name;
        this.prob = prob;
    }
    public boolean isKhal() {
        return steps == 1;
    }
    @Override
    public String toString() {
        return name;
    }
}