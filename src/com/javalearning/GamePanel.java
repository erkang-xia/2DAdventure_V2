package com.javalearning;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //JPanel works as game screen

   //SCREEN SETTING
    final int originalTileSize = 16; //16*16 tile, standard in old time
    final int scale = 3; //scale the tile by 3 times
    public final int tileSize = originalTileSize * scale; //48*48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //768 pixels //Size of the game screen
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels


    //WORLD SETTING
    public final int maxWorldCol = 50; //corresponding to the world map file
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;


    //FRAME PER SEC
    int FPS = 60;


    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public  UI ui = new UI(this);
    Sound backgroundMusic = new Sound();
    Sound soundEffect = new Sound();

    Thread gameThread; // work with runable

    //ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity[] npc = new Entity[10]; //more than one npc in the future
    public SuperObject[] obj = new SuperObject[10]; //we can dispaly 10 objects at the same time

    //GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;


    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        // if set to true, all the drawing from this component will be done in an offscreen painting buffer
        //enable this can improve game's rendering performance
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true); // with this gamePanel can be focused to receive key input

    }

    //use for add elements: object, NPC and so on
    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();

        //play BlueBoyAdventure.wav
        playMusic(0);
        //stopMusic();

        gameState = playState;


    }

    public void startGameThread(){

        //this means the gamePanel, you are passing gamePanel to the Thread
        gameThread = new Thread(this);
        gameThread.start();

    }

    // when we start gameThread, it's gonna automatically called the run method
    @Override
    public void run() {
        // this where we create the game loop the core of our game
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        // can we just set a time constrain?
        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                //1 update the information such as character position
                update();
                //2 draw position with updated info
                repaint();// call paintComponent function
                delta--;
            }


        }
    }

    public void update() {
        if(gameState == playState) {
            player.update();
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }
        }
        else if(gameState == pauseState){

        }
    }

    public void paintComponent(Graphics g) {
        // graphics a class use to draw objects on the screen
        super.paintComponent(g); //standard way to do it

        Graphics2D g2 = (Graphics2D) g; //casting to graphic 2d for more function

        //DRAW
        //Tile
        tileM.draw(g2,true);//here tile comes first because it works as layer, tile is the background
        //Object
        for (int i = 0; i < obj.length; i++){
            if(obj[i] != null){
                obj[i].draw(g2,this);
            }
        }

        //NPC
        for (int i = 0; i < npc.length; i++){
            if(npc[i] != null){
                npc[i].draw(g2);
            }
        }
        //Player
        player.draw(g2);
        ui.draw(g2);

        //tileM.draw(g2,false);


        g2.dispose(); // dispose context and release memory
    }


    public void playMusic(int i) {
        backgroundMusic.setFile(i);
        backgroundMusic.play();
        backgroundMusic.loop();
    }

    public void stopMusic() {
        backgroundMusic.stop();
    }

    public  void playSoundEffect(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }
}
