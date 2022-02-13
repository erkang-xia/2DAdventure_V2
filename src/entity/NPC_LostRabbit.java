package entity;

import com.javalearning.GamePanel;
import java.util.Random;

public class NPC_LostRabbit extends Entity{

    public NPC_LostRabbit(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        getNPCImage();
        setDialogue();
    }

    public void getNPCImage(){

        //called setup in parent class
        left1 = setup("/npc/rabbit_left_1");
        left2 = setup("/npc/rabbit_left_2");
        right1 = setup("/npc/rabbit_right_1");
        right2 = setup("/npc/rabbit_right_2");
        still = setup("/npc/rabbit_still");
    }

    public void setDialogue() {
        dialogues[0] = "Hello, hello!";
        dialogues[1] = "Can you help me?";
        dialogues[2] = "This is my first time here, I am \nhungary but I can't find any carrots";
        dialogues[3] = "Do you willing to help?";


    }
    public void speak() {
        super.speak();

    }
    public void setAction(){

        actionLocker ++;
        if (actionLocker == 150){
            Random random = new Random();
            int i = random.nextInt(100) + 1; //get random number from 1 to 100

            if (i <= 30) {
                direction = "left";
            }
            if(i > 30 && i <=60) {
                direction = "right";
            }
            if(i > 60) {
                direction = "still";
            }

            actionLocker = 0;
        }


    }



}
