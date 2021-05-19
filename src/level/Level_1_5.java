package level;

import java.awt.*;
import java.util.ArrayList;

public class Level_1_5 implements Level {

    ArrayList<ArrayList<Integer>> color = new ArrayList<>();
    @Override
    public ArrayList<Rectangle> paintBrick() {
        ArrayList<Rectangle> Brick = new ArrayList<>();
        int brickx = 80;
        int bricky = 50;
        int index = 5;

        for (int i = 0; i < 36; i++) {
             Brick.add(new Rectangle(brickx, bricky, 15, 5));   
    
            if (i == index) {
                brickx = 49;
                bricky += 12;
                index = index + 6;
            }
            brickx += 31;

        }
        return Brick;
    }

    @Override
    public int getBallSpeed() {
        return 5;
    }

    @Override
    public Rectangle getBat() {
        return new Rectangle(160, 245, 50, 7);
    }

    @Override
    public int getBatSpeed() {
        return 3;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getColors() {
         if (color.size() != 36) {
            for (int i = 0; i < 36; i++) { // (i*6)+39
                if (i == 0 || i == 2 || i == 3 || i == 5 || i == 19 || i == 22 || i == 31 || i == 34) {
                    ArrayList<Integer> colorIndex = new ArrayList<>();
                    colorIndex.add(255);
                    colorIndex.add(255);
                    colorIndex.add(255);
                    color.add(colorIndex);
                } else if (i == 1 || i == 4) {
                    ArrayList<Integer> colorIndex = new ArrayList<>();
                    colorIndex.add(0);
                    colorIndex.add(0);
                    colorIndex.add(0);
                    color.add(colorIndex);   
                } else if (i > 23 && i < 30) {
                    ArrayList<Integer> colorIndex = new ArrayList<>();
                    colorIndex.add(255);
                    colorIndex.add(0);
                    colorIndex.add(0);
                    color.add(colorIndex);  
                } else {
                    ArrayList<Integer> colorIndex = new ArrayList<>();
                    colorIndex.add(0);
                    colorIndex.add(255-((i*6)+39));
                    colorIndex.add(0);
                    color.add(colorIndex);  
                }
            }
        }
        return color;
    }

    @Override
    public int getCode() {
        return 0007;
    }
}
