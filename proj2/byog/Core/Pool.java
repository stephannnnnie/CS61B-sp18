package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;

public class Pool implements Serializable {
    private static final long serialVersionUID=1L;
    private Position player;
    private TETile[][] world;

    public Pool(Position p, TETile[][] w){
        player = p;
        world = w;
    }

    public Pool() {
        world =  WorldGenerator.fillTiles().getWorld();
        player = WorldGenerator.fillTiles().getPlayer();
    }

    public Position getPlayer(){
        return player;
    }

    public TETile[][] getWorld(){
        return world;
    }
}
