package level;

import java.awt.*;
import java.util.ArrayList;

public class Level_1_4 implements Level {

    ArrayList<ArrayList<Integer>> color = new ArrayList<>();

    @Override
    public ArrayList<Rectangle> paintBrick() {
        ArrayList<Rectangle> Brick = new ArrayList<>();
        int brickx = 70;
        int bricky = 50;
        int index = 5;

        //30 wide 10 height
        for (int i = 0; i < 36; i++) {
            if (i == 2 || i == 3 ) {
                Brick.add(null);
                brickx += 31;
            } else if (i == 5) {
                Brick.add(new Rectangle(brickx, bricky, 30, 10));
                brickx = 70;
                bricky += 12;
            } else if (i == 8 || i == 9) {
                Brick.add(null);
                brickx += 31;
            } else if (i == 11) {
                Brick.add(new Rectangle(brickx, bricky, 30, 10));
                brickx = 70;
                bricky += 12;
            } else if (i == 12 || i == 13 || i == 16) {
                Brick.add(null);
                brickx += 31;
            } else if (i == 17) {
                Brick.add(null);
                brickx = 70;
                bricky += 12;
            } else if (i == 18) {
                Brick.add(null);
                brickx += 31;
            } else if (i == 23) {
                Brick.add(null);
                brickx = 70;
                bricky += 12;
            } else if (i == 24) {
                Brick.add(null);
                brickx += 31;
            } else if (i == 29) {
                Brick.add(null);
                brickx = 70;
                bricky += 12;
            } else if (i == 30) {
                Brick.add(null);
                brickx += 31;
            } else if (i == 32 || i == 33) {
                Brick.add(null);
                brickx += 31;
            } else if (i == 35) {
                Brick.add(null);
            } else {
                Brick.add(new Rectangle(brickx, bricky, 30, 10));
                brickx += 31;
            }

        }
        return Brick;
    }
    @Override
    public int getBallSpeed() {
        return 10;
    }

    @Override
    public Rectangle getBat() {
        return new Rectangle(150, 225, 30, 5);
    }

    @Override
    public int getBatSpeed() {
        return 3;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getColors() {
        if (color.size() != 36) {
            for (int i = 0; i < 36; i++) {
                ArrayList<Integer> colorIndex = new ArrayList<>();
                colorIndex.add(0);
                colorIndex.add(0);
                colorIndex.add(0);
                color.add(colorIndex);
            }
        }
        return color;
    }

    @Override
    public int getCode() {
        return 3775;
    }
}
