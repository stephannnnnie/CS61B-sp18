package byog.Core;

import byog.SaveDemo.World;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import org.junit.Test;

import java.awt.*;
import java.io.*;
import java.util.Random;

/**
 * @author sheldon
 */
public class Game implements Serializable{
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    public static long SEED;

    private int steps;
    private boolean gameOver;
    private int health;
    private int blessing;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        setting();
        menu();
        steps=0;
        gameOver=false;
        health = 5;
        blessing=0;
        while (true) {
            String input="";
            if (!StdDraw.hasNextKeyTyped()) {
                menu();
                drawNote("press your choice please");
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            input+=key;
            if (key=='N'||key=='n'){
                StdDraw.clear(Color.black);
                drawNote("please enter a random seed and press S/s to finish");
                StringBuilder seed=new StringBuilder();
                char ch;
                while (true){
                    if (!StdDraw.hasNextKeyTyped()){
                        continue;
                    }
                    ch = StdDraw.nextKeyTyped();
                    if (ch=='s'||ch=='S'){
                        break;
                    }
                    seed.append(ch);
                    StdDraw.clear(Color.black);
                    drawNote("Your seed is:"+ seed);
                }
                SEED = Long.parseLong(String.valueOf(seed));
                StdDraw.pause(500);

                ter.initialize(WIDTH,HEIGHT);
                Pool newPool = WorldGenerator.fillTiles();
                ter.renderFrame(newPool.getWorld());
                play(newPool);
                break;

            }else if (key=='L'||key=='l'){
                gameOver=false;
                Pool oldPool = load();
                ter.initialize(WIDTH,HEIGHT);
                ter.renderFrame(oldPool.getWorld());
                play(oldPool);
                break;
            }else if (key=='q'||key=='Q'){
                gameOver=true;
                System.exit(0);
                break;
                //这里是还未进入游戏就退出了，所以不需要保存
            }
        }
    }

    public void setting(){
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

    }
    public void menu() {
        StdDraw.setPenColor(Color.white);
        Font titleFont = new Font("Georgia", Font.BOLD, 40);
        Font choiceFont = new Font("Georgia", Font.PLAIN,25);
        StdDraw.setFont(titleFont);
        StdDraw.text(WIDTH/2,3*HEIGHT/4,"~~CS61B: THE BEST GAME~~");
        StdDraw.setFont(choiceFont);
        StdDraw.text(WIDTH/2,HEIGHT/2,"New Game (N)");
        StdDraw.text(WIDTH/2,HEIGHT/2-2,"Load Game (L)");
        StdDraw.text(WIDTH/2,HEIGHT/2-4,"Quit (Q)");
        StdDraw.enableDoubleBuffering();
        StdDraw.show();

    }

