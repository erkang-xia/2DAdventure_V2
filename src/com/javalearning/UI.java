package com.javalearning;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, astral;

    public boolean messageOn = false;
    public String message = "";
    int messageTimer = 0;

    double playTime;

    DecimalFormat dFormat = new DecimalFormat("#0.00"); //set numerical format
    public String currentDialogue = "";

    public boolean isGameFinished = false;

    public UI(GamePanel gp) {
        this.gp = gp;

        try{
            //instantiate font
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Astral Sisters.ttf");
            astral = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }


    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }


    public void draw(Graphics2D g2) {
        // so we can use this g2 in other method
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        //PLAY STATE
        if (gp.gameState == gp.playState){

        }
        //PAUSE STATE
        if (gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }


    }

    private void drawDialogueScreen() {
        //create dialogue window
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize * 4;
        drawSubwindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        int wordx = x + gp.tileSize;
        int wordy = y + gp.tileSize;

        for (String line : currentDialogue.split("\n")){
            g2.drawString(line,wordx,wordy);
            // the next line will display below the line
            wordy += 40;
        }


    }

    public void drawSubwindow(int x, int y, int width, int height) {
        //create new color
        Color blk = new Color(0,0,0,200);
        g2.setColor(blk);
        g2.fillRoundRect(x,y,width,height,35,35);

        Color wht = new Color(225,225,225);
        g2.setColor(wht);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);




    }

    public void drawPauseScreen(){
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text,x,y);
    }

    public int getXForCenteredText(String text) {

        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        //X
        return gp.screenWidth/2 -length/2;
    }


}
