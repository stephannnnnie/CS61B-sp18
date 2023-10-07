package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import org.junit.Test;

import java.awt.*;
import java.util.Random;

/**
 * @author sheldon
 */
public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 43;
    public static long SEED;

    private int steps;
    private boolean gameOver;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        setting();
        menu();
        steps=0;
        gameOver=false;
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
                TETile[][] newWorld = WorldGenerator.fillTiles();
                ter.renderFrame(newWorld);
                play(newWorld);
                break;

            }else if (key=='L'||key=='l'){
                gameOver=false;
                TETile[][] world = load();
                ter.initialize(WIDTH,HEIGHT);
                ter.renderFrame(world);
                play(world);
                break;
            }else if (key=='q'||key=='Q'){
                gameOver=true;
                System.exit(0);
                break;
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

    public void play(TETile[][] world){

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

        TETile[][] finalWorldFrame = null;
        StringBuilder seed = new StringBuilder();
        if (input.charAt(0)=='n'||input.charAt(0)=='N'){
            for (int i = 1; i < input.length(); i++) {
                if (input.charAt(i)=='S'||input.charAt(i)=='s'){
                    break;
                }
                seed.append(input.charAt(i));
            }
            SEED = Long.parseLong(String.valueOf(seed));
            finalWorldFrame = WorldGenerator.fillTiles();

        }
        if (input.contains("l")||input.contains("L")){
            finalWorldFrame = load();
        }

        if (input.endsWith(":q")||input.endsWith(":Q")){
            save(finalWorldFrame);
            return finalWorldFrame;
        }
        return finalWorldFrame;
    }

    public void move(TETile[][] world, char dir){
        switch (dir){
            case 'W':{//up

            }
            case 'S':{//down

            }
            case 'A':{//left

            }
            case 'D':{//right

            }
            default:{
                return;
            }
        }
    }



    public void save(TETile[][] world){

    }

    public TETile[][] load(){
        return null;
    }


}
