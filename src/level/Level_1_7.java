package level;

import java.awt.*;
import java.util.ArrayList;

public class Level_1_7 implements Level{

    ArrayList<ArrayList<Integer>> color = new ArrayList<>();

    @Override
    public ArrayList<Rectangle> paintBrick() {
        ArrayList<Rectangle> Brick = new ArrayList<>();
        int brickx = 70;
        int bricky = 30;
        int index = 11;

        for (int i = 0; i < 72; i++) {
            
            if (i % 5 == 0) {
              Brick.add(null);
            } else {
              Brick.add(new Rectangle(brickx, bricky, 15, 5)); 
            }
            if (i == index) {
                brickx = 54;
                bricky += 7;
                index = index + 12;
            }
            brickx += 16;

        }
        return Brick;
    }

    @Override
    public int getBallSpeed() {
        return 4;
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
        if (color.size() != 72) {
            for (int i = 0; i < 72; i++) { // (72*3)+39
                
            }
        }
        return color;
    }

    @Override
    public int getCode() {
        return 2221;
    }
}
