package byog.Core;

import java.io.Serializable;

public class Position implements Serializable {
    private static final long serialVersionUID=2L;
    private int x;
    private int y;
    public Position(int xx, int yy){
        this.x = xx;
        this.y = yy;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static Position smallerX(Position p1,Position p2){
        if (p1.getX()<p2.getX()){
            return p1;
        }else {
            return p2;
        }
    }

    public static Position smallerY(Position p1,Position p2){
        if (p1.getY()<p2.getY()){
            return p1;
        }else {
            return p2;
        }
    }

    public static Position largerX(Position p1, Position p2) {
        if (p1.getX()< p2.getX()) {
            return p2;
        } else {
            return p1;
        }
    }

    public static Position largerY(Position p1, Position p2) {
        if (p1.getY() < p2.getY()) {
            return p2;
        } else {
            return p1;
        }
    }
}
