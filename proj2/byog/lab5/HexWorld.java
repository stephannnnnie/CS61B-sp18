package byog.lab5;
import org.junit.Test;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 50;

    private static final Random random = new Random();
    public static void addHexagon(TETile[][] world, Position position, int s, TETile t){
        if (s<2){
            throw new IllegalArgumentException("size is not permitted");
        }
        for (int j = 0;j<2*s;j++){
            int thisrowy = position.y+j;
            int xrowstart = position.x+hexRowOffset(s,j);
            Position rowstartp = new Position(xrowstart,thisrowy);
            int rowWidth = hexRowWidth(s,j);
            addRow(world,rowstartp,rowWidth,t);
        }
    }

    /**
     * Computes the width of row i for a size s hexagon.
     * @param s The size of the hex.
     * @param i The row number where i = 0 is the bottom row.
     * @return
     */
    public static int hexRowWidth(int s, int i){
        int actual = i;
        if (i>=s){
            actual = 2*s-actual-1;
        }
        return s+2*actual;
    }
    /**
     * Computes relative x coordinate of the leftmost tile in the ith
     * row of a hexagon, assuming that the bottom row has an x-coordinate
     * of zero.
     * @param s size of the hexagon
     * @param i row num of the hexagon, where i = 0 is the bottom
     * @return
     */
    public static int hexRowOffset(int s, int i){
        int actual = i;
        if (i>=s){
            actual = 2*s-actual-1;
        }
        return -actual;
    }

    private static void addRow(TETile[][] world,Position p, int s,TETile t) {
//        for (int j = y;j<y+s-i-1;j++){
//            world[i][j] = Tileset.NOTHING;
//        }
//        for (int j = x+s-1-i;j<x+2*s+i-1;j++){
//            world[j][i] = Tileset.NORM;
//        }
//        for (int j = y+2*s+i-1;j<y+3*s-2;j++){
//            world[i][j] = Tileset.NOTHING;
//        }
        for (int i = 0;i<s;i++){
            int x = p.x+i;
            int y = p.y;
            world[x][y] = TETile.colorVariant(t,32,32,32,random);
        }
    }

//    @Test
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Position position = new Position(10,10);
        TETile teTile = Tileset.NORM;
        addHexagon(world,position,3,teTile);
        ter.renderFrame(world);
    }
}
