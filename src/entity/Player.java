package entity;

import com.javalearning.GamePanel;
import com.javalearning.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity{

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;


    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 16;
        solidArea.height = 16;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize*23;
        worldY = gp.tileSize*21;
        speed = 4;
        direction = "down";
    }


    public void getPlayerImage(){
            up1 = setup("/player/boy_up_1");
            up2 = setup("/player/boy_up_2");
            down1 = setup("/player/boy_down_1");
            down2 = setup("/player/boy_down_2");
            left1 = setup("/player/boy_left_1");
            left2 = setup("/player/boy_left_2");
            right1 = setup("/player/boy_right_1");
            right2 = setup("/player/boy_right_2");
    }

    @Override
    public void update() {
        if(keyH.rightPressed || keyH.downPressed || keyH.leftPressed || keyH.upPressed){
            if(keyH.upPressed) {
                direction = "up";
            }
            else if(keyH.downPressed){
                direction = "down";
            }
            else if(keyH.leftPressed){
                direction = "left";
            }
            else {
                direction = "right";
            }

            //CHECK COLLISION
            collisionIsOn = false;
            gp.cChecker.checkTile(this);
            int objIndex = gp.cChecker.checkObject(this, true);
            int npcIndex = gp.cChecker.checkCollisionWithEntity(this,gp.npc);
            npcInteration(npcIndex);

            //PICK UP OBJECTS
            pickUPObject(objIndex);

            // IF COLLISION IS FALSE< PLAYER CAN MOVE

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

    }

    private void npcInteration(int npcIndex) {
        if (npcIndex != 999){
            if(gp.keyH.enterPressed){
                gp.gameState = gp.dialogueState;
                gp.npc[npcIndex].speak();
            }
        }
        gp.keyH.enterPressed = false;

    }

    public void pickUPObject(int index){
        if (index != 999){

        }

    }

    public void draw(Graphics2D g2) {
/*
        g2.setColor(Color.white);
        g2.fillRect(x,y,gp.tileSize,gp.tileSize);
*/

        BufferedImage image = null;
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

        int x = screenX;
        int y = screenY;

        if(screenX > worldX) {
            x = worldX;
        }
        if(screenY > worldY) {
            y = worldY;
        }

        int rightOffset = gp.screenWidth - screenX;
        if(rightOffset > gp.worldWidth - worldX) {
            x = gp.screenWidth - (gp.worldWidth -worldX);
        }
        int bottomOffset = gp.screenHeight - screenY;
        if(bottomOffset > gp.worldHeight - worldY) {
            y = gp.screenHeight - (gp.worldHeight -worldY);
        }


        g2.drawImage(image, x, y, null);

    }
}
