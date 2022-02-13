package tile;

import com.javalearning.GamePanel;
import com.javalearning.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum_Ground;
    public int[][] mapTileNum_Air;
    UtilityTool uTool;

    public TileManager(GamePanel gp) {

        this.gp = gp;
        this.tile = new Tile[100];
        this.mapTileNum_Ground = new int [gp.maxWorldCol][gp.maxWorldRow];
        this.mapTileNum_Air = new int [gp.maxWorldCol][gp.maxWorldRow];
        this.uTool = new UtilityTool();

        getTileImage();
        loadMap("/maps/myWorld.txt",true);
        //loadMap("/maps/myWorld2.txt",false);


    }

    public void getTileImage() {
//        setup(0,"grass00",false);
//        setup(1,"grass00",false);
//        setup(2,"grass00",false);
//        setup(3,"grass00",false);
//        setup(4,"grass00",false);
//        setup(5,"grass00",false);
//        setup(6,"grass00",false);
//        setup(7,"grass00",false);
//        setup(8,"grass00",false);
//        setup(9,"grass00",false);
//        setup(10,"grass00",false);
        setup(11,"Ground_11",false);
        setup(12,"Ground_12",false);
        setup(13,"Ground_13",false);
        setup(14,"Ground_14",false);
        setup(15,"Ground_15",false);
        setup(16,"Ground_16",false);
        setup(17,"Ground_17",false);
        setup(21,"Grass_21",false);
        setup(22,"Grass_22",false);
        setup(23,"Grass_23",false);
        setup(31,"Tree_31",false);
        setup(32,"Tree_32",true);
        setup(33,"Tree_33",false);
        setup(51,"Pond_51",true);
        setup(52,"Pond_52",true);
        setup(53,"River_53",false);
        setup(54,"River_54",false);
        setup(55,"River_55",false);
        setup(56,"River_56",false);
        setup(61,"River_61",true);
        setup(62,"River_62",true);
        setup(63,"River_63",true);
        setup(64,"River_64",true);
        setup(65,"River_65",true);
        setup(66,"River_66",true);
        setup(67,"River_67",true);
        setup(68,"River_68",true);
        setup(99,"Transparent_99",false);






    }

    // this func used for installation, import image, scale and collision
    public void setup(int index, String imageName, boolean collision ) {
        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            //tile[index].image = uTool.scaledImage(tile[index].image, gp.tileSize,gp.tileSize);
            tile[index].collision = collision;
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    public void loadMap(String filePath, Boolean isGround) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;


            while(col< gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine(); // read a line of content

                while(col < gp.maxWorldCol) {
                    // number = a number seperate by " "
                    String[] numbers = line.split(" "); // line.split: split this spring around matches of given regular expression
                    // using col as index
                    int num = Integer.parseInt(numbers[col]);
                    if(isGround){
                        mapTileNum_Ground[col][row] = num;
                    }else{
                        mapTileNum_Air[col][row] = num;
                    }

                    col++;
                }
                if (col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

   //camera
    public void draw(Graphics g2,boolean isGround) {

        int worldCol = 0;
        int worldRow = 0;
        int tileNum;

        while(worldCol< gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            if(isGround){
                tileNum = mapTileNum_Ground[worldCol][worldRow];
            }else {
                tileNum = mapTileNum_Air[worldCol][worldRow];
            }



            int worldX = worldCol *gp.tileSize;
            int worldY = worldRow *gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            //Stop moving the camera at the edge
            if (gp.player.screenX > gp.player.worldX){
                screenX = worldX;
            }
            if(gp.player.screenY > gp.player.worldY ){
                screenY = worldY;
            }
            int rightOffset = gp.screenWidth - gp.player.screenX;
            if(rightOffset > gp.worldWidth - gp.player.worldX) {
                screenX = gp.screenWidth - (gp.worldWidth -worldX);
            }
            int bottomOffset = gp.screenHeight - gp.player.screenY;
            if(bottomOffset > gp.worldHeight - gp.player.worldY) {
                screenY = gp.screenHeight - (gp.worldHeight -worldY);
            }

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                //the forth and fifth ones determine the tile size
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                //for test
                g2.setColor(Color.white);
                g2.drawRect(screenX,screenY,48,48);
                int tilex = worldX/gp.tileSize;
                int tiley = worldY/gp.tileSize;
                g2.drawString((String.valueOf(tilex)),screenX,screenY + 15);
                g2.drawString((String.valueOf(tiley)),screenX + 20,screenY + 15);
            }
            else if (gp.player.screenX > gp.player.worldX ||
                     gp.player.screenY > gp.player.worldY ||
                     rightOffset > gp.screenWidth - gp.player.worldX ||
                     bottomOffset > gp.screenHeight - gp.player.worldY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            if(worldCol ==gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
