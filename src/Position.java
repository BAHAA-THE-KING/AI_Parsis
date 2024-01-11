import java.util.Objects;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(int id){
        this.x = id%19;
        this.y = id/19;
    }


    public static boolean isEqual(Position p1 , Position p2) {
        if(p1.x == p2.x && p1.y == p2.y) return true;
        return false;
    }



    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
