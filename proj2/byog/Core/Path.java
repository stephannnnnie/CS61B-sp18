package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class Path implements Serializable {
    private static final long serialVersionUID=4L;
    int srcX;
    int srcY;
    int dstX;
    int dstY;
    int flag;
    //先horizontal(0) or 先vertical(1);
    int cX;
    //corner x
    int cY;
    //corner y

    public Path(int srcX, int srcY, int dstX, int dstY, int flag, int cX, int cY) {
        this.srcX = srcX;
        this.srcY = srcY;
        this.dstX = dstX;
        this.dstY = dstY;
        this.flag = flag;
        this.cX = cX;
        this.cY = cY;
    }

    public Path(int srcX, int srcY, int dstX, int dstY, int flag) {
        this.srcX = srcX;
        this.srcY = srcY;
        this.dstX = dstX;
        this.dstY = dstY;
        this.flag = flag;
    }

    public void setcX(int cX) {
        this.cX = cX;
    }

    public void setcY(int cY) {
        this.cY = cY;
    }

    public static Position buildVerticalPath(TETile[][] world, int srcX, int srcY, int h) {
        for (int y = 0; y < h; y++) {
            world[srcX - 1][srcY + y] = Tileset.WALL;
            world[srcX + 1][srcY + y] = Tileset.WALL;
        }
        return new Position(srcX,srcY+h);
    }

    public static Position buildHorizontalPath(TETile[][] world, int srcX,int srcY,int w){
        for (int x = 0;x<w;x++){
            world[srcX + x][srcY - 1] = Tileset.WALL;
            world[srcX + x][srcY + 1] = Tileset.WALL;
        }
        return new Position(srcX+w,srcY);
    }

    private static void fillHorizontalPath(TETile[][] world, int srcX, int srcY, int w){
        for (int x = 0; x<w;x++){
            world[srcX+x][srcY]=Tileset.FLOOR;
        }
    }

    private static void fillVerticalPath(TETile[][] world, int srcX,int srcY, int h){
        for (int y = 0;y < h; y++){
            world[srcX][srcY+y]=Tileset.FLOOR;
        }
    }

    public static void fillPath(Path path, TETile[][] world){
        if (path.flag==0){
            fillHorizontalPath(world, path.srcX,path.srcY,Math.abs(path.cX-path.srcX)+1);
            if (path.cY<path.dstY){
                fillVerticalPath(world, path.cX,path.cY,Math.abs(path.cY-path.dstY)+1);
            }else{
                fillVerticalPath(world, path.dstX,path.dstY,Math.abs(path.cY-path.dstY)+1);
            }
        }else{
            fillVerticalPath(world, path.srcX,path.srcY,Math.abs(path.cY-path.srcY)+1);
            if (path.cX<path.dstX){
                fillHorizontalPath(world, path.cX,path.cY,Math.abs(path.cX-path.dstX)+1);
            }else{
                fillHorizontalPath(world, path.dstX,path.dstY,Math.abs(path.cX-path.dstX)+1);
            }
        }
    }
}
