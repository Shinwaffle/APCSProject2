package level;

import java.awt.*;
import java.util.ArrayList;

public class Level_1_8 implements Level{

    ArrayList<ArrayList<Integer>> color = new ArrayList<>();

    @Override
    public ArrayList<Rectangle> paintBrick() {
        ArrayList<Rectangle> Brick = new ArrayList<>();
        int brickx = 70;
        int bricky = 50;
        int index = 5;

        for (int i = 0; i < 42; i++) {
           if (i == 1 ||i == 2 ||i == 4 ||i == 7 ||i == 8 ||i == 10 ||i == 13 ||i == 14 ||i == 16 ||i == 22 ||i == 25 ||i == 26 ||i == 28 ||
              i == 31 ||i == 32 ||i == 34 ||i == 37 ||i == 38 ||i == 41 ||) {
              Brick.add(null);
           } else if (i == 11) {
              Brick.add(null);
           } else {
            Brick.add(new Rectangle(brickx, bricky, 30, 10));
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
        if (color.size() != 42) {
            for (int i = 0; i < 42; i++) { // (i*6)+3
                ArrayList<Integer> colorIndex = new ArrayList<>();
                    colorIndex.add((i*6)+3);
                    colorIndex.add(0);
                    colorIndex.add((i*6)+3);
                    color.add(colorIndex);
            }
        }
        return color;
    }

    @Override
    public int getCode() {
        return 5893;
    }
}
