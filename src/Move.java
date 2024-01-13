public class Move {
    int steps;
    String name;
    double prob;

    public Move(int steps) {
        this.steps = steps;
        switch (steps) {
            case 2 -> {
                this.name = "dua";
                this.prob = 0.31104;
            }
            case 3 -> {
                this.name = "three";
                this.prob = 0.27648;
            }
            case 4 -> {
                this.name = "four";
                this.prob = 0.13824;
            }
            case 6 -> {
                this.name = "shaka";
                this.prob = 0.046656;
            }
            case 10 -> {
                this.name = "dest";
                this.prob = 0.186624;
            }
            case 12 -> {
                this.name = "bara";
                this.prob = 0.004096;
            }
            case 25 -> {
                this.name = "bnj";
                this.prob = 0.0384;
            }
        }
    }

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