package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import static byog.Core.Game.SEED;


public class WorldGenerator {

    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    private static final int MAX_ROOM = 50;

    public static TETile[][] fillTiles(){
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        Random random = new Random(SEED+1000);
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        ArrayList<Rectangle> rooms = WorldGenerator.makeManyRooms(world,random.nextInt(MAX_ROOM)+16);
        //设定maxroom=50
        ArrayList<Path> pathArrayList = WorldGenerator.makeRoomsConnected(world,rooms);
        for (Rectangle existingRoom : rooms) {
            Rectangle.fillRoom(world,existingRoom);
        }
        for (Path path : pathArrayList) {
            Path.fillPath(path,world);
//            System.out.println(path.srcX+" "+path.srcY+" "+path.dstX+" "+path.dstY);
        }
        doorPlayer(world,rooms);
        return world;
    }

    public static ArrayList<Rectangle> makeManyRooms(TETile[][] world, int roomNum){
        int w,h,left,bottom,right,top;
        int maxw = 5, maxh = 4;
        ArrayList<Rectangle> existingRooms = new ArrayList<>();
        Random random = new Random(SEED+10);

        for (int i = 0;i<roomNum;i++){
            w = random.nextInt(maxw)+3;
            h = random.nextInt(maxh)+3;
            left = random.nextInt(WIDTH-w);
            right = left+w+1;
            bottom = random.nextInt(HEIGHT-h);
            top = bottom+h+1;
            Rectangle cur = new Rectangle(left,right,bottom,top);
            Rectangle.makeRecRoom(world,cur);
            existingRooms.add(cur);
        }
        return existingRooms;
    }

    public static ArrayList<Path> makeRoomsConnected(TETile[][] world,ArrayList<Rectangle> rooms){
        ArrayList<Path> paths = new ArrayList<>();
        Random random = new Random(SEED);
        Rectangle rr = rooms.remove(rooms.size()-1);
        //取出最后一个房间
        rooms.sort(Comparator.comparingInt(o->(o.left+o.bottom)));
        //按照和原点（左下角）的距离从小到大排列剩下的房间
//        int lx = random.nextInt(rr.getWidth()-2)+rr.left+1;
//        int ly = random.nextInt(rr.getHeight()-2)+rr.bottom+1;
        int lx = rr.getCenterX();
        int ly = rr.getCenterY();
        for (Rectangle r:rooms){
            int x = random.nextInt(r.getWidth()-2)+r.left+1;
            int y = random.nextInt(r.getHeight()-2)+r.bottom+1;
            Path newpath = makePathBetween2Floors(world,lx,ly,x,y);
            paths.add(newpath);
            lx = x;
            ly = y;
            //从前一个生成的通道结束处生成下一个通道的开头吗
        }
        return paths;
    }

    private static Path makePathBetween2Floors(TETile[][] world, int x1, int y1, int x2, int y2) {
        Random random = new Random(SEED+100);
        int ampY = Math.abs(y1-y2);
        int ampX = Math.abs(x1-x2);
//        int srcY,srcX,dstY,dstX,cx,cy;
        Position p1 = new Position(x1,y1);
        Position p2 = new Position(x2,y2);
        if (random.nextBoolean()){
            //draw vertical way first, 选取y值更小的room作为起点
            Position vStart = Position.smallerY(p1,p2);
            Position dst = Position.largerY(p1,p2);
            Position vCorner = Path.buildVerticalPath(world, vStart.getX(), vStart.getY(), ampY);
            Path path = new Path(vStart.getX(), vStart.getY(), dst.getX(), dst.getY(), 1, vCorner.getX(), vCorner.getY());

            Position hStart = Position.smallerX(vCorner,dst);
            Path.buildHorizontalPath(world, hStart.getX(), hStart.getY(), ampX);
            corner(world, vCorner.getX(), vCorner.getY());
            return path;

        }else{//draw horizontal way first,选取x值更小的room作为起点
            Position hStart = Position.smallerX(p1,p2);
            Position dst = Position.largerX(p1,p2);
            Position hCorner = Path.buildHorizontalPath(world, hStart.getX(), hStart.getY(), ampX);
            Path path = new Path(hStart.getX(), hStart.getY(), dst.getX(), dst.getY(), 0, hCorner.getX(),hCorner.getY());

            Position vStart = Position.smallerY(hCorner,dst);
            Path.buildVerticalPath(world, vStart.getX(), vStart.getY(), ampY);
            corner(world, hCorner.getX(), hCorner.getY());
            return path;
        }
    }


    private static void corner(TETile[][] world, int x,int y){
        world[x][y]= Tileset.FLOOR;
        for (int i = x-1;i<=x+1;i++){
            for (int j = y-1;j<=y+1;j++){
                world[i][j] = Tileset.WALL;

            }
        }
    }

    private static void player(TETile[][] world, ArrayList<Rectangle> rooms) {
        Random random = new Random(SEED);
        Rectangle picked = rooms.get(random.nextInt(rooms.size()));
        int x = picked.getLeft()+random.nextInt((picked.getWidth()-2)/2)+1;
        int y = picked.getBottom()+random.nextInt((picked.getHeight()-2)/2)+1;
        world[x][y] = Tileset.PLAYER;
    }

    private static void doorPlayer(TETile[][] world, ArrayList<Rectangle> rooms){
        Random random = new Random(SEED);
        Rectangle picked = rooms.get(random.nextInt(rooms.size()));

        Position door = new Position(picked.getLeft()+random.nextInt(picked.getWidth()-2)+1,picked.getBottom());
        if (world[door.getX()][door.getY()]==Tileset.WALL) {
            world[door.getX()][door.getY()] = Tileset.LOCKED_DOOR;
        }else{

        }

    }
}
