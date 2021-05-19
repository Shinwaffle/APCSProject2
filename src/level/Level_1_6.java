package level;

import java.awt.*;
import java.util.ArrayList;

public class Level_1_6 implements Level{

    ArrayList<ArrayList<Integer>> color = new ArrayList<>();

    @Override
    public ArrayList<Rectangle> paintBrick() {
        ArrayList<Rectangle> Brick = new ArrayList<>();
        int brickx = 70;
        int bricky = 50;
        int index = 5;

        for (int i = 0; i < 36; i++) {
            if (i == 12 || i == 18 || i == 17 || i == 23 || i == 29|| i == 24 || i == 25 || i == 28 || i == 30 || i == 31 || i == 34 || i == 35) {
              Brick.add(null);
            } else {
              Brick.add(new Rectangle(brickx, bricky, 30, 10));   
            }
            if (i == 17 || i == 23 || i == 29 || i == 35) {
              Brick.add(null);
              brickx = 70;
              bricky += 12;
              index += 6;
              continue;
            }
            if (i == index) {
                brickx = 39;
                bricky += 12;
                index = index + 6;
            }
           
            brickx += 31;

        }
        return Brick;
    }

    @Override
    public int getBallSpeed() {
        return 6;
    }

    @Override
    public Rectangle getBat() {
        return new Rectangle(160, 245, 35, 10);
    }

    @Override
    public int getBatSpeed() {
        return 3;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getColors() {
        if (color.size() != 37) {
            for (int i = 0; i < 37; i++) {
                if ((i >= 0 && i <= 6) || i == 11) {
                    ArrayList<Integer> colorIndex = new ArrayList<>();
                    colorIndex.add(0);
                    colorIndex.add(0);
                    colorIndex.add(0);
                    color.add(colorIndex);
                } else if (
                  (i >= 7 && i <= 10) 
                  || (i >= 13 && i <= 16)
                  || (i >= 19 && i <= 23)) {
                   ArrayList<Integer> colorIndex = new ArrayList<>();
                    colorIndex.add(200);
                    colorIndex.add(61);
                    colorIndex.add(61);
                    color.add(colorIndex);
                } else {
                  ArrayList<Integer> colorIndex = new ArrayList<>();
                    colorIndex.add(255);
                    colorIndex.add(255);
                    colorIndex.add(255);
                    color.add(colorIndex); 
                }
            }
        }
        return color;
    }

    @Override
    public int getCode() {
        return 4066;
    }
}
