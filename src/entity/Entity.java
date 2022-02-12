package entity;

import com.javalearning.GamePanel;
import com.javalearning.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    GamePanel gp;
    UtilityTool uTool;
    public int worldX, worldY;
    public int speed;

    // an image with an accessible buffer of image data, we use this to store our image files
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0,0,48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionIsOn = false;
    public int actionLocker = 0;
    String[] dialogues = new String[20];
    int dialogueIndex = 0;


    public Entity(GamePanel gp) {
        this.gp = gp;
        this.uTool = new UtilityTool();
    }

    public void setAction(){}
    public void speak() {
        if (dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }

    }
    public void update() {
        setAction();

        collisionIsOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this,false);
        gp.cChecker.checkPlayer(this);
        if (!collisionIsOn) {

            switch (direction) {
                case "up" -> worldY = worldY - speed;
                case "down" -> worldY = worldY + speed;
                case "left" -> worldX = worldX - speed;
                case "right" -> worldX = worldX + speed;
            }
        }


        spriteCounter++;
        if (spriteCounter >10) {
            if (spriteNum == 1) {
                spriteNum =2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

    }

    public BufferedImage setup(String imagePath) {

        BufferedImage scaledImage = null;

        try{
            scaledImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream( imagePath + ".png")));
            scaledImage = uTool.scaledImage(scaledImage, gp.tileSize, gp.tileSize);

        }
        catch(IOException e){
            e.printStackTrace();
        }

        return scaledImage;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

            switch (direction) {
                case "up" -> {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                case "down" -> {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                case "left" -> {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                case "right" -> {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
