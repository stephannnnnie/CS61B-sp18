package byog.Core;

import byog.TileEngine.TETile;

public class Pool {
    private Position player;
    private TETile[][] world;

    public Pool(Position p, TETile[][] w){
        player = p;
        world = w;
    }

    public Position getPlayer(){
        return player;
    }

    public TETile[][] getWorld(){
        return world;
    }
}