    public void drawNote(String s){
//        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.yellow);
        Font font = new Font("Georgia", Font.PLAIN,25);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH/2,HEIGHT/2-8,s);
        StdDraw.show();
    }

    public void play(Pool pool){
        //考虑要线程吗
        while (!gameOver){
            StdDraw.enableDoubleBuffering();
            hud(pool);
            if (!StdDraw.hasNextKeyTyped()){
                continue;
            }
            char dir = StdDraw.nextKeyTyped();
            if (health==0){
                gameOver=true;
                StdDraw.clear(Color.BLACK);
                drawNote("***Game Over***");
                StdDraw.pause(6000);
                break;
            }else if (pool.getWorld()[pool.getPlayer().getX()][pool.getPlayer().getY()-1].equals(Tileset.LOCKED_DOOR)){
                gameOver=true;
                StdDraw.enableDoubleBuffering();
                StdDraw.clear(Color.BLACK);
                Font font = new Font("Georgia", Font.PLAIN,25);
                StdDraw.setFont(font);
                StdDraw.setPenColor(Color.white);
                StdDraw.text(WIDTH/2,HEIGHT/2,"You collect "+blessing+" vapor-eon in total");
                drawNote("OMG! You Did It, Winner!");

                StdDraw.pause(6000);
                break;
            }
            //在游戏中途保存退出
            if (dir=='q'){
                save(pool);
                StdDraw.clear(Color.BLACK);
                drawNote("Already saved your game~");
                StdDraw.pause(4000);
                gameOver=true;
                System.exit(0);
            }
            pool = move(pool,dir);
        }

    }

    private void hud(Pool pool) {
        int mousex = (int) StdDraw.mouseX();
        int mousey = (int) StdDraw.mouseY();
        Font font = new Font("Georgia",Font.PLAIN,14);
        StdDraw.setFont(font);
        if (pool.getWorld()[mousex][mousey].equals(Tileset.WALL)){
            ter.renderFrame(pool.getWorld());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.textLeft(1, HEIGHT-1, "Whoops! Here is the pool's wall");
        } else if (pool.getWorld()[mousex][mousey].equals(Tileset.WATER)) {
            ter.renderFrame(pool.getWorld());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.textLeft(1, HEIGHT-1, "Here is the vapor-eon, condensing the power of water");
        } else if (pool.getWorld()[mousex][mousey].equals(Tileset.TREE)) {
            ter.renderFrame(pool.getWorld());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.textLeft(1, HEIGHT-1, "Here is the mutated fish, try to avoid them");
        }else if (pool.getWorld()[mousex][mousey].equals(Tileset.FLOOR)){
            ter.renderFrame(pool.getWorld());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.textLeft(1, HEIGHT-1, "Here is the water you swim in");
        }else if (pool.getWorld()[mousex][mousey].equals(Tileset.PLAYER)){
            ter.renderFrame(pool.getWorld());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.textLeft(1, HEIGHT-1, "Yourself, a little yellow duck");
        }else if (pool.getWorld()[mousex][mousey].equals(Tileset.LOCKED_DOOR)){
            ter.renderFrame(pool.getWorld());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.textLeft(1, HEIGHT-1, "The door, open it!");
        }else {
            ter.renderFrame(pool.getWorld());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.textLeft(1, HEIGHT-1, "Unknown. What's next?");
        }
        StdDraw.textRight(WIDTH-1, HEIGHT - 1, "Your Health Value: " + health);
        StdDraw.textRight(WIDTH-1,HEIGHT-2,"Your blessing value: "+blessing);
        StdDraw.show();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }
        Pool finalPool = null;
        StringBuilder seed = new StringBuilder();
        int start=0;
        if (input.charAt(0)=='n'||input.charAt(0)=='N'){
            for (int i = 1; i < input.length(); i++) {
                if (input.charAt(i)=='S'||input.charAt(i)=='s'){
                    start = i+1;
                    break;
                }
                seed.append(input.charAt(i));
            }
            SEED = Long.parseLong(String.valueOf(seed));
            finalPool = WorldGenerator.fillTiles();
//            finalWorldFrame = finalPool.getWorld();
            for (int i = start;i<input.length();i++){
                finalPool = move(finalPool,input.charAt(i));
                if((input.charAt(i)==':'&&input.charAt(i+1)=='q')||(input.charAt(i)==':'&&input.charAt(i+1)=='Q')){
                    gameOver=true;
                    save(finalPool);
                    break;
                }
            }return finalPool.getWorld();

        }
        if (input.contains("l")||input.contains("L")){
            finalPool = load();
            finalWorldFrame = finalPool.getWorld();
            int index = input.lastIndexOf('l');
            if (index==-1){
                index = input.lastIndexOf('L');
            }
            for (int i = index+1;i<input.length();i++){
                if (input.charAt(i) == ':' && input.charAt(i + 1) == 'q'|| (input.charAt(i) == ':' && input.charAt(i + 1) == 'Q')) {
                    gameOver = true;
                    save(finalPool);
                    break;
                }
                finalPool = move(finalPool,input.charAt(i));
            }
        }

        if (input.contains(":q")||input.contains(":Q")){
            save(finalPool);
            if (finalPool!=null) {
                return finalPool.getWorld();
            }else{
                return finalWorldFrame;
            }
        }
        return finalWorldFrame;
    }

    public Pool move(Pool pool, char dir){
        TETile up = pool.getWorld()[pool.getPlayer().getX()][pool.getPlayer().getY()+1];
        TETile down = pool.getWorld()[pool.getPlayer().getX()][pool.getPlayer().getY()-1];
        TETile left = pool.getWorld()[pool.getPlayer().getX()-1][pool.getPlayer().getY()];
        TETile right = pool.getWorld()[pool.getPlayer().getX()+1][pool.getPlayer().getY()];
        switch (dir){
            case 'W':
            case 'w':{//up
                if (up.equals(Tileset.WALL)){
                    return pool;
                }else{
                    if (up.equals(Tileset.WATER)){
                        health++;
                        blessing++;
                    } else if (up.equals(Tileset.TREE)) {
                        health--;
                    }
                    pool.getWorld()[pool.getPlayer().getX()][pool.getPlayer().getY()+1]=Tileset.PLAYER;
                    pool.getWorld()[pool.getPlayer().getX()][pool.getPlayer().getY()]=Tileset.FLOOR;
                    Position player = new Position(pool.getPlayer().getX(),pool.getPlayer().getY()+1);
                    return new Pool(player,pool.getWorld());
                }
            }
            case 's':
            case 'S':{//down
                if (down.equals(Tileset.WALL)){
                    return pool;
                }else if (down.equals(Tileset.LOCKED_DOOR)){
                    gameOver=true;
                    return pool;
                }else{
                    if (down.equals(Tileset.WATER)){
                        health++;
                        blessing++;
                    } else if (down.equals(Tileset.TREE)) {
                        health--;
                    }
                    pool.getWorld()[pool.getPlayer().getX()][pool.getPlayer().getY()-1]=Tileset.PLAYER;
                    pool.getWorld()[pool.getPlayer().getX()][pool.getPlayer().getY()]=Tileset.FLOOR;
                    Position player = new Position(pool.getPlayer().getX(),pool.getPlayer().getY()-1);
                    return new Pool(player,pool.getWorld());
                }
            }
            case 'a':
            case 'A':{//left
                if (left.equals(Tileset.WALL)){
                    return pool;
                }else{
                    if (left.equals(Tileset.WATER)) {
                        health++;
                        blessing++;
                    } else if (left.equals(Tileset.TREE)) {
                        health--;
                    }
                    pool.getWorld()[pool.getPlayer().getX()-1][pool.getPlayer().getY()]=Tileset.PLAYER;
                    pool.getWorld()[pool.getPlayer().getX()][pool.getPlayer().getY()]=Tileset.FLOOR;
                    Position player = new Position(pool.getPlayer().getX()-1,pool.getPlayer().getY());
                    return new Pool(player,pool.getWorld());
                }
            }
            case 'd':
            case 'D':{
                //right
                if (right.equals(Tileset.WALL)){
                    return pool;
                }else{
                    if (right.equals(Tileset.WATER)) {
                        health++;
                        blessing++;
                    } else if (right.equals(Tileset.TREE)) {
                        health--;
                    }
                    pool.getWorld()[pool.getPlayer().getX()+1][pool.getPlayer().getY()]=Tileset.PLAYER;
                    pool.getWorld()[pool.getPlayer().getX()][pool.getPlayer().getY()]=Tileset.FLOOR;
                    Position player = new Position(pool.getPlayer().getX()+1,pool.getPlayer().getY());
                    return new Pool(player,pool.getWorld());
                }
            }
            default:{
                return pool;
            }
        }
    }



    public static void save(Pool pool){
        File file = new File("./pool.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(pool);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }

    }

    public Pool load(){
        File file = new File("./pool.txt");
        if (file.exists()) {
            try {
                FileInputStream fs = new FileInputStream(file);
                ObjectInputStream os = new ObjectInputStream(fs);
                Pool loadWorld = (Pool) os.readObject();
                os.close();
                return loadWorld;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        return new Pool();
    }


}
