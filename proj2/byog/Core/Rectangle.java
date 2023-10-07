package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Rectangle {
    int left;
    //左边界的坐标
    int right;
    //右边界的坐标
    int bottom;
    //下边界的坐标
    int top;
    //上边界的坐标
    public Rectangle(int left, int right, int bottom, int top) {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
    }

    public int getCenterX(){
        return (left+right)/2;
    }

    public int getLeft(){
        return left;
    }

    public int getBottom(){
        return bottom;
    }

    public int getCenterY(){
        return (bottom+top)/2;
    }

    public int getWidth(){
        return right-left;
    }

    public int getHeight(){
        return top-bottom;
    }

    //使用逆向思维：确定什么情况会永不重合
    public boolean isRectangleOverlap(Rectangle room) {
        //在算法题里先要判断是否是矩形，面积是否大于0
        int w = room.getWidth();
        int h = room.getHeight();
        if (w<=0||h<=0){
            return false;
        }
        //在以下情况之外都不会重合
        return !(left < room.right || right > room.left || top > room.bottom || bottom < room.top);
    }

    public static void makeRecRoom(TETile[][] world, Rectangle rec) {
        for (int i = rec.bottom;i< rec.top;i++){
            for (int j = rec.left;j< rec.right;j++){
                if (i == rec.bottom||i==rec.top-1||j == rec.left||j == rec.right-1){
                    world[j][i]= Tileset.WALL;
                }else{
                    world[j][i] =Tileset.FLOOR;
                }
            }
        }
    }
    public static void fillRoom(TETile[][] world, Rectangle rec){
        for (int i = rec.left+1;i<rec.right-1;i++){
            for (int j = rec.bottom+1;j<rec.top-1;j++){
                world[i][j]=Tileset.FLOOR;
            }
        }
    }

}
