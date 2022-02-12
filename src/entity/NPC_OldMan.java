package entity;

import com.javalearning.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        getNPCImage();
        setDialogue();
    }

    public void getNPCImage(){

        //called setup in parent class
        up1 = setup("/npc/oldman_up_1");
        up2 = setup("/npc/oldman_up_2");
        down1 = setup("/npc/oldman_down_1");
        down2 = setup("/npc/oldman_down_2");
        left1 = setup("/npc/oldman_left_1");
        left2 = setup("/npc/oldman_left_2");
        right1 = setup("/npc/oldman_right_1");
        right2 = setup("/npc/oldman_right_2");
    }

    public void setDialogue() {
        dialogues[0] = "Hello, lad.";
        dialogues[1] = "I've wait for you a long time.";
        dialogues[2] = "I used to be a great wizard...but now I am too \nold to take you on an adventure.";
        dialogues[3] = "Do you willing to help?";


    }
    public void speak() {
        super.speak();

    }
    public void setAction(){

        actionLocker ++;
        if (actionLocker == 120){
            Random random = new Random();
            int i = random.nextInt(100) + 1; //get random number from 1 to 100

            if (i <= 25) {
                direction = "up";
            }
            if(i > 25 && i <=50) {
                direction = "down";
            }
            if(i > 50 && i <=75) {
                direction = "left";
            }
            if(i > 75) {
                direction = "right";
            }
            actionLocker = 0;
        }


    }

}
